package slava0135.fuzz3.maze;

import java.util.*;
import java.util.stream.Collectors;

import slava0135.fuzz3.Location;
import slava0135.fuzz3.Monitor;
import slava0135.fuzz3.MutationFuzzer;
import slava0135.fuzz3.maze.FunctionRunner.Outcome;

public class AdvancedMutationFuzzer extends MutationFuzzer {
    public List<String> seeds;
    public MazeMutator mutator;
    public PowerSchedule schedule;
    public List<String> inputs;
    public List<Seed> population;
    private int seedIndex;
    private Random random;
    public Set<String> coveragesSeen = new HashSet<>();
    private Monitor monitor;
    PathIDGenerator pathIDGenerator = new PathIDGenerator();

    public AdvancedMutationFuzzer(
            List<String> seeds,
            MazeMutator mutator,
            PowerSchedule schedule,
            Monitor monitor,
            int minMutations,
            int maxMutations) {
        super(seeds, minMutations, maxMutations);
        this.seeds = seeds;
        this.mutator = mutator;
        this.schedule = schedule;
        this.monitor = monitor;
        this.inputs = new ArrayList<>();
        this.random = new Random();
        reset();
    }

    public void reset() {
        this.population = seeds.stream()
                .map(Seed::new)
                .collect(Collectors.toList());
        this.seedIndex = 0;
    }

    public String createCandidate() {
        Seed seed = schedule.choose(population);

        // Stacking: Apply multiple mutations to generate the candidate
        String candidate = seed.getData();
        int trials = random.nextInt(minMutations, maxMutations);
        for (int i = 0; i < trials; i++) {
            candidate = mutator.mutate(candidate);
        }
        return candidate;
    }

    @Override
    public String fuzz() {
        String inp;
        if (seedIndex < seeds.size()) {
            inp = seeds.get(seedIndex);
            seedIndex++;
        } else {
            inp = createCandidate();
        }

        inputs.add(inp);
        return inp;
    }

    @Override
    public String mutate(String input) {
        return mutator.mutate(input);
    }

    public Object run(FunctionRunner runner, String input) {
        var resultOutcome = runner.run(input);
        var result = resultOutcome.first;
        var outcome = resultOutcome.second;
        var coverage = new TreeSet<>(runner.coverage);
        var locations = coverage.stream().map(Location::buildFromString).collect(Collectors.toSet());
        schedule.pathFrequency.merge(pathIDGenerator.getPathID(locations), 1, (prev, next) -> prev + next);
        if (outcome == Outcome.PASS && !coveragesSeen.containsAll(coverage)) {
            System.out.println("NEW COVERAGE");
            var seed = new Seed(input);
            seed.setCoverage(locations);
            population.add(seed);
            coveragesSeen.addAll(coverage);
            System.out.println("INPUT = '" + input + "'");
        }
        return result;
    }

    public void fuzz(FunctionRunner runner, int trials) {
        for (int i = 0; i < trials; i++) {
            var input = fuzz();
            var result = run(runner, input);
            if (monitor.addResult(input, result)) {
                break;
            }
        }
    }

    public List<String> getInputs() {
        return inputs;
    }
}

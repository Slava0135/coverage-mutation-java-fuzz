package slava0135.fuzz3.maze;

import java.util.*;
import java.util.stream.Collectors;

import slava0135.fuzz3.MutationFuzzer;

public class AdvancedMutationFuzzer extends MutationFuzzer {
    public List<String> seeds;
    public MazeMutator mutator;
    public PowerSchedule schedule;
    public List<String> inputs;
    public List<Seed> population;
    private int seedIndex;
    private Random random;
    public Set<String> coveragesSeen = new HashSet<>();

    public AdvancedMutationFuzzer(
            List<String> seeds,
            MazeMutator mutator,
            PowerSchedule schedule,
            int minMutations,
            int maxMutations) {
        super(seeds, minMutations, maxMutations);
        this.seeds = seeds;
        this.mutator = mutator;
        this.schedule = schedule;
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
        int trials = Math.min(candidate.length(), 1 << random.nextInt(5) + 1);
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
        FunctionRunner.Tuple<Object, String> resultOutcome = runner.run(input);
        Object result = resultOutcome.first;
        System.err.println(runner.coverage);
        if (!coveragesSeen.containsAll(runner.coverage)) {
            System.out.println("NEW COVERAGE");
            population.add(new Seed(input));
            coveragesSeen.addAll(runner.coverage);
            System.out.println("INPUT = " + input);
        }
        return result;
    }

    public void fuzz(FunctionRunner runner, int trials) {
        for (int i = 0; i < trials; i++) {
            String input = fuzz();
            run(runner, input);
        }
    }

    public List<String> getInputs() {
        return inputs;
    }
}

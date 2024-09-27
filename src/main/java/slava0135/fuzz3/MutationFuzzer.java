package slava0135.fuzz3;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class MutationFuzzer {
    protected List<String> seed;
    protected int minMutations;
    protected int maxMutations;
    protected List<String> population;
    protected int seedIndex;
    private static final Random random = new Random();

    public MutationFuzzer(List<String> seed, int minMutations, int maxMutations) {
        this.seed = seed;
        this.minMutations = minMutations;
        this.maxMutations = maxMutations;
        reset(seed);
    }

    public void reset(List<String> seed) {
        this.population = new ArrayList<>(seed);
        this.seedIndex = 0;
    }

    public String createCandidate() {
        String candidate = population.get(random.nextInt(population.size()));
        int trials = random.nextInt(maxMutations - minMutations + 1) + minMutations;
        for (int i = 0; i < trials; i++) {
            candidate = mutate(candidate);
        }
        return candidate;
    }

    public String fuzz() {
        String inp = "";
        if (seedIndex < seed.size()) {
            inp = seed.get(seedIndex);
            seedIndex++;
        } else {
            inp = createCandidate();
        }
        return inp;
    }

    public abstract String mutate(String input);
}

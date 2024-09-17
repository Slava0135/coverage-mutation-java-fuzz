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

    /**
     * Конструктор.
     *
     * @param seed - список строк для мутации.
     * @param minMutations - минимальное количество мутаций.
     * @param maxMutations - максимальное количество мутаций.
     */
    public MutationFuzzer(List<String> seed, int minMutations, int maxMutations) {
        this.seed = seed;
        this.minMutations = minMutations;
        this.maxMutations = maxMutations;
        reset();
    }

    /**
     * Устанавливает популяцию на начальный набор.
     * Должен быть переопределен в подклассах.
     */
    public void reset() {
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

    // Пример метода мутации (может быть реализован в подклассах)
    public abstract String mutate(String input);

    // Геттеры и сеттеры для полей, если нужно

    public List<String> getSeed() {
        return seed;
    }

    public void setSeed(List<String> seed) {
        this.seed = seed;
    }

    public int getMinMutations() {
        return minMutations;
    }

    public void setMinMutations(int minMutations) {
        this.minMutations = minMutations;
    }

    public int getMaxMutations() {
        return maxMutations;
    }

    public void setMaxMutations(int maxMutations) {
        this.maxMutations = maxMutations;
    }

    public List<String> getPopulation() {
        return population;
    }

    public void setPopulation(List<String> population) {
        this.population = population;
    }

    public int getSeedIndex() {
        return seedIndex;
    }

    public void setSeedIndex(int seedIndex) {
        this.seedIndex = seedIndex;
    }
}

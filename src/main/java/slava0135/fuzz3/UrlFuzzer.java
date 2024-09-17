package slava0135.fuzz3;

import slava0135.fuzz3.instrumentation.CoverageTracker;

import java.util.*;

public class UrlFuzzer extends MutationFuzzer {
    public enum Status {
        PASS,
        FAIL
    }

    private static final List<String> defaultSeed = List.of("https://www.google.com/", "https://docs.kernel.org/process/1.Intro.html#executive-summary");
    private static final Random random = new Random();

    private final Set<Set<String>> coveragesSeen = new HashSet<>();

    public UrlFuzzer(List<String> seed, int minMutations, int maxMutations) {
        super(seed, minMutations, maxMutations);
    }

    @Override
    public String mutate(String input) {
        return switch (random.nextInt(3)) {
            case 0 -> StringMutator.deleteRandomCharacter(input);
            case 1 -> StringMutator.flipRandomCharacter(input);
            default -> StringMutator.insertRandomCharacter(input);
        };
    }

    public Status run(String input) {

        var status = Status.PASS;
        try {
            UrlParser.parse(input);
        } catch (IllegalArgumentException ignored) {
        } catch (Exception ex) {
            status = Status.FAIL;
        }

        Set<String> newCoverage = CoverageTracker.coverage;
        if (status.equals(Status.PASS) && !coveragesSeen.contains(newCoverage)) {
            population.add(input);
            coveragesSeen.add(newCoverage);
        }

        return status;
    }

    public void runs(int count) {
        int passes = 0;
        int fails = 0;
        for (var i = 1; i <= count; i++) {
            var s = fuzz();
            switch (run(s)) {
                case PASS -> {
                    passes++;
                }
                case FAIL -> {
                    System.out.printf("FAIL\n%s\n", s);
                    fails++;
                }
            }
        }
        System.out.printf("PASSES: %d; FAILS: %d", passes, fails);
    }

    public static void main(String[] args) {
        var fuzzer = new UrlFuzzer(defaultSeed, 10, 100);
        fuzzer.runs(100000);
    }
}

package slava0135.fuzz3;

import slava0135.fuzz3.instrumentation.CoverageTracker;

import java.util.*;
import java.util.stream.Collectors;

public class CgiFuzzer extends MutationFuzzer {
    public enum Status {
        PASS,
        FAIL
    }

    private static final List<String> defaultSeed = List.of("Hello+World%21", "+", "%20", "abc");
    private static final Random random = new Random();

    private final Set<Set<String>> coveragesSeen = new HashSet<>();

    public CgiFuzzer(List<String> seed, int minMutations, int maxMutations) {
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
            CgiDecoder.cgiDecode(input);
        } catch (Exception ignored) {
            status = Status.FAIL;
        }

        Set<String> newCoverage = CoverageTracker.coverage.entrySet().stream().map(it -> {
            var sb = new StringBuilder(it.getKey());
            for (Iterator<String> iter = it.getValue().descendingIterator(); iter.hasNext(); ) {
                var l = iter.next();
                sb.append(l);
            }
            return sb.toString();
        }).collect(Collectors.toSet());

        if (status.equals(Status.PASS) && !coveragesSeen.contains(newCoverage)) {
            population.add(input);
            coveragesSeen.add(newCoverage);
        }

        return Status.PASS;
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

    @Override
    public void reset() {
        super.reset();
//        coveragesSeen.clear();
        population = new ArrayList<>(defaultSeed);
    }

    public static void main(String[] args) {
        var fuzzer = new CgiFuzzer(defaultSeed, 1, 4);
        fuzzer.runs(100000);
    }
}

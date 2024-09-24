package slava0135.fuzz3.maze;

import java.util.Set;
import java.util.TreeSet;
import java.util.function.Function;
import java.util.stream.Collectors;

import slava0135.fuzz3.instrumentation.CoverageTracker;

public class FunctionRunner {
    private static final String PASS = "PASS";
    private static final String FAIL = "FAIL";
    private final Function<String, Object> function;
    public TreeSet<String> coverage;

    public FunctionRunner(Function<String, Object> function) {
        this.function = function;
    }

    public Object runFunction(String inp) {
        return function.apply(inp);
    }

    public Object runFunctionWithCoverage(String inp) {
        CoverageTracker.coverage.clear();
        coverage = CoverageTracker.coverage;
        return function.apply(inp);
    }

    public Set<Location> getCoverageAsLocations() {
        return coverage.stream().map(Location::buildFromString).collect(Collectors.toSet());
    }

    public Tuple<Object, String> run(String inp) {
        Object result;
        String outcome;
        try {
            result = runFunctionWithCoverage(inp);
            outcome = PASS;
        } catch (Throwable e) {
            result = null;
            outcome = FAIL;
        }
        return new Tuple<>(result, outcome);
    }

    public static class Tuple<T, U> {
        public final T first;
        public final U second;

        public Tuple(T first, U second) {
            this.first = first;
            this.second = second;
        }
    }

    public static void main(String[] args) {
        Function<String, Object> func = s -> {
            if (s.equals("error")) throw new RuntimeException("Test exception");
            return s.toUpperCase();
        };

        FunctionRunner runner = new FunctionRunner(func);
        Tuple<Object, String> result = runner.run("test");
        System.out.println("Result: " + result.first + ", Outcome: " + result.second);

        result = runner.run("error");
        System.out.println("Result: " + result.first + ", Outcome: " + result.second);
    }
}

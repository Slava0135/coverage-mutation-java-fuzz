package slava0135.fuzz3.maze;

import java.util.Set;
import java.util.TreeSet;
import java.util.function.Function;
import java.util.stream.Collectors;

import slava0135.fuzz3.Location;
import slava0135.fuzz3.instrumentation.CoverageTracker;

public class FunctionRunner {
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

    public Tuple<Object, Outcome> run(String inp) {
        Object result;
        Outcome outcome;
        try {
            result = runFunctionWithCoverage(inp);
            outcome = Outcome.PASS;
        } catch (Throwable e) {
            result = null;
            outcome = Outcome.FAIL;
        }
        return new Tuple<>(result, outcome);
    }

    public enum Outcome {
        PASS,
        FAIL
    } 

    public static class Tuple<T, U> {
        public final T first;
        public final U second;

        public Tuple(T first, U second) {
            this.first = first;
            this.second = second;
        }
    }
}

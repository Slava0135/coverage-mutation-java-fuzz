package slava0135.fuzz3.instrumentation;

import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;

public class CoverageTracker {
    public static final ConcurrentHashMap<String, TreeSet<String>> coverage = new ConcurrentHashMap<>();

    public static void logCoverage(String methodSignature, String lineNumber) {
        if (coverage.containsKey(methodSignature)) {
            coverage.get(methodSignature).add(lineNumber);
        } else {
            coverage.put(methodSignature, new TreeSet<>());
            coverage.get(methodSignature).add(lineNumber);
        }
    }
}
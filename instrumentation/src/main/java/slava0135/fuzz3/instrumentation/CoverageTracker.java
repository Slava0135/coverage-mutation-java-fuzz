package slava0135.fuzz3.instrumentation;

import java.util.TreeSet;

public class CoverageTracker {
    public static final TreeSet<String> coverage = new TreeSet<>();

    public static void logCoverage(String methodSignature, String lineNumber) {
        coverage.add(methodSignature + ":" + lineNumber);
    }
}
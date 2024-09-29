package slava0135.fuzz3.instrumentation;

import java.util.*;

public class CallGraph {
    public static Map<String, Set<String>> graph = new HashMap<>();
    public static Map<Edge, Integer> distance = new HashMap<>();
    
    public record Edge(String from, String to) {}

    public static void cacheDistance() {
        distance.clear();
        var allMethods = new HashSet<String>();
        graph.entrySet().forEach(it -> {
            allMethods.add(it.getKey());
            allMethods.addAll(it.getValue());
        });
        for (var from : allMethods) {
            for (var to : allMethods) {
                if (from == to) {
                    continue;
                }
                var checked = new HashSet<String>();
                var depth = 0;
                var queue = new ArrayDeque<String>();
                var nextQueue = new ArrayDeque<String>();
                var found = false;
                queue.add(from);
                while (!queue.isEmpty()) {
                    for (var next : queue) {
                        if (checked.add(next)) {
                            if (next == to) {
                                found = true;
                            } else {
                                nextQueue.addAll(graph.getOrDefault(next, Set.of()));
                            }
                        }
                    }
                    if (found) {
                        break;
                    }
                    queue = new ArrayDeque<>(nextQueue);
                    nextQueue.clear();
                    depth++;
                }
                if (found) {
                    distance.put(new Edge(from, to), depth);
                }
            }
        }
    }

    public static int getDistance(String from, String to) {
        return -1;
    }
}

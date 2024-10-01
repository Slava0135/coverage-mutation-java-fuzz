package slava0135.fuzz3.maze;

import java.util.Set;
import java.util.stream.Collectors;

import slava0135.fuzz3.Location;

public class PathIDGenerator {
    public int getPathID(Set<Location> coverage) {
        return coverage.stream()
            .filter(it -> it.function.startsWith("tile"))
            .map(it -> it.function + it.lineno)
            .collect(Collectors.toSet()).hashCode();
    }
}

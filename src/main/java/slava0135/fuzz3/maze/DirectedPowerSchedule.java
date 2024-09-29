package slava0135.fuzz3.maze;

import java.util.List;
import java.util.stream.Collectors;

import slava0135.fuzz3.instrumentation.CallGraph;

public class DirectedPowerSchedule extends PowerSchedule {
    private static final String target = "tile_5_7";
    private static final double maxDistance = 10000;

    @Override
    public void assignEnergy(List<Seed> population) {
        for (Seed seed : population) {
            var numDist = 0;
            var sumDist = 0;
            var functions = seed.coverage.stream().map(it -> it.function).collect(Collectors.toSet());
            for (var f : functions) {
                var dist = CallGraph.getDistance(f, target);
                if (dist > 0) {
                    sumDist += dist;
                    numDist++;
                }
            };
            if (numDist == 0) {
                seed.setDistance(maxDistance);
            } else {
                seed.setDistance(sumDist / numDist);
            }
            seed.setDistance(functions.stream().map(it -> CallGraph.getDistance(it, target)).min(Double::compare).orElseGet(() -> maxDistance));
            seed.setEnergy(Math.pow(1 / seed.getDistance(), 1.2)); 
        }
    }
}

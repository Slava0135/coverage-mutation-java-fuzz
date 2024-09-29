package slava0135.fuzz3.maze;

import java.util.List;
import java.util.stream.Collectors;

import slava0135.fuzz3.instrumentation.CallGraph;

public class AdvancedDirectedPowerSchedule extends PowerSchedule {
    private static final String target = "tile_5_7";
    private static final double maxDistance = 10000;
    private static final double eps = 0.000001;

    @Override
    public void assignEnergy(List<Seed> population) {
        var minDistance = maxDistance;
        var maxDistance = 0.0;
        for (var seed : population) {
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
            if (numDist > 0) {
                seed.setDistance((double) sumDist / numDist);
                minDistance = Math.min(minDistance, seed.getDistance());
                maxDistance = Math.max(maxDistance, seed.getDistance());
            } else {
                seed.setDistance(maxDistance);
            }
        }
        for (var seed : population) {
            if (Math.abs(seed.getDistance() - minDistance) <= eps) {
                if (Math.abs(maxDistance - minDistance) <= eps) {
                    seed.setEnergy(1);
                } else {
                    seed.setEnergy(maxDistance - minDistance);
                }
            } else {
                seed.setEnergy((maxDistance - minDistance) / (seed.getDistance() - minDistance));
            }
        }
    }
}

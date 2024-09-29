package slava0135.fuzz3.maze;

import java.util.List;

import slava0135.fuzz3.instrumentation.CallGraph;

public class DirectedGreyBoxMazeFuzzer {
    public static void main(String[] args) {
        var monitor = new MazeMonitor();
        var fuzzer = new AdvancedMutationFuzzer(
            List.of(""),
            new MazeMutator(),
            new DirectedPowerSchedule(),
            monitor,
            1, 3
        );
        var runner = new FunctionRunner(s -> {
            var res = MazeGenerated.maze(s);
            if (res.toLowerCase().contains("invalid")) {
                throw new IllegalArgumentException();
            }
            return res;
        });
        fuzzer.fuzz(runner, 10000);
        monitor.printStats();
        // System.out.println(CallGraph.getDistance("tile_1_1", "tile_5_7"));
    }
}

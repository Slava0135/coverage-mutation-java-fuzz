package slava0135.fuzz3.maze;

import java.util.List;

public class GreyBoxMazeFuzzer {
    public static void main(String[] args) {
        var monitor = new MazeMonitor();
        var fuzzer = new AdvancedMutationFuzzer(
            List.of(""),
            new MazeMutator(),
            new PowerSchedule(),
            monitor,
            1, 3
        );
        var runner = new FunctionRunner(s -> {
            return MazeGenerated.maze(s);
        });
        fuzzer.fuzz(runner, 100000);
        monitor.printStats();
    }
}

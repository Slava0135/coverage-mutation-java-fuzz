package slava0135.fuzz3.maze;

import java.util.List;

public class GreyBoxMazeFuzzer {
    public static void main(String[] args) {
        var fuzzer = new AdvancedMutationFuzzer(List.of(""), new MazeMutator(), new PowerSchedule(), 1, 3);
        final int[] trials = new int[1];
        var runner = new FunctionRunner(s -> {
            trials[0]++;
            var res = MazeGenerated.maze(s);
            if (res.contains("SOLVED")) {
                System.out.println("\n-- FOUND SOLUTION (trials: " + trials[0] + ")--\n'" + s + "'");
                System.exit(0);
            }
            if (res.contains("INVALID")) {
                throw new IllegalArgumentException();
            }
            return "OK";
        });
        fuzzer.fuzz(runner, 30000);
    }
}

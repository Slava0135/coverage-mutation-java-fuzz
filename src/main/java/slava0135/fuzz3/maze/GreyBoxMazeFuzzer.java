package slava0135.fuzz3.maze;

import java.util.List;

public class GreyBoxMazeFuzzer {
    public static void main(String[] args) {
        var fuzzer = new AdvancedMutationFuzzer(List.of(""), new MazeMutator(), new PowerSchedule(), 0, 0);
        var runner = new FunctionRunner(s -> {
            return switch (MazeGenerated.maze(s)) {
                case "INVALID" -> throw new IllegalArgumentException();
                default -> "OK";
            };
        });
        fuzzer.fuzz(runner, 10000);
    }
}

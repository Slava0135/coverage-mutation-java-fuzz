package slava0135.fuzz3.maze;

import java.util.List;

public class GreyBoxMazeFuzzer {
    public static void main(String[] args) {
        var fuzzer = new AdvancedMutationFuzzer(List.of(""), new MazeMutator(), new PowerSchedule(), 3, 5);
        var runner = new FunctionRunner(s -> {
            switch (MazeGenerated.maze(s)) {
                case "SOLVED" -> {
                    System.out.println("SOLVED: '" + s + "'");
                    System.exit(0);
                    throw new Error();
                }
                case "INVALID" -> throw new IllegalArgumentException();
                default -> {
                    return "OK";
                }
            }
        });
        fuzzer.fuzz(runner, 10000);
    }
}

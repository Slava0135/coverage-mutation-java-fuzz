package slava0135.fuzz3.maze;

import java.util.List;
import java.util.Random;

public class MazeMutator {
    private static final Random random = new Random();
    private static final List<String> dirs = List.of("U", "D", "L", "R");

    private static String randomDir() {
        return dirs.get(random.nextInt(4));
    }

    private static String append(String input) {
        return input + randomDir();
    }

    private static String deleteLast(String input) {
        return input.substring(0, Math.max(0, input.length() - 1));
    }

    private static String insert(String input) {
        if (input.isEmpty()) {
            return input;
        }
        var splitIndex = random.nextInt(input.length());
        return input.substring(0, splitIndex) + randomDir() + input.substring(splitIndex, input.length());
    }

    public String mutate(String candidate) {
        return switch (random.nextInt(3)) {
            case 0 -> append(candidate);
            case 1 -> deleteLast(candidate);
            default -> insert(candidate);
        };
    }
}

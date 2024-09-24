package slava0135.fuzz3.maze;

public class MazeRunner {

    String maze = """
                +-+-----+
                |X|     |
                | | --+ |
                | |   | |
                | +-- | |
                |     |#|
                +-----+-+
                """;
    public static void main(String[] args) {
        System.out.println(MazeGenerated.maze("D"));
    }
}

package slava0135.fuzz3.maze;

import slava0135.fuzz3.Monitor;

class MazeMonitor implements Monitor {

    int valid, invalid;
    boolean solved;
    String solution;

    @Override
    public boolean addResult(String input, Object result) {
        if (result == null) {
            invalid++;
            return false;
        }
        if (result instanceof String out) {
            if (out.toLowerCase().contains("valid")) {
                valid++;
                return false;
            } else if (out.toLowerCase().contains("solved")) {
                valid++;
                solved = true;
                solution = input;
                return true;
            } else {
                throw new IllegalArgumentException();
            }
        } else {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public void printStats() {
        System.out.println();
        if (solved) {
            System.out.printf(">> Maze was solved in %d tries (valid: %d, invalid: %d)\n", valid + invalid, valid, invalid);
            System.out.printf(">> Solution: '%s'\n", solution);
        } else {
            System.out.printf(">> Maze was NOT solved in %d tries (valid: %d, invalid: %d)\n", valid + invalid, valid, invalid);
        }
    }
    
}

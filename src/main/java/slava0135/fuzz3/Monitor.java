package slava0135.fuzz3;

public interface Monitor {
    // if true then stop fuzzing
    public boolean addResult(String input, Object result);
    public void printStats();
}

package slava0135.fuzz3.grammar;

public class Expansion {
    private final Pair<String, Option> expansionValue;

    public Expansion(Pair<String, Option> pairValue) {
        this.expansionValue = pairValue;
    }

    public Expansion(String stringValue) {
        this.expansionValue = new Pair<>(stringValue, null);
    }

    @Override
    public String toString() {
        return expansionValue.getKey();
    }
}

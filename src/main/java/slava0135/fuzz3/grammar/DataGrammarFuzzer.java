package slava0135.fuzz3.grammar;

public class DataGrammarFuzzer {
    public static void main(String[] args) {
        var grammar = DataGrammar.getBetterGrammar();
        var fuzzer = new GrammarFuzzer(grammar, "<document>", 10, 20, false, false);
        var tree = fuzzer.initTree();
        var expanded = fuzzer.expandTree(tree);
        System.out.println(expanded.treeToString());
    }
}

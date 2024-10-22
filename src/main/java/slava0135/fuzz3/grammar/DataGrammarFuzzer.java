package slava0135.fuzz3.grammar;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class DataGrammarFuzzer {
    public static void main(String[] args) throws IOException, InterruptedException {
        var grammar = DataGrammar.getBetterGrammar();
        var fuzzer = new GrammarFuzzer(grammar, "<document>", 100, 300, false, false);
        var i = 0;
        while (true) {
            i++;
            var tree = fuzzer.initTree();
            var expanded = fuzzer.expandTree(tree);

            var writer = new BufferedWriter(new FileWriter("/tmp/javafuzzingtmp.txt"));
            writer.write(expanded.treeToString());
            writer.close();

            var pb = new ProcessBuilder("java", "-jar", "target2.jar", "/tmp/javafuzzingtmp.txt");
            pb.redirectErrorStream(true);
            var process = pb.start();
            process.waitFor();

            var out = process.getInputStream();
            var result = new String(out.readAllBytes(), StandardCharsets.UTF_8);

            if (process.exitValue() != 0) {
                System.out.println(":: found bug " + "(in " + i + " tries)" + " ::");
                System.out.println(result);
                System.out.println(":: input data ::");
                System.out.println(expanded.treeToString());
                return;
            }
        }
    }
}

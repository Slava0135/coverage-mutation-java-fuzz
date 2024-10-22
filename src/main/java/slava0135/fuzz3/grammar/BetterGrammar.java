package slava0135.fuzz3.grammar;

import java.util.*;

public class BetterGrammar {

    public Map<String, List<Expansion>> rules = new HashMap<>();

    public BetterGrammar(LinkedHashMap<String, List<String>> grammarRules) {
        for (Map.Entry<String, List<String>> entry : grammarRules.entrySet()) {
            var expansions = entry.getValue().stream().map(Expansion::new).toList();
            rules.put(entry.getKey(), expansions);
        }
    }
}

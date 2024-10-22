package slava0135.fuzz3.grammar;

import java.util.*;

public class DataGrammar {

    // <document> ::= <element> | <element> <document>
    // <element> ::= <key_value> | <block> | <comment>
    // <key_value> ::= <key> ":" <value>
    // <key> ::= <identifier>
    // <value> ::= <string> | <number> | <boolean> | <array> | <block>
    // <block> ::= <key> "{" <elements> "}"
    // <elements> ::= <element> | <element> <elements>
    // <array> ::= "[" <array_elements> "]"
    // <array_elements> ::= <string> | <string> "," <array_elements>
    // <comment> ::= "#" <text>
    // <identifier> ::= <letter> | <letter> <identifier_part>
    // <identifier_part> ::= <letter> | <digit> | "_"
    // <string> ::= "\"" <characters> "\""
    // <characters> ::= <letter> | <letter> <characters>
    // <number> ::= <digit> | <digit> <number>
    // <boolean> ::= "true" | "false"
    // <letter> ::= "a" | "b" | ::. | "z" | "A" | "B" | ::. | "Z"
    // <digit> ::= "0" | "1" | "2" | ::. | "9"
    // <text> ::= <character> | <character> <text>

    public static final LinkedHashMap<String, List<String>> DATA_GRAMMAR = new LinkedHashMap<>() {
        {
            put("<document>", Arrays.asList("<element>", "<element>\n<document>"));
            put("<element>", Arrays.asList("<key_value>", "<block>", "<comment>"));
            put("<basic_element>", Arrays.asList("<key_value>", "<comment>"));
            put("<key_value>", Arrays.asList("<key>:<value>"));
            put("<key>", Arrays.asList("<identifier>"));
            put("<value>", Arrays.asList("<string>", "<number>", "<boolean>", "<array>", "<block>"));
            put("<block>", Arrays.asList("<key>{\n<basic_elements>\n}"));
            put("<elements>", Arrays.asList("<element>", "<element>\n<elements>"));
            put("<basic_elements>", Arrays.asList("<basic_element>", "<basic_element>\n<basic_elements>"));
            put("<array>", Arrays.asList("[<array_elements>]"));
            put("<array_elements>", Arrays.asList("<string>", "<string>,<array_elements>"));
            put("<comment>", Arrays.asList("#<text>"));
            put("<identifier>", Arrays.asList("<letter>", "<letter><identifier_part>"));
            put("<identifier_part>", Arrays.asList("<letter>", "<digit>", "_"));
            put("<string>", Arrays.asList("\"<characters>\""));
            put("<characters>", Arrays.asList("<letter>", "<letter><characters>"));
            put("<number>", Arrays.asList("<digit>", "<digit><number>"));
            put("<boolean>", Arrays.asList("true", "false"));
            put("<letter>",
                    Arrays.asList("A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q",
                            "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "a", "b", "c", "d", "e", "f", "g", "h", "i",
                            "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"));
            put("<digit>", Arrays.asList("0", "1", "2", "3", "4", "5", "6", "7", "8", "9"));
            put("<text>", Arrays.asList("<character>", "<character><text>"));
            put("<character>", Arrays.asList("<letter>", "<digit>"));
        }
    };

    public static BetterGrammar getBetterGrammar() {
        return new BetterGrammar(DATA_GRAMMAR);
    }
}

package slava0135.fuzz3;

import java.util.Random;

public class StringMutator {
    private static final Random random = new Random();

    public static String deleteRandomCharacter(String s) {
        if (s.isEmpty()) {
            return s;
        }
        int pos = random.nextInt(s.length());
        return s.substring(0, pos) + s.substring(pos + 1);
    }

    public static String insertRandomCharacter(String s) {
        int pos = random.nextInt(s.length() + 1);
        char randomCharacter = (char) (random.nextInt(96) + 32);
        return s.substring(0, pos) + randomCharacter + s.substring(pos);
    }

    public static String flipRandomCharacter(String s) {
        if (s.isEmpty()) {
            return s;
        }

        int pos = random.nextInt(s.length());
        char c = s.charAt(pos);
        int bit = 1 << random.nextInt(7);
        char newC = (char) (c ^ bit);
        return s.substring(0, pos) + newC + s.substring(pos + 1);
    }

    public static void main(String[] args) {
        String input = "example";
        String result = deleteRandomCharacter(input);
        System.out.println("Original string: " + input);
        System.out.println("Modified string: " + result);
    }
}

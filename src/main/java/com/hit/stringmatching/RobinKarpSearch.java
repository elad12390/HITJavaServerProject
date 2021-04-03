package main.java.com.hit.stringmatching;

import main.java.com.hit.stringmatching.abstracts.AbstractMatcher;

public class RobinKarpSearch extends AbstractMatcher {
    long bMult = 512;

    @Override
    public Integer match(String searchWord, String input) {
        Integer edgeCases = super.match(searchWord, input);
        if (edgeCases != null) return edgeCases;

        long hashedWord = 0;
        long highestPow = 1;
        for (Character c : searchWord.toCharArray()) {
            hashedWord *= bMult;
            hashedWord += c.hashCode();
            highestPow *= bMult;
        }

        long hashedSubText = 0;
        for (int i = 0; i <= input.length(); i++) {
            if (hashedSubText == hashedWord) {
                return i - searchWord.length();
            }
            if (i < input.length()) {
                if (i >= searchWord.length()) {
                    hashedSubText -= highestPow * (input.charAt(i - searchWord.length()));
                }
                hashedSubText *= bMult;
                hashedSubText += input.charAt(i);
            }
        }

        return -1;
    }


}

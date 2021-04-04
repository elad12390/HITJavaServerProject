package main.java.com.hit.stringmatching.implementations;

import main.java.com.hit.stringmatching.abstracts.AbstractAlgoMatcher;

public class RobinKarpAlgoMatcherImpl extends AbstractAlgoMatcher {
    long bMult = 512;
    long qModulo = 100000;

    @Override
    public Integer match(String searchWord, String input) {
        Integer edgeCases = super.match(searchWord, input);
        if (edgeCases != null) return edgeCases;

        long hashedWord = 0;
        long highestPow = 1;
        for (char c : searchWord.toCharArray()) {
            hashedWord *= bMult;
            hashedWord += c;
            highestPow *= bMult;
        }
        hashedWord %= qModulo;

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
                hashedSubText %= qModulo;
                hashedSubText += input.charAt(i);
                hashedSubText %= qModulo;
            }
        }

        return -1;
    }


}

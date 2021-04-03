package main.java.com.hit.stringmatching.implementations;

import main.java.com.hit.stringmatching.abstracts.AbstractAlgoMatcher;

public class KnuthMorrisPrattAlgoMatcherImpl extends AbstractAlgoMatcher {
    private int[] lpsArray(String pat, int[] lps) {
        int patLen = pat.length();
        int lenLongestPs = 0;
        int i = 1;
        lps[0] = 0;
        while (i < patLen) {
            if (pat.charAt(i) == pat.charAt(lenLongestPs)) {
                lenLongestPs++;
                lps[i] = lenLongestPs;
                i++;
            } else {
                if (lenLongestPs != 0) {
                    lenLongestPs = lps[lenLongestPs - 1];
                } else {
                    lps[i] = lenLongestPs;
                    i++;
                }
            }

        }
        return lps;
    }

    @Override
    public Integer match(String searchWord, String input) {
        Integer edgeCases = super.match(searchWord, input);
        if (edgeCases != null) return edgeCases;
        int S = searchWord.length();
        int N = input.length();
        int[] lps = new int[S];
        lpsArray(searchWord, lps);
        int j = 0, i = 0;
        while (i < N) {
            if (searchWord.charAt(j) == input.charAt(i)) {
                j++;
                i++;
            }
            if (j == S) {
                j = lps[j - 1];
            } else if (i < N && searchWord.charAt(j) != input.charAt(i)) {
                if (j != 0) {
                    j = lps[j - 1];
                } else {
                    i = i + 1;
                }
            }
        }
        return -1;
    }
}

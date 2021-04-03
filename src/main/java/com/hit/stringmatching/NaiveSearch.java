package main.java.com.hit.stringmatching;
import main.java.com.hit.stringmatching.abstracts.AbstractMatcher;

public class NaiveSearch extends AbstractMatcher {
    @Override
    public Integer match(String searchWord, String input) {
        Integer edgeCases = super.match(searchWord, input);
        if (edgeCases != null) return edgeCases;

        for (int i = 0; i < input.length(); i++) {
            int j;
            for (j = 0; (i + j) < searchWord.length(); j++) {
                if (input.charAt(i+j) != searchWord.charAt(j)) {
                    // location i is wrong.
                    break;
                }
            }
            if (j == searchWord.length()) {
                // found word at index i
                return i;
            }
        }

        // did not find word
        return -1;
    }
}

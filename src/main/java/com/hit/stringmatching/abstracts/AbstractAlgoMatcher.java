package main.java.com.hit.stringmatching.abstracts;

import main.java.com.hit.stringmatching.interfaces.IAlgoStringMatcher;

public abstract class AbstractAlgoMatcher implements IAlgoStringMatcher {
    public Integer match(String searchWord, String input) {
        if (input.length() < searchWord.length()) {
            return -1;
        } else if (input.length() == searchWord.length()) {
            return input.equals(searchWord) ? 0 : -1;
        }

        return null;
    }
}
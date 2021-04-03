package main.java.com.hit.stringmatching.abstracts;
import main.java.com.hit.stringmatching.interfaces.IStringMatcher;

public abstract class AbstractMatcher implements IStringMatcher {
    public Integer match(String searchWord, String input) {
        if (input.length() < searchWord.length()) {
            return -1;
        } else if (input.length() == searchWord.length()) {
            return input.equals(searchWord) ? 0 : -1;
        }

        return null;
    }
}
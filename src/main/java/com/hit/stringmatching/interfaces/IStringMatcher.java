package main.java.com.hit.stringmatching.interfaces;

public interface IStringMatcher {
    /**
     * Function to return a matching index of the searchWord in the input string
     *
     * @param searchWord The word to search for
     * @param input      The input text to search in
     * @return The index of the first occurrence of the search word
     */
    Integer match(String searchWord, String input); // searchWord="elad" input="fdslakjaskjdfkjasehdelad"
}

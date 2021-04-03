package test;

import main.java.com.hit.stringmatching.KmpSearch;
import main.java.com.hit.stringmatching.NaiveSearch;
import main.java.com.hit.stringmatching.RobinKarpSearch;
import main.java.com.hit.stringmatching.interfaces.IStringMatcher;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class StringMatchTester {
    private final IStringMatcher stringMatcher;

    @Parameterized.Parameters
    public static Collection<IStringMatcher> stringMatchers() {
        List<IStringMatcher> list = new ArrayList<>();
        list.add(new KmpSearch());
        list.add(new NaiveSearch());
        list.add(new RobinKarpSearch());
        return list;
    }

    public StringMatchTester(IStringMatcher matcher) {
        this.stringMatcher = matcher;
    }

    @Test
    public void testStringMatcher_DoesNotExist() {
        Integer result = stringMatcher.match("ama", "obam");
        assertEquals((Integer)(-1), result);
    }

    @Test
    public void testStringMatcher_WordLargerThanInput() {
        var wordLen = Math.abs(new Random().nextInt(10000));

        String input = generateRandomString(wordLen);
        String searchWord = generateRandomString(wordLen + 1);
        Integer result = stringMatcher.match(searchWord,  input);
        assertEquals((Integer)(-1), result);
    }

    @Test
    public void testStringMatcher_wordExactlySameLength() {
        var wordLen = Math.abs(new Random().nextInt(10000));

        String input = generateRandomString(wordLen);
        String searchWord = generateRandomString(wordLen);

        Integer result = stringMatcher.match(searchWord,  input);
        assertEquals((Integer)input.indexOf(searchWord), result);
    }

    @Test
    public void testStringMatcher_RandomString() {
        String input = generateRandomString(Math.abs(new Random().nextInt(10000)));
        String searchWord = generateRandomString(Math.abs(new Random().nextInt(100)));

        Integer result = stringMatcher.match(searchWord,  input);
        assertEquals((Integer)input.indexOf(searchWord), result);
    }

    private String generateRandomString(int length) {
        int leftLimit = 'a';
        int rightLimit = 'z';
        Random random = new Random();

        return random.ints(leftLimit, rightLimit + 1)
                .limit(length)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }
}

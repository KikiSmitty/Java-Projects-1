import static org.junit.Assert.assertEquals;

import org.junit.Test;

import components.set.Set;
import components.set.Set1L;

public class StringReassemblyTest {

    @Test
    public void testCombination_1() {
        String str1 = "abcde";
        String str2 = "cdefg";
        int overlap = 3;
        String answer = StringReassembly.combination(str1, str2, overlap);
        assertEquals(answer, "abcdefg");
    }

    public void testCombination_2() {
        String str1 = "Hello my name is Kiki";
        String str2 = "ki how are you today";
        int overlap = 2;
        String answer = StringReassembly.combination(str1, str2, overlap);
        assertEquals(answer, "Hello my name is Kiki how are you today");
    }

    public void testCombination_3() {
        String str1 = "Sunday is my favorite day of the week";
        String str2 = "and I hate Mondays";
        int overlap = 0;
        String answer = StringReassembly.combination(str1, str2, overlap);
        assertEquals(answer,
                "Sunday is my favorite day of the week and I hate Mondays ");
    }

    public void testAddToSetAvoidingSubstrings_1() {
        Set<String> temp = new Set1L<>();
        temp.add("basketball");
        temp.add("ball");
        String test = "baseball";

        Set<String> answer = new Set1L<>();
        answer.add("basketball");
        answer.add("baseball");

        StringReassembly.addToSetAvoidingSubstrings(temp, test);
        assertEquals(temp, answer);
    }

    public void testAddToSetAvoidingSubstrings_2() {
        Set<String> temp = new Set1L<>();
        temp.add("Hey");
        temp.add("Kierra");
        String test = "Kierra Smith";

        Set<String> answer = new Set1L<>();
        answer.add("Hey");
        answer.add("Kierra Smith");

        StringReassembly.addToSetAvoidingSubstrings(temp, test);
        assertEquals(temp, answer);
    }
}

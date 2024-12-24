import java.util.Comparator;

import components.map.Map;
import components.map.Map1L;
import components.queue.Queue;
import components.queue.Queue1L;
import components.set.Set;
import components.set.Set1L;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * Glossary project that outputs multiple html files for each word.
 *
 * @author Kierra Smith
 *
 */
public final class Glossary {

    /**
     * No argument constructor--private to prevent instantiation.
     */
    private Glossary() {
    }

    /**
     * Returns the first "word" (maximal length string of characters not in
     * {@code separators}) or "separator string" (maximal length string of
     * characters in {@code separators}) in the given {@code text} starting at
     * the given {@code position}.
     *
     * @param text
     *            the {@code String} from which to get the word or separator
     *            string
     * @param position
     *            the starting index
     * @param separators
     *            the {@code Set} of separator characters
     * @return the first word or separator string found in {@code text} starting
     *         at index {@code position}
     * @requires 0 <= position < |text|
     * @ensures <pre>
     * nextWordOrSeparator =
     *   text[position, position + |nextWordOrSeparator|)  and
     * if entries(text[position, position + 1)) intersection separators = {}
     * then
     *   entries(nextWordOrSeparator) intersection separators = {}  and
     *   (position + |nextWordOrSeparator| = |text|  or
     *    entries(text[position, position + |nextWordOrSeparator| + 1))
     *      intersection separators /= {})
     * else
     *   entries(nextWordOrSeparator) is subset of separators  and
     *   (position + |nextWordOrSeparator| = |text|  or
     *    entries(text[position, position + |nextWordOrSeparator| + 1))
     *      is not subset of separators)
     * </pre>
     */
    private static String nextWordOrSeparator(String text, int position,
            Set<Character> separators) {
        assert text != null : "Violation of: text is not null";
        assert separators != null : "Violation of: separators is not null";
        assert 0 <= position : "Violation of: 0 <= position";
        assert position < text.length() : "Violation of: position < |text|";
        int end = position;
        //runs if character is a separator in the set
        if (separators.contains((text.charAt(position)))) {
            //runs to increment end
            while (text.length() != end
                    && separators.contains(text.charAt(end))) {
                end++;
            }
            // runs if character is not in separator set
        } else {
            //runs to increment end
            while (text.length() != end
                    && !separators.contains(text.charAt(end))) {
                end++;
            }
        }
        //return text
        return text.substring(position, end);
    }

    /**
     * Comparator to enable the usage of sort for following methods.
     *
     */
    private static class StringLT implements Comparator<String> {
        @Override
        public int compare(String o1, String o2) {
            return o1.compareTo(o2);
        }
    }

    /**
     * Makes a queue of keys and sorts the keys alphabetically.
     *
     * @param wordAndDef
     *            map used to store word paired with respective definition
     * @return keys are sorted alphabetically
     */
    private static Queue<String> sort(Map<String, String> wordAndDef) {
        Queue<String> order = new Queue1L<>();
        Comparator<String> aToZ = new StringLT();
        //every pair in map and adding the words from wordAndDef to order
        for (Map.Pair<String, String> wordPair : wordAndDef) {
            order.enqueue(wordPair.key());
        }
        //sort the order in alphabetical order
        order.sort(aToZ);
        //return order
        return order;
    }

    /**
     * Generates the set of characters in the given {@code String} into the
     * given {@code Set}.
     *
     * @param str
     *            the given {@code String}
     * @param charSet
     *            the {@code Set} to be replaced
     * @replaces charSet
     * @ensures charSet = entries(str)
     */
    private static void generateElements(String str, Set<Character> charSet) {
        assert str != null : "Violation of: str is not null";
        assert charSet != null : "Violation of: charSet is not null";
        for (int i = 0; i < str.length(); i++) {
            if (!charSet.contains(str.charAt(i))) {
                charSet.add(str.charAt(i));
            }
        }
    }

    /**
     * Generates a html file for each term.
     *
     * @param word
     *            word used to create the html file
     * @param wordAndDef
     *            map used to store word paired with respective definition
     * @param fileName
     *            file name input by user
     * @ensures that each term has an html file
     */
    private static void files(String word, Map<String, String> wordAndDef,
            String fileName) {
        SimpleWriter fileOut = new SimpleWriter1L(word + ".html");
        Set<Character> separatorSet = new Set1L<>();
        final String separatorStr = " \t, .";
        generateElements(separatorStr, separatorSet);
        int position = 0;
        //print out html elements
        fileOut.println("<html>");
        fileOut.println("<head>");
        fileOut.println("<title>" + word + "</title>");
        fileOut.println("</head>");
        fileOut.println("<body>");
        fileOut.println("<h2><b><i><font color=\"red\">" + word
                + "</font></i></b></h2>");
        fileOut.print("<blockquote>");
        //while loop creates a hyperlink for each word that appears in a definition
        while (position < wordAndDef.value(word).length()) {
            String strName = nextWordOrSeparator(wordAndDef.value(word),
                    position, separatorSet);
            if (wordAndDef.hasKey(strName)) {
                fileOut.print("<a href= \"" + strName + ".html\">" + strName
                        + "</a>");
            } else {
                fileOut.print(strName);
            }
            position += strName.length();
        }
        fileOut.println("</blockquote>");
        fileOut.println("<hr />");
        fileOut.println(
                "<p> Return to <a href= \"" + fileName + "\">index</a>.</p>");
        fileOut.println("</body>");
        fileOut.println("</html>");
        fileOut.close();

    }

    /**
     * prints the ordered list in the html file.
     *
     * @param keys
     *            queue that stores the words in wordsAndDef alphabetically
     * @param wordAndDef
     *            map used to store word paired with respective definition
     * @param fileName
     *            file name input by user
     * @param html
     *            html file that everything prints to
     * @ensures that each term has an html file
     */
    private static void printOrderedList(Queue<String> keys, String fileName,
            Map<String, String> wordAndDef, SimpleWriter html) {
        //while loop prints each word in alphabet order and hyperlinks it
        while (keys.length() > 0) {
            String printList = keys.dequeue();
            files(printList, wordAndDef, fileName);
            html.println("<li><a href=\"" + printList + ".html\">" + printList
                    + "</a></li>");
        }
    }

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments
     */
    public static void main(String[] args) {
        //initialize variables and ask for user inputs
        SimpleReader in = new SimpleReader1L();
        SimpleWriter out = new SimpleWriter1L();
        out.println("input a txt file for the glossary: ");
        String file = in.nextLine();
        SimpleReader readFile = new SimpleReader1L(file);
        out.println("input a name for html file: ");
        String fileName = in.nextLine();
        SimpleWriter html = new SimpleWriter1L(fileName);
        Queue<String> keys = new Queue1L<>();
        Map<String, String> wordAndDef = new Map1L<>();
        //goes through each word and definition in the file and adds them to map
        while (!readFile.atEOS()) {
            String word = readFile.nextLine();
            String definition = readFile.nextLine() + " ";
            String guess = readFile.nextLine();
            while (!guess.equals("")) {
                definition = definition + guess + " ";
                guess = readFile.nextLine();
            }
            wordAndDef.add(word, definition);
        }
        //sorting the keys
        keys = sort(wordAndDef);
        //html code
        html.println("<html><head><title> Glossary </title>");
        html.println("</head><body>");
        html.println("<h1> Glossary </h1>");
        html.println("<h2> Index </h2>");
        html.println("<ul>");
        //method prints the list in alphabetical order
        printOrderedList(keys, fileName, wordAndDef, html);
        html.println("</ul>");
        html.println("</body>");
        html.println("</html>");
        html.close();
        //close streams
        readFile.close();
        in.close();
        out.close();
    }

}

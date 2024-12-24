import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;
import components.utilities.Reporter;
import components.xmltree.XMLTree;
import components.xmltree.XMLTree1;

/**
 * Put a short phrase describing the program here.
 *
 * @author Kierra Smith
 *
 */
public final class XMLTreeIntExpressionEvaluator {

    /**
     * No argument constructor--private to prevent instantiation.
     */
    private XMLTreeIntExpressionEvaluator() {
    }

    /**
     * Evaluate the given expression.
     *
     * @param exp
     *            the {@code XMLTree} representing the expression
     * @return the value of the expression
     * @requires <pre>
     * [exp is a subtree of a well-formed XML arithmetic expression]  and
     *  [the label of the root of exp is not "expression"]
     * </pre>
     * @ensures evaluate = [the value of the expression]
     */
    private static int evaluate(XMLTree exp) {
        //initialize variables
        int total = 0;
        //base case is the attribute that equals "value"
        if (exp.hasAttribute("value")) {
            //give total the new value of attribute "value"
            total = Integer.parseInt(exp.attributeValue("value"));
            // if statement to apply addition to the equation
        } else if (exp.label().equals("plus")) {
            total = evaluate(exp.child(0)) + evaluate(exp.child(1));
            // if statement to apply subtraction to the equation
        } else if (exp.label().equals("minus")) {
            total = evaluate(exp.child(0)) - evaluate(exp.child(1));
            // if statement to apply division to the equation
        } else if (exp.label().equals("divide")) {
            //if statement to send an error message if there is division by zero
            if (evaluate(exp.child(1)) == 0) {
                Reporter.fatalErrorToConsole("Can't divide by zero");
            }
            total = evaluate(exp.child(0)) / evaluate(exp.child(1));
            // if statement to apply multiplication to the equation
        } else if (exp.label().equals("times")) {
            total = evaluate(exp.child(0)) * evaluate(exp.child(1));
        }
        //return the total
        return total;

    }

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments
     */
    public static void main(String[] args) {
        SimpleReader in = new SimpleReader1L();
        SimpleWriter out = new SimpleWriter1L();

        //Ask for XML file
        out.print("Input an xml tree file: ");
        String file = in.nextLine();
        XMLTree xml = new XMLTree1(file);

        //print the answer to the equation
        out.print(evaluate(xml.child(0)));

        in.close();
        out.close();
    }

}

import components.naturalnumber.NaturalNumber;
import components.naturalnumber.NaturalNumber1L;
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
public final class XMLTreeNNExpressionEvaluator {

    /**
     * No argument constructor--private to prevent instantiation.
     */
    private XMLTreeNNExpressionEvaluator() {
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
    private static NaturalNumber evaluate(XMLTree exp) {
        NaturalNumber total = new NaturalNumber1L(0);
        NaturalNumber zero = new NaturalNumber1L(0);

        if (exp.hasAttribute("value")) {
            //give total the new value of attribute "value"
            total = new NaturalNumber1L((exp.attributeValue("value")));
            // if statement to apply addition to the equation
        } else if (exp.label().equals("plus")) {
            NaturalNumber expression1 = new NaturalNumber1L(
                    evaluate(exp.child(0)));
            NaturalNumber expression2 = new NaturalNumber1L(
                    evaluate(exp.child(1)));
            expression1.add(expression2);
            total.copyFrom(expression1);
            // if statement to apply subtraction to the equation
        } else if (exp.label().equals("minus")) {
            NaturalNumber expression1 = new NaturalNumber1L(
                    evaluate(exp.child(0)));
            NaturalNumber expression2 = new NaturalNumber1L(
                    evaluate(exp.child(1)));
            if (expression1.compareTo(expression2) < 0) {
                Reporter.fatalErrorToConsole(
                        "Natural Numbers cannot be negative.");
            }
            expression1.subtract(expression2);
            total.copyFrom(expression1);
            // if statement to apply division to the equation
        } else if (exp.label().equals("divide")) {
            NaturalNumber expression1 = new NaturalNumber1L(
                    evaluate(exp.child(0)));
            NaturalNumber expression2 = new NaturalNumber1L(
                    evaluate(exp.child(1)));
            //if statement to send an error message if there is division by zero
            if (expression2.equals(zero)) {
                Reporter.fatalErrorToConsole("Can't divide by zero");
            }
            expression1.divide(expression2);
            total.copyFrom(expression1);

            // if statement to apply multiplication to the equation
        } else if (exp.label().equals("times")) {
            NaturalNumber expression1 = new NaturalNumber1L(
                    evaluate(exp.child(0)));
            NaturalNumber expression2 = new NaturalNumber1L(
                    evaluate(exp.child(1)));
            expression1.multiply(expression2);
            total.copyFrom(expression1);
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

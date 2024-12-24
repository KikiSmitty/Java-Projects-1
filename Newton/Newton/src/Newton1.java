import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * Put a short phrase describing the program here.
 *
 * @author Kierra Smith
 *
 */
public final class Newton1 {

    /**
     * No argument constructor--private to prevent instantiation.
     */
    private Newton1() {
    }

    /**
     * Put a short phrase describing the static method sqrt here.
     *
     * @param x
     *            user input that the square root function is being used on
     *
     * @return returns the square root of the input
     */
    private static double sqrt(double x) {

        double r = x; // initializes r to the user input in main method

        final double error = 0.001; //initializes error to the value of 0.0001

        /*
         * while loop plugs user input into an equation and if the result of
         * that equation is greater that 0.0001, then the equation will continue
         * to iterate until it is less than error. When the equation in the
         * while loop is less than 0.0001, then the while loop will terminate
         * and return r, which is the the square root of the user input.
         */
        while ((Math.abs(Math.pow(r, 2) - x) / x) > error) {
            r = (r + (x / r)) / 2;

        }
        return r;
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
        /*
         * Put your main program code here; it may call sqrt as shown
         */
        boolean ask = true; //boolean setting ask to true

        /*
         * while loop that sets ask to false and terminates the program if the
         * user inputs anything other than 'y' in the first input.
         *
         * nested if statement that determines the boolean value of ask, if
         * answer is not equal to the string 'y' then ask will be set to False.
         * Else the program will ask the user to input a number to be sqaure
         * rooted. This process will continue until the user inputs anything
         * other than 'y'.
         *
         */

        while (ask) {
            out.println("Do you wish to calculate a square root?: ");
            String answer = in.nextLine();
            if (!answer.equals("y")) {
                ask = false;
            } else {
                out.println("Enter a number: ");
                double num = in.nextDouble();

                out.println(sqrt(num));
            }
        }

        /*
         * Close input and output streams
         */
        in.close();
        out.close();
    }

}

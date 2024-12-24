import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;
import components.utilities.FormatChecker;

/**
 * Put a short phrase describing the program here.
 *
 * @author Put your name here
 *
 */
public final class ABCDGuesser1 {

    /**
     * No argument constructor--private to prevent instantiation.
     */
    private ABCDGuesser1() {
    }

    /**
     * Repeatedly asks the user for a positive real number until the user enters
     * one. Returns the positive real number.
     *
     * @param in
     *            the input stream
     * @param out
     *            the output stream
     * @return a positive real number entered by the user
     */
    private static double getPositiveDouble(SimpleReader in, SimpleWriter out) {
        String mu = "";
        double convertedMu = 0.0;
        boolean ask = true;
        //while loop set to a true boolean to continue asking for a positive real number
        while (ask) {
            out.print("Enter a positive real number: ");
            mu = in.nextLine();
            //format checker will check if mu is a real number
            if (FormatChecker.canParseDouble(mu)) {
                //mu to convert it back into a double
                convertedMu = Double.parseDouble(mu);

                //set the boolean to false to end the main while loop
                if (convertedMu > 0) {
                    ask = false;
                }
            }
        }
        //return double mu
        return convertedMu;
    }

    /**
     * Repeatedly asks the user for a positive real number not equal to 1.0
     * until the user enters one. Returns the positive real number.
     *
     * @param in
     *            the input stream
     * @param out
     *            the output stream
     * @return a positive real number not equal to 1.0 entered by the user
     */
    private static double getPositiveDoubleNotOne(SimpleReader in,
            SimpleWriter out) {

        double userInput = 0.0;
        boolean ask = true;
        //while loop set to a true boolean to continue asking for a positive real number
        while (ask) {
            //use previous method to find a positive real number
            userInput = getPositiveDouble(in, out);
            //these inputs cannot be 1, set boolean to false
            if (userInput != 1) {
                ask = false;
            }
        }
        return userInput;
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
         * Put your main program code here; it may call myMethod as shown
         */

        final double[] exponents = { -5.0, -4.0, -3.0, -2.0, -1.0, -0.5, -0.33,
                -0.25, 0.0, 0.25, 0.33, 0.5, 1.0, 2.0, 3.0, 4.0, 5.0 };
//collecting values
        double mu = getPositiveDouble(in, out);
        double w = getPositiveDoubleNotOne(in, out);
        double x = getPositiveDoubleNotOne(in, out);
        double y = getPositiveDoubleNotOne(in, out);
        double z = getPositiveDoubleNotOne(in, out);
        //defining variables for calculations
        double calculation = 0.0;
        double error = 0.0;
        final double hundred = 100.00;
        //increment variables
        int a = 0, b = 0, c = 0, d = 0;
        //initialize best answers for final print
        double bestA = 0.0, bestB = 0.0, bestC = 0.0, bestD = 0.0;
        double bestGuess = 0.0;

        //iterate through all possible values of a in exponent array
        while (a < exponents.length) {
            b = 0;
            //iterate through all possible values of b in exponent array
            while (b < exponents.length) {
                c = 0;
                //iterate through all possible values of c in exponent array
                while (c < exponents.length) {
                    d = 0;
                    //iterate through all possible values of d in exponent array
                    while (d < exponents.length) {
                        //performs main calculation
                        calculation = Math.pow(w, exponents[a])
                                * Math.pow(x, exponents[b])
                                * Math.pow(y, exponents[c])
                                * Math.pow(z, exponents[d]);
                        //if statement so save the best calculations
                        if ((Math.abs(mu - calculation)) < Math
                                .abs(mu - bestGuess)) {
                            bestGuess = calculation;
                            bestA = exponents[a];
                            bestB = exponents[b];
                            bestC = exponents[c];
                            bestD = exponents[d];
                        }
                        d++;
                    }
                    c++;
                }
                b++;
            }
            a++;
        }
        //print out all calculations
        error = Math.abs(bestGuess - mu) / mu * hundred;
        out.println("best a: " + bestA + " best b: " + bestB + " best c: "
                + bestC + " best d: " + bestD);
        out.println("The value of deJager formula is: " + bestGuess);
        out.print("Relative error of approx is: ");
        out.print(error, 2, false);

        /*
         * Close input and output streams
         */
        in.close();
        out.close();
    }

}

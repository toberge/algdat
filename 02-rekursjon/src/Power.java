/**
 * Disclaimer: These methods will fail with negative n
 */

public class Power {

    /**
     * Oppgave 2.1-1
     *
     * Linear recursion:<br>
     * Call to self(n-1) means the complexity is &Theta;(n)
     *
     * @param x base
     * @param n exponent
     * @return power of x to the nth
     */
    public static double simplePow(double x, int n) {
        if (n == 0) return 1;
        else return x * simplePow(x, n - 1);
    }

    /**
     * Oppgave 2.2-3
     *
     * Approximately:
     * <code>
     * T(n) = {
     *     for n > 0:   T((n-0.5) / 2) + 3
     *     for n = 0:   T(0) = 2
     * }
     * </code>
     * Which means n is halved in each call while n > 0.
     *
     * From the formula for recursive complexity<br>
     * I get that the complexity is &Theta;(log(n))<br>
     * which is a serious improvement.<br>
     *
     * @param x base
     * @param n exponent
     * @return power of x to the nth
     */
    public static double splitPow(double x, int n) {
        if (n == 0) return 1;
        else if (n % 2 == 0) return
                splitPow(x*x, (int) (n / 2.0/* + 0.5*/)); // partall
        else return x *
                splitPow(x*x, (int) ((n-1) / 2.0/* + 0.5*/)); // oddetall (NB VI HAR SKILT UT ODDE/PAR så + 0.5 er totalt unødvendig)
    }
}

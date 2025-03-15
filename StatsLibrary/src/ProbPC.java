import java.math.BigInteger;

public class ProbPC {
    private int n;
    private int r;

    public ProbPC() {
        n = 1;
        r = 1;
    }
    public void setNV(int n, int r) {
        this.n = n;
        this.r = r;

    }
    /*
    public int Factorial(int n) {
        if (n == 0) {
            return 1;
        } else {
            return n * Factorial(n - 1);
        }
    }   Not good or efficient
    */

    public BigInteger factorial ( int num){
            BigInteger result = BigInteger.ONE;
            for (int i = 2; i <= num; i++) {
                result = result.multiply(BigInteger.valueOf(i));
            }
            return result;
    }


    public BigInteger permutation () {
            return factorial(n).divide(factorial(n - r));
    }

    public BigInteger combination () {
            return factorial(n).divide(factorial(r).multiply(factorial(n - r)));
    }

}
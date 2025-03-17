import java.util.Arrays;
public class StatsLibrary {


    //using mean, mode and median formulas for the calculations
    public double getMean(int[] userInputNumbers) {
        if (userInputNumbers == null || userInputNumbers.length == 0) return Double.NaN;
        double total = 0;
        for (int singleNum : userInputNumbers) {
            total += singleNum;
        }
        return total / userInputNumbers.length;
    }

    public double getMedian(int[] userInputNumbers) {
        if (userInputNumbers == null || userInputNumbers.length == 0) return Double.NaN;

        Arrays.sort(userInputNumbers);
        if (userInputNumbers.length % 2 == 0) {
            int l = userInputNumbers.length / 2 - 1;
            int m = userInputNumbers.length / 2;
            return (double) (userInputNumbers[l] + userInputNumbers[m]) / 2;
        } else {
            return userInputNumbers[userInputNumbers.length / 2];
        }
    }

    public double getMode(int[] userInputNumbers) {
        if (userInputNumbers == null || userInputNumbers.length == 0) return Double.NaN;

        Arrays.sort(userInputNumbers);
        int count = 1;
        int maxCount = 1;
        double mode = userInputNumbers[0];
        boolean hasMode = false;

        for (int i = 1; i < userInputNumbers.length; i++) {
            if (userInputNumbers[i] == userInputNumbers[i - 1]) {
                count++;
                if (count > maxCount) {
                    maxCount = count;
                    mode = userInputNumbers[i];
                    hasMode = true;
                }
            } else {
                count = 1;
            }
        }

        return hasMode ? mode : Double.NaN; // Returns NaN if no mode exists
    }

    public double getSampleStandardDeviation(int[] theDataset) {
        if (theDataset == null || theDataset.length <= 1) return Double.NaN;
        double mean = getMean(theDataset);
        double sum = 0;

        for (int num : theDataset) {
            double xVar = num - mean;
            sum += Math.pow(xVar, 2);
        }
        return Math.sqrt(sum / (theDataset.length - 1));
    }

    public double getPopulationStandardDeviation(int[] theDataset) {
        if (theDataset == null || theDataset.length == 0) return Double.NaN;
        double mean = getMean(theDataset);
        double sum = 0;

        for (int num : theDataset) {
            double xVar = num - mean;
            sum += Math.pow(xVar, 2);
        }
        return Math.sqrt(sum / theDataset.length);
    }

    // does not use BigInteger like PC class as long is sufficient for this class
    public static long factorial(int n) {
        if (n < 0) return 0;
        long result = 1;
        for (int i = 2; i <= n; i++) {
            result *= i;
        }
        return result;
    }

    public static long combination(int n, int r) {
        if (n < 0 || r < 0 || r > n) return 0;
        return factorial(n) / (factorial(r) * factorial(n - r));
    }

    public static long permutation(int n, int r) {
        if (n < 0 || r < 0 || r > n) return 0;
        return factorial(n) / factorial(n - r);
    }

    // Multiplicative law of probability for independent events: P(A ∩ B) = P(A) * P(B)
    public static double multiplicativeLawIndependent(double probA, double probB) {
        if (probA < 0 || probA > 1 || probB < 0 || probB > 1) return Double.NaN;
        return probA * probB;
    }

    // Additive law of probability for mutually exclusive events: P(A ∪ B) = P(A) + P(B)
    public static double additiveLawMutuallyExclusive(double probA, double probB) {
        if (probA < 0 || probA > 1 || probB < 0 || probB > 1) return Double.NaN;
        double result = probA + probB;
        return (result > 1) ? Double.NaN : result;
    }

    // Bayes' Theorem: P(A|B) = P(B|A) * P(A) / P(B)
    public static double bayesTheorem(double probBgivenA, double probA, double probB) {
        if (probB == 0 || probA < 0 || probA > 1 || probB < 0 || probB > 1 || probBgivenA < 0 || probBgivenA > 1)
            return Double.NaN;
        return (probBgivenA * probA) / probB;
    }
    public static double binomialPMF(int n, int k, double p) {
        if (n < 0 || k < 0 || k > n || p < 0 || p > 1) return Double.NaN;
        return combination(n, k) * Math.pow(p, k) * Math.pow(1 - p, n - k);
    }
    public static double geometricProbability(int k, double p) {
        if (k < 1 || p < 0 || p > 1) return Double.NaN;
        return Math.pow(1 - p, k - 1) * p;
    }

    public static double conditionalProbability(double probAandB, double probB) {
        if (probB == 0 || probAandB < 0 || probB < 0 || probAandB > 1 || probB > 1) return -1;
        return probAandB / probB;
    }
    public static double additiveLaw(double probA, double probB, double probIntersectB) {
        if (probA < 0 || probA > 1 || probB < 0 || probB > 1 || probIntersectB < 0 || probIntersectB > 1) return -1;
        return probA + probB - probIntersectB;
    }
    public double binomialExpectedValue(int n, double p) {
        return n * p;
    }

    public double binomialVariance(int n, double p) {
        return n * p * (1 - p);
    }

    // Geometric Distribution (trials until first success)
    public double geometricExpectedValue(double p) {
        return 1 / p;
    }

    public double geometricVariance(double p) {
        return (1 - p) / (p * p);
    }

    public static double negativeBinomial(int r, int x, double p) {
        return combination(x - 1, r - 1) * Math.pow(p, r) * Math.pow(1 - p, x - r);
    }
    // Negative Binomial Distribution (trials until r-th success)
    public double negativeBinomialExpectedValue(int r, double p) {
        return r / p;
    }

    public double negativeBinomialVariance(int r, double p) {
        return r * (1 - p) / (p * p);
    }

    public static double hypergeometric(int N, int K, int n, int k) {
        return (double) (combination(K, k) * combination(N - K, n - k)) / combination(N, n);
    }
    // Hypergeometric Distribution (N=population, K=successes, n=sample)
    public double hypergeometricExpectedValue(int N, int K, int n) {
        return n * ((double) K / N);
    }

    public double hypergeometricVariance(int N, int K, int n) {
        return n * ((double) K / N) * ((double) (N - K) / N) * ((double) (N - n) / (N - 1));
    }




}



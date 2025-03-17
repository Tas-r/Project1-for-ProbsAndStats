import java.util.ArrayList;
import java.util.Arrays;

public class testStatsLibrary {

        public static void main(String[] args) {
            StatsLibrary stats = new StatsLibrary();
            int[] testData = {1, 2, 2, 3, 4};
            System.out.println("Mean: " + stats.getMean(testData));
            System.out.println("Median: " + stats.getMedian(testData));
            System.out.println("Mode: " + stats.getMode(testData));
            System.out.println("Sample Standard Deviation: " + stats.getSampleStandardDeviation(testData));
            System.out.println("Population Standard Deviation: " + stats.getPopulationStandardDeviation(testData));
            System.out.println("C(5,2) = " + StatsLibrary.combination(5, 2));
            System.out.println("P(5,2) = " + StatsLibrary.permutation(5, 2));  // Should print 20
            System.out.println("P(A and B) = " + StatsLibrary.multiplicativeLawIndependent(0.3, 0.4));
            System.out.println("P(A or B) = " + StatsLibrary.additiveLawMutuallyExclusive(0.3, 0.4));
            System.out.println("P(A or B)(P intersect B) = " + StatsLibrary.additiveLaw(0.3, 0.4, 0.1));
            System.out.println("Conditional P(A|B) = " + StatsLibrary.conditionalProbability(0.12, 0.4));

            System.out.println("Binomial P(Y=2) = " + StatsLibrary.binomialPMF(5, 2, 0.3));
            System.out.println("Geometric P(Y=2) = " + StatsLibrary.geometricProbability(2, 0.3));
            System.out.println("Negative Binomial: " + StatsLibrary.negativeBinomial(3, 5, 0.5));
            System.out.println("Hypergeometric: " + StatsLibrary.hypergeometric(10, 5, 4, 2));

            // Adding Expected Value and Variance for each distribution
            // Binomial (n=5, p=0.3)
            System.out.println("Binomial E[X] = " + stats.binomialExpectedValue(5, 0.3));
            System.out.println("Binomial Var[X] = " + stats.binomialVariance(5, 0.3));

            // Geometric (p=0.3, trials until first success)
            System.out.println("Geometric E[X] = " + stats.geometricExpectedValue(0.3));
            System.out.println("Geometric Var[X] = " + stats.geometricVariance(0.3));

            // Negative Binomial (r=3, p=0.5, trials until 3rd success)
            System.out.println("Negative Binomial E[X] = " + stats.negativeBinomialExpectedValue(3, 0.5));
            System.out.println("Negative Binomial Var[X] = " + stats.negativeBinomialVariance(3, 0.5));

            // Hypergeometric (N=10, K=5, n=4)
            System.out.println("Hypergeometric E[X] = " + stats.hypergeometricExpectedValue(10, 5, 4));
            System.out.println("Hypergeometric Var[X] = " + stats.hypergeometricVariance(10, 5, 4));


        }
}

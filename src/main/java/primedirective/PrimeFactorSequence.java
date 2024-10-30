package primedirective;

import java.util.*;

public class PrimeFactorSequence {
    private List<Integer> primes;
    private int upperBound;

    /**
     * Create a PrimeFactorSequence object with a defined upperbound.
     *
     * @param _upperBound the upper bound for sequences and primes in this instance,
     * {@code upperBound > 0}.
     */
    public PrimeFactorSequence(int _upperBound) {
        upperBound = _upperBound;
        primes = Primes.getPrimes(upperBound);
    }

    private boolean isPrime(int n){
       for (int p: primes){
           if(p == n){
               return true;
           }
       }
       return false;
    }

    /**
     * Obtain a sequence L[0 .. upper bound] where L[i] represents the number of prime factors i
     * has, including repeated factors
     *
     * @return sequence L[0 .. upper bound] where L[i] represents the number of prime factors i
     * has, including repeated factors
     */
    public List<Integer> primeFactorSequence() {
        List<Integer> seq = new ArrayList<>(Collections.nCopies(upperBound+1, 0));
        for (int p : primes){
            for (int k = p; k <= upperBound; k +=p){
                int tep = k;
                while (tep % p == 0){
                    seq.set(k, seq.get(k) +1);
                    tep /= p;
                }
            }
        }
        return seq;
    }

    /**
     * Obtain a sequence L that is sorted in ascending order and where L[i] has exactly m
     * prime factors (including repeated factors) and L[i] <= upper bound
     *
     * @param m the number of prime factors that each element of the output sequence has
     * @return a sequence L that is sorted in ascending order and where L[i] has exactly
     * m prime factors (including repeated factors) and L[i] <= upper bound
     */
    public List<Integer> numbersWithMPrimeFactors(int m) {
        List<Integer> withPrimeFactorsm = new ArrayList<>();
        List<Integer> seq = primeFactorSequence();

        for(int i = 0; i < seq.size(); i ++){
            if(seq.get(i) == m){
                withPrimeFactorsm.add(i);

            }
        }
        return withPrimeFactorsm;
    }

    /**
     * Obtain a sequence of integer pairs [(Sa, Sb)] where Sa and Sb have exactly m prime factors
     * (including repeated factors), and where |Sa - Sb| <= gap and where Sa and Sb are
     * adjacent each other in the output of {@code numbersWithMPrimeFactors(m)}
     *
     * @param m   the number of prime factors that each element in the output sequence has
     * @param gap the maximum gap between two adjacent entries of interest in the output
     *            of {@code numbersWithMPrimeFactors(m)}
     * @return a sequence of integer pairs [(Sa, Sb)] where Sa and Sb have exactly
     * m prime factors (including repeated factors), and where |Sa - Sb| <= gap and where
     * Sa and Sb are adjacent each other in the output of {@code numbersWithMPrimeFactors(m)}
     */
    public List<IntPair> numbersWithMPrimeFactorsAndSmallGap(int m, int gap) {
        List<IntPair> listOfPairs = new ArrayList<>();
        List<Integer> seq = numbersWithMPrimeFactors(m);

        if(seq.size() <= 2){
            return listOfPairs;
        }
        System.out.println("mPrimeFactorsSeq: " + seq);


        for(int i = 0; i < seq.size() -1; i ++){
            int current = seq.get(i);
            int next = seq.get(i+1);
            if(next-current <= gap){
                listOfPairs.add(new IntPair(current, next));
            }

        }
        return listOfPairs;
    }

    /**
     * Transform n to a larger prime (unless n is already prime) using the following steps:
     * <p>
     *     <ul>
     *         <li>A 0-step where we obtain 2 * n + 1</li>,
     *         <li>or a 1-step where we obtain n + 1</li>.
     *     </ul>
     *      We can apply these steps repeatedly, with more details in the problem statement.
     * </p>
     * @param n the number to transform
     * @return a string representation of the smallest transformation sequence
     */
    public String changeToPrime(int n) {

        if(isPrime(n)) return "";

        Queue<Object[]> queue = new LinkedList<>();
        queue.add(new Object[]{n, ""});

        while(!queue.isEmpty()){
            Object[] current = queue.poll();

            int number = (int) current[0];
            String path = (String) current[1];

            //0-step
            int zeroStep = 2*number + 1;
            if(zeroStep <= upperBound){
                if(isPrime(zeroStep)) return path + "0";
                queue.add(new Object[]{zeroStep, path + "0"});
            }

            int oneStep = number +1;
            if(oneStep <= upperBound){
                if(isPrime(oneStep)) return path + "1";
                queue.add(new Object[]{oneStep, path + "1"});
            }
        }

        return "-";
    }

}

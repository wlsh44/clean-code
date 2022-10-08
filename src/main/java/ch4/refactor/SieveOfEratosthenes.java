package ch4.refactor;

public class SieveOfEratosthenes implements PrimeGenerator {

    public static final int MINIMUM_PRIME_VALUE = 2;

    private int size;
    private boolean[] sieve;

    public int[] generatePrimes(int maxValue) {
        if (!isValidValue(maxValue)) {
            return new int[0];
        }

        size = maxValue + 1;
        sieve = initSieve();

        filterSieve();

        return findPrimes();
    }

    private boolean isValidValue(int maxValue) {
        return maxValue >= MINIMUM_PRIME_VALUE;
    }

    private boolean[] initSieve() {
        boolean[] sieve = new boolean[size];

        for (int i = MINIMUM_PRIME_VALUE; i < size; i++) {
            sieve[i] = true;
        }
        return sieve;
    }

    private void filterSieve() {
        for (int i = MINIMUM_PRIME_VALUE; i < iterLimit(); i++) {
            if (isPrimeNum(sieve[i])) {
                filterMultipleOfPrime(i);
            }
        }
    }

    private int[] findPrimes() {
        int[] primes = initPrimes();

        for (int i = 0, j = 0; i < size; i++) {
            if (isPrimeNum(sieve[i])) {
                primes[j++] = i;
            }
        }
        return primes;
    }

    private int iterLimit() {
        return (int) Math.sqrt(size) + 1;
    }

    private void filterMultipleOfPrime(int primeNum) {
        for (int multipleNum = MINIMUM_PRIME_VALUE * primeNum; multipleNum < size; multipleNum += primeNum) {
            sieve[multipleNum] = false;
        }
    }

    private int[] initPrimes() {
        int count = countPrimes();

        return new int[count];
    }

    private int countPrimes() {
        int count = 0;

        for (int i = 0; i < size; i++) {
            if (isPrimeNum(sieve[i])) {
                count++;
            }
        }
        return count;
    }


    private boolean isPrimeNum(boolean sieve) {
        return sieve;
    }
}

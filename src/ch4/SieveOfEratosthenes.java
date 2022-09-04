package ch4;

public class SieveOfEratosthenes implements PrimeGenerator {

    public static final int MINIMUM_PRIME_VALUE = 2;

    boolean[] sieve;

    public int[] generatePrimes(int maxValue) {
        if (!isValidValue(maxValue)) {
            return new int[0];
        }

        int size = maxValue + 1;
        sieve = initSieve(size);

        filterSieve(size);

        return findPrimes(size);
    }

    private boolean isValidValue(int maxValue) {
        return maxValue >= MINIMUM_PRIME_VALUE;
    }

    private boolean[] initSieve(int size) {
        boolean[] sieve = new boolean[size];

        for (int i = 0; i < size; i++) {
            sieve[i] = true;
        }
        sieve[0] = sieve[1] = false;
        return sieve;
    }

    private int[] findPrimes(int size) {
        int[] primes = initPrimes(size);

        for (int i = 0, j = 0; i < size; i++) {
            if (isPrimeNum(sieve[i])) {
                primes[j++] = i;
            }
        }
        return primes;
    }

    private void filterSieve(int size) {
        for (int i = MINIMUM_PRIME_VALUE; i < iterLimit(size); i++) {
            if (isPrimeNum(sieve[i])) {
                setFalseToMultipleOfPrime(size, i);
            }
        }
    }

    private int iterLimit(int size) {
        return (int) Math.sqrt(size) + 1;
    }

    private int[] initPrimes(int size) {
        int count = countPrimes(size);

        return new int[count];
    }

    private int countPrimes(int size) {
        int count = 0;

        for (int i = 0; i < size; i++) {
            if (isPrimeNum(sieve[i])) {
                count++;
            }
        }
        return count;
    }

    private void setFalseToMultipleOfPrime(int size, int i) {
        for (int j = MINIMUM_PRIME_VALUE * i; j < size; j += i) {
            sieve[j] = false;
        }
    }

    private boolean isPrimeNum(boolean sieve) {
        return sieve;
    }
}

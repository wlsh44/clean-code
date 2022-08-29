package ch4;

/**
 * 에라스토테네스 체를 이용한 소수 생성기
 */
public class PrimeGenerator {

    public static final int MINIMUM_PRIME_VALUE = 2;

    public static int[] generatePrimes(int maxValue) {
        if (!isValidValue(maxValue)) {
            return new int[0]; //입력이 잘못되면 비어 있는 배열을 반환한다.
        }

        int size = maxValue + 1;
        boolean[] sieve = initSieve(size);

        filterSieve(size, sieve);

        return findPrimes(size, sieve);
    }

    private static boolean isValidValue(int maxValue) {
        return maxValue >= MINIMUM_PRIME_VALUE;
    }

    private static void filterSieve(int size, boolean[] sieve) {
        for (int i = MINIMUM_PRIME_VALUE; i < iterLimit(size); i++) {
            if (isPrimeNum(sieve[i])) {
                setFalseToMultipleOfPrime(size, sieve, i);
            }
        }
    }

    private static int iterLimit(int size) {
        return (int) Math.sqrt(size) + 1;
    }

    private static int[] findPrimes(int size, boolean[] sieve) {
        int[] primes = initPrimes(size, sieve);

        for (int i = 0, j = 0; i < size; i++) {
            if (isPrimeNum(sieve[i])) {
                primes[j++] = i;
            }
        }
        return primes;
    }

    private static int[] initPrimes(int size, boolean[] sieve) {
        int count = countPrimes(size, sieve);

        return new int[count];
    }

    private static int countPrimes(int size, boolean[] sieve) {
        int count = 0;

        for (int i = 0; i < size; i++) {
            if (isPrimeNum(sieve[i])) {
                count++;
            }
        }
        return count;
    }

    private static void setFalseToMultipleOfPrime(int size, boolean[] sieve, int i) {
        for (int j = MINIMUM_PRIME_VALUE * i; j < size; j += i) {
            sieve[j] = false;
        }
    }

    private static boolean isPrimeNum(boolean sieve) {
        return sieve;
    }

    private static boolean[] initSieve(int size) {
        boolean[] sieve = new boolean[size];

        for (int i = 0; i < size; i++) {
            sieve[i] = true;
        }
        sieve[0] = sieve[1] = false;
        return sieve;
    }
}

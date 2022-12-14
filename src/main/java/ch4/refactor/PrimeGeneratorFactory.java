package ch4.refactor;

public class PrimeGeneratorFactory {

    public static PrimeGenerator createPrimeGenerator(PrimeGenerateAlgorithm algorithm) {
        switch (algorithm) {
            case SIEVE_OF_ERATOSTHENES:
                return new SieveOfEratosthenes();
        }
        throw new IllegalArgumentException();
    }
}

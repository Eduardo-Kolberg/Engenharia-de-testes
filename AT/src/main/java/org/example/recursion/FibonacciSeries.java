package org.example.recursion;

public class FibonacciSeries {
    private FibonacciSeries() {
        throw new UnsupportedOperationException("Utility class");
    }
    public static int fibonacci(int n) {
        if (n <= 1) {
            return n;
        } else {
            return fibonacci(n - 1) + fibonacci(n - 2);
        }
    }
}

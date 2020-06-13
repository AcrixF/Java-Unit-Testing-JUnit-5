package org.neoa.bookstore.utils;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.function.BiFunction;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;

public class DSLTest {

    @Test
    public void nullAssertionTest() {
        String str = null;
        assertNull(str);
        assertNull(str, "str should be null");
        assertNull(str, () -> "str should be null");
    }

    @Test
    public void shouldCheckForEvenNumbers() {
        int number = new Random(10).nextInt();

        assertTrue(() -> number % 2 == 0, number + " is not an even number.");

        BiFunction<Integer, Integer, Boolean> divisible = (x, y) -> x % y == 0;
        Function<Integer, Boolean> multipleOf2 = (x) -> divisible.apply(x, 2);

        assertTrue(() -> multipleOf2.apply(number), () -> " 2 is not factor of " + number);

        List<Integer> numbers = Arrays.asList(1, 1, 1, 1, 2);
        assertTrue(() -> numbers.stream().distinct().anyMatch(DSLTest::isEven));

    }

    @Test
    public void thisTestShouldFail() {
        fail(() -> "This test should fail.");
    }

    public static boolean isEven(int number) {
        return number % 2 == 0;
    }

}

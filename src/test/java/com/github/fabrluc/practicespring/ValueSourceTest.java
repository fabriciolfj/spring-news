package com.github.fabrluc.practicespring;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ValueSourceTest {

    @ParameterizedTest
    @ValueSource(ints = {2, 4})
    void testCheckNumber(final int number) {
        assertEquals(0, number % 2, "Supplied number is not even number");
    }

}
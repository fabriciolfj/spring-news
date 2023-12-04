package com.github.fabrluc.practicespring;

import com.github.fabrluc.practicespring.entities.Person;
import com.github.fabrluc.practicespring.enums.ChronoUnit;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.AggregateWith;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.provider.*;
import org.testcontainers.shaded.org.apache.commons.lang3.StringUtils;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class ValueSourceTest {

    @ParameterizedTest
    @ValueSource(ints = {2, 4})
    void testCheckNumber(final int number) {
        assertEquals(0, number % 2, "Supplied number is not even number");
    }

    @ParameterizedTest
    @NullAndEmptySource
    void testChecknullAndEmpyt(String value) {
        assertTrue(value == null || value.isEmpty());
    }

    @ParameterizedTest
    @MethodSource
    void checkImplicitMethodSource(String word) {
        assertTrue(StringUtils.isAlphanumeric(word),
                "Supplied word is not alpha-numeric");
    }

    /* carregando metodo externo a classe
    @ParameterizedTest
    @MethodSource(
            "source.method.ExternalMethodSource#checkExternalMethodSourceArgs")
    void checkExternalMethodSource(String word) {
        assertTrue(StringUtils.isAlphanumeric(word),
                "Supplied word is not alpha-numeric");
    }*/

    @ParameterizedTest
    @CsvFileSource(
            files = "src/test/resources/csv-file-source.csv",
            numLinesToSkip = 1)
    void testCheckCsvFileSource(int number, String expected) {
        assertEquals(StringUtils.equals(expected, "even")
                ? 0 : 1, number % 2);
    }

    @ParameterizedTest
    @EnumSource(ChronoUnit.class)
    void testCheckEnumSourceValue(ChronoUnit unit) {
        assertNotNull(unit);
    }

    //conversao
    @ParameterizedTest
    @ValueSource(strings = { "Name1", "Name2" })
    void testCheckImplicitFallbackArgumentConversion(Person person) {
        assertNotNull(person.getName());
    }

    //como o parametrize manda um parametro, caso precisamos de amis, podemos usar o arguments accessor
    @ParameterizedTest
    @CsvSource(value = {"John, 20",
            "Harry, 30"})
    void testCheckArgumentsAccessor(ArgumentsAccessor arguments) {
        Person person = new Person(arguments.getString(0),
                arguments.getInteger(1));
        assertTrue(person.getAge() > 19, person.getName() + " is a teenager");
    }

    @ParameterizedTest
    @CsvSource({ "John, 20",
            "Harry, 30" })
    void testCheckArgumentsAggregator(
            @AggregateWith(PersonArgumentsAggregator.class) Person person) {
        assertTrue(person.getAge() > 19, person.getName() + " is a teenager");
    }

    static Stream<String> checkImplicitMethodSource() {
        return Stream.of("a1",
                "b2");
    }

}
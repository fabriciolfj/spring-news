package com.github.fabrluc.practicespring;

import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LinkedHashMapTest {

    static final LinkedHashMap<String, String> THE_MAP = new LinkedHashMap<>();

    static {
        THE_MAP.put("key one", "a1 b1 c1");
        THE_MAP.put("key two", "a2 b2 c2");
        THE_MAP.put("key three", "a3 b3 c3");
        THE_MAP.put("key four", "a4 b4 c4");
    }


    @Test
    void testPegandoPrimeiroItem() {
        var firstEntry = THE_MAP.entrySet().iterator().next();

        assertEquals(firstEntry.getValue(), "a1 b1 c1");
        assertEquals(firstEntry.getKey(), "key one");
    }

    @Test
    void testPegandoUltimoElemento() {
        var entry = THE_MAP.entrySet().iterator();
        Map.Entry<String, String> temp = null;

        while(entry.hasNext()) {
            temp = entry.next();
        }

        assertEquals(temp.getValue(), "a4 b4 c4");
        assertEquals(temp.getKey(), "key four");
    }

    @Test
    void testConvertendoUmArray() {
        Map.Entry<String, String>[] array = new Map.Entry[THE_MAP.size()];
        THE_MAP.entrySet().toArray(array);

        assertEquals(array[0].getValue(), "a1 b1 c1");
        assertEquals(array[0].getKey(), "key one");

        assertEquals(array[array.length - 1].getValue(), "a4 b4 c4");
        assertEquals(array[array.length - 1].getKey(), "key four");
    }

}

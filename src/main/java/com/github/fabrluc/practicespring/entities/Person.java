package com.github.fabrluc.practicespring.entities;

import lombok.Getter;

@Getter
public class Person {

    private String name;
    private int age;

    public Person(final String name) {
        this.name = name;
    }

    public Person(final String name, final int age) {
        this.name = name;
        this.age = age;
    }
}

package com.github.fabrluc.practicespring.listener;


import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class UserEventStore {

    @Getter
    private final static List<UserEvent> userEvents = new ArrayList<>();

    public static void addUserEvent(UserEvent userEvent) {
        userEvents.add(userEvent);
    }

    public static void clearUserEvents() {
        userEvents.clear();
    }
}
package com.github.fabrluc.practicespring.tools;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Component;

@Component
public class MyTools {

    @Tool(name = "mytool", description = "Get the current weather for a given location")
    public String weather(String location) {
        return "The current weather in " + location + " is sunny with a temperature of 25°C.";
    }
}

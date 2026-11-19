package com.idream.demo;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class AppTest {
    @Test
    void greetingContainsInstituteName() {
        assertEquals("Welcome to iDream IT Solution!", App.greeting());
    }
}

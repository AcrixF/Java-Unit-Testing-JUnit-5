package org.neoa.bookstore.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FailureAndErrorTests {

    @Test
    public void StringNotEmpty() {
        String str = "";
        assertFalse(str.isEmpty());
    }

    @Test
    public void thisMethodThrowsException() {
        String str = null;
        assertTrue(str.isEmpty());
    }

}

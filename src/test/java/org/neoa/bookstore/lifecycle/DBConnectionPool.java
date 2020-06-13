package org.neoa.bookstore.lifecycle;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

public abstract class DBConnectionPool {

    @BeforeAll
    public static void connectDBConnectionPool() {

    }

    @AfterAll
    public static void closeDBConnectionPool() {

    }
}

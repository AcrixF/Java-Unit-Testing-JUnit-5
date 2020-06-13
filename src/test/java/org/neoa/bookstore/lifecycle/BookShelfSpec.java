package org.neoa.bookstore.lifecycle;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BookShelfSpec extends DBConnectionPool {

    @BeforeEach
    public void initializeBookShelfWithDataBase() {}

    @Test
    public void shouldGiveBackAllBooksInShelf() {}

    @AfterEach
    public void deleteShelfFromDB() {}

}

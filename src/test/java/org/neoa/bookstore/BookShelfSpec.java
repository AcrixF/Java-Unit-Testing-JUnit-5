package org.neoa.bookstore;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.neoa.bookstore.model.BookShelf;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("<= BookShelf Specification =>")
public class BookShelfSpec {

    private BookShelf shelf;

    @BeforeEach
    public void init() throws Exception {
        this.shelf = new BookShelf();
    }

    @Test
    @DisplayName("Is empty when no book is added to it.")
    void shelfEmptyWhenNoBookAdded(TestInfo testInfo) throws Exception {
        List<String> books = shelf.books();
        assertTrue(books.isEmpty(), () -> "BookShelf should be empty.");
    }

    @Test
    @DisplayName("Bookshelf Contains Two Books When Two Books Added.")
    public void bookshelfContainsTwoBooksWhenTwoBooksAdded() {
        shelf.add("Effective Java", "Code Complete");
        List<String> books = shelf.books();
        assertEquals(2, books.size(), () -> "BookShelf should have two books.");
    }

    @Test
    @DisplayName("Empty BookShelf When Add Is Called Without Books.")
    public void emptyBookShelfWhenAddIsCalledWithoutBooks() {
        shelf.add();
        List<String> books = shelf.books();
        assertTrue(books::isEmpty, () -> "BookShelf should be empty.");
    }

    @Test
    @DisplayName("Books Returned From BookShelf Is Immutable For Client.")
    public void booksReturnedFromBookShelfIsImmutableForClient() {
        shelf.add("Effective Java", "Code Complete");
        List<String> books = shelf.books();
        try {
            books.add("The Mythical Man-Month");
            fail(() -> "Should not be able to add book to books");
        } catch (Exception e) {
            assertTrue(e instanceof UnsupportedOperationException, () -> "Should throw UnsupportedOperationException");
        }
    }

    @Test
    @DisplayName("Bookshelf Arranged By Book Title")
    public void bookshelfArrangedByBookTitle() {
        shelf.add("Effective Java", "Code Complete","The Mythical Man-Month");
        List<String> books = shelf.arrange();
        assertEquals(Arrays.asList("Code Complete","Effective Java", "The Mythical Man-Month"), books, () -> "Books in bookshelf should be arranged lexicographically by book title.");
    }

    @Test
    @DisplayName("Books In BookShelf Are In Insertion Order After Calling Arrange")
    public void booksInBookShelfAreInInsertionOrderAfterCallingArrange() {
        shelf.add("Effective Java", "Code Complete", "The Mythical Man-Month");
        shelf.arrange();
        List<String> books = shelf.books();
        assertEquals(Arrays.asList("Effective Java", "Code Complete", "The Mythical Man-Month"), books, () -> "Books in bookshelf are in insertion order");
    }

}

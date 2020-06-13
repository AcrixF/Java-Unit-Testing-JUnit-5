package org.neoa.bookstore;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.neoa.bookstore.model.Book;
import org.neoa.bookstore.model.BookShelf;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.function.BiFunction;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("<= BookShelf Specification =>")
public class BookShelfSpec {

    private BookShelfSpec(TestInfo testInfo) {
        System.out.println("Working in Test " + testInfo.getDisplayName());
    }

    @Test
    @DisplayName("Is empty when no book is added to it.")
    void shelfEmptyWhenNoBookAdded(TestInfo testInfo) throws Exception {
        BookShelf shelf = new BookShelf();
        List<String> books = shelf.books();
        assertTrue(books.isEmpty(), () -> "BookShelf should be empty.");
    }

    @Test
    public void shelfToStringShouldPrintBookCountAndTitles() throws Exception {
        BookShelf shelf = new BookShelf();
        List<String> books = shelf.books();

        shelf.add("The Phoenix Project");
        shelf.add("Java 8 in Action");

        String shelfStr = shelf.toString();

        assertTrue(shelfStr.contains("The Phoenix Project"), "1st book title missing");
        assertTrue(shelfStr.contains("Java 8 in Action"), "2nd book title missing");
        assertTrue(shelfStr.contains("2 books found"), "Book count missing");

    }

    @Test
    public void shelfToStringShouldPrintBookCountAndTitlesAssertAll() throws Exception {
        BookShelf shelf = new BookShelf();
        List<String> books = shelf.books();

        shelf.add("The Phoenix Project");
        shelf.add("Java 8 in Action");

        String shelfStr = shelf.toString();

        assertAll(() -> assertTrue(shelfStr.contains("The Phoenix Project"), "1st book title missing"),
                  () -> assertTrue(shelfStr.contains("Java 8 in Action"), "2nd book title missing"),
                  () -> assertTrue(shelfStr.contains("2 books found"), "Book count missing"));
    }


}

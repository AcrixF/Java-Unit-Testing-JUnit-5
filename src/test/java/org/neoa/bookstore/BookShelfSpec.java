package org.neoa.bookstore;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.extension.ExtendWith;
import org.neoa.bookstore.model.Book;
import org.neoa.bookstore.model.BookShelf;

import java.time.Year;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("<= BookShelf Specification =>")
@ExtendWith(BooksParameterResolver.class)
public class BookShelfSpec {

    private BookShelf shelf;

    private Book effectiveJava;
    private Book codeComplete;
    private Book mythicalManMonth;
    private Book cleanCode;

    @BeforeEach
    public void init(Map<String, Book> books) throws Exception {
        this.shelf = new BookShelf();
        this.effectiveJava = books.get("Effective Java");
        this.codeComplete = books.get("Code Complete");
        this.mythicalManMonth = books.get("The Mythical Man-Month");
        this.cleanCode = books.get("Clean Code");
    }


    @Nested
    @DisplayName("BookShelf is empty ")
    class IsEmpty {

        @Test
        @DisplayName("Is empty when no book is added to it.")
        void shelfEmptyWhenNoBookAdded(TestInfo testInfo) throws Exception {
            List<Book> books = shelf.books();
            assertTrue(books.isEmpty(), () -> "BookShelf should be empty.");
        }

        @Test
        @DisplayName("Empty BookShelf When Add Is Called Without Books.")
        public void emptyBookShelfWhenAddIsCalledWithoutBooks() {
            shelf.add();
            List<Book> books = shelf.books();
            assertTrue(books::isEmpty, () -> "BookShelf should be empty.");
        }
    }

    @Nested
    @DisplayName("BookShelf after adding Books")
    class BooksAreAdded {

        @Test
        @DisplayName("Bookshelf Contains Two Books When Two Books Added.")
        public void bookshelfContainsTwoBooksWhenTwoBooksAdded() {
            shelf.add(effectiveJava, codeComplete);
            List<Book> books = shelf.books();
            assertEquals(2, books.size(), () -> "BookShelf should have two books.");
        }

        @Test
        @DisplayName("Books Returned From BookShelf Is Immutable For Client.")
        public void booksReturnedFromBookShelfIsImmutableForClient() {
            shelf.add(effectiveJava, codeComplete);
            List<Book> books = shelf.books();
            try {
                books.add(mythicalManMonth);
                fail(() -> "Should not be able to add book to books");
            } catch (Exception e) {
                assertTrue(e instanceof UnsupportedOperationException, () -> "Should throw UnsupportedOperationException");
            }
        }

    }

    @Nested
    @DisplayName("BookShelf is Arranged")
    class IsArranged {
        @Test
        @DisplayName("Bookshelf Arranged By Book Title")
        public void bookshelfArrangedByBookTitle() {
            shelf.add(effectiveJava, codeComplete, mythicalManMonth);
            List<Book> books = shelf.arrange();
            assertEquals(Arrays.asList(codeComplete, effectiveJava, mythicalManMonth), books, () -> "Books in bookshelf should be arranged lexicographically by book title.");
        }

        @Test
        @DisplayName("Books In BookShelf Are In Insertion Order After Calling Arrange")
        public void booksInBookShelfAreInInsertionOrderAfterCallingArrange() {
            shelf.add(effectiveJava, codeComplete, mythicalManMonth);
            shelf.arrange();
            List<Book> books = shelf.books();
            assertEquals(Arrays.asList(effectiveJava, codeComplete, mythicalManMonth), books, () -> "Books in bookshelf are in insertion order");
        }

        @Test
        @DisplayName("Bookshelf Arranged By User Provided Criteria")
        public void bookshelfArrangedByUserProvidedCriteria() {
            shelf.add(effectiveJava, codeComplete, mythicalManMonth);
            Comparator<Book> reversed = Comparator.<Book> naturalOrder().reversed();
            List<Book> books = shelf.arrange(reversed);
            assertThat(books).isSortedAccordingTo(reversed);
        }
    }

    @Nested
    @DisplayName("BookShelf GroupBy")
    class GroupingBy {
        @Test
        @DisplayName("Group Books Inside BookShelf By Publication Year")
        public void groupBooksInsideBookShelfByPublicationYear() {
            shelf.add(effectiveJava, codeComplete, mythicalManMonth, cleanCode);
            Map<Year, List<Book>> booksByPublicationYear = shelf.groupByPublicationYear();

            assertThat(booksByPublicationYear)
                    .containsKey(Year.of(2008))
                    .containsValues(Arrays.asList(effectiveJava, cleanCode));

            assertThat(booksByPublicationYear)
                    .containsKey(Year.of(2004))
                    .containsValues(Collections.singletonList(codeComplete));

            assertThat(booksByPublicationYear)
                    .containsKey(Year.of(1975))
                    .containsValues(Collections.singletonList(mythicalManMonth));
        }


        @Test
        @DisplayName("Books inside bookshelf are grouped according to user provided criteria(group by author name)")
        public void groupBooksByUserProvidedCriteria() {
            shelf.add(effectiveJava, codeComplete, mythicalManMonth, cleanCode);
            Map<String, List<Book>> booksByAuthor = shelf.groupBy(Book::getAuthor);

            assertThat(booksByAuthor)
                    .containsKey("Joshua Bloch")
                    .containsValues(Collections.singletonList(effectiveJava));

            assertThat(booksByAuthor)
                    .containsKey("Steve McConnel")
                    .containsValues(Collections.singletonList(codeComplete));

            assertThat(booksByAuthor)
                    .containsKey("Frederick Phillips Brooks")
                    .containsValues(Collections.singletonList(mythicalManMonth));

            assertThat(booksByAuthor)
                    .containsKey("Robert C. Martin")
                    .containsValues(Collections.singletonList(cleanCode));
        }

    }

}

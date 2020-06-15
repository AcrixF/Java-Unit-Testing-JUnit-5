package org.neoa.bookstore;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.extension.ExtendWith;
import org.neoa.bookstore.model.Book;
import org.neoa.bookstore.model.BookFilter;
import org.neoa.bookstore.model.BookPublishedYearFilter;
import org.neoa.bookstore.model.BookShelf;
import org.neoa.bookstore.model.Progress;

import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("<= A BookShelf Progress =>")
@ExtendWith(BooksParameterResolver.class)
public class BookShelfSpec {

    private BookShelf shelf;

    private Book effectiveJava;
    private Book codeComplete;
    private Book mythicalManMonth;
    private Book cleanCode;
    private Book refactoring;

    @BeforeEach
    public void init(Map<String,Book> books) throws Exception {
        this.shelf = new BookShelf();

        this.effectiveJava = books.get("Effective Java");
        this.codeComplete = books.get("Code Complete");
        this.mythicalManMonth = books.get("The Mythical Man-Month");
        this.cleanCode = books.get("Clean Code");
        this.refactoring = books.get("Refactoring: Improving the Design of Existing Code");

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

    @Nested
    @DisplayName("Progress")
    class BookShelfProgress {

        @Test
        @DisplayName("is 40% completed, and 60% to read, when two books read and 3 books no read yet")
        public void progressWithCompletedProgressAndToReadPercentages() {
            effectiveJava.startedReadingOn(LocalDate.of(2016, Month.JULY, 1));
            effectiveJava.finishedReadingOn(LocalDate.of(2016, Month.JULY, 31));

            cleanCode.startedReadingOn(LocalDate.of(2016, Month.AUGUST, 1));
            cleanCode.finishedReadingOn(LocalDate.of(2016, Month.AUGUST, 31));

            shelf.add(effectiveJava, codeComplete, mythicalManMonth, cleanCode, refactoring);

            Progress progress = shelf.progress();
            assertThat(progress.completed()).isEqualTo(40);
            assertThat(progress.toRead()).isEqualTo(60);
        }

        @Test
        @DisplayName("is 0% completed and 100% to-read when no book is read yet")
        public void progress100PercentUnread() {
            Progress progress = shelf.progress();
            assertThat(progress.completed()).isEqualTo(0);
            assertThat(progress.toRead()).isEqualTo(100);
        }

        @Test
        @DisplayName("is 40% completed, 20% in-progress, and 40% to-read when 2 books read, 1 book in progress, and 2 books unread")
        public void progressWithCompletedInProgressAndToReadPercentages() {
            effectiveJava.startedReadingOn(LocalDate.of(2016, Month.JULY, 1));
            effectiveJava.finishedReadingOn(LocalDate.of(2016, Month.JULY, 31));

            cleanCode.startedReadingOn(LocalDate.of(2016, Month.AUGUST, 1));
            cleanCode.finishedReadingOn(LocalDate.of(2016, Month.AUGUST, 31));

            mythicalManMonth.startedReadingOn(LocalDate.of(2016, Month.MAY, 15));

            shelf.add(effectiveJava, codeComplete, mythicalManMonth, cleanCode, refactoring);

            Progress progress = shelf.progress();

            assertThat(progress.completed()).isEqualTo(40);
            assertThat(progress.inProgress()).isEqualTo(20);
            assertThat(progress.toRead()).isEqualTo(40);
        }

    }

    @Nested
    @DisplayName("Filtering")
    class Filtering {

        @Test
        @DisplayName("should find books with title containing text")
        public void shouldFindBooksWithTitleContainingText() {
            shelf.add(codeComplete, effectiveJava, mythicalManMonth, cleanCode);
            List<Book> books = shelf.findBooksByTitle("code");
            assertThat(books.size()).isEqualTo(2);
        }

        @Test
        @DisplayName("should find books with title containing text and published after specified date.")
        public void shouldFilterSearchedBooksBasedOnPublishedDate() {
            shelf.add(codeComplete, effectiveJava, mythicalManMonth, cleanCode);
            List<Book> books = shelf.findBooksByTitle("code", b -> b.getPublishedOn().isBefore(LocalDate.of(2014,12,31)));
            assertThat(books.size()).isEqualTo(2);
        }
    }

    @Nested
    @DisplayName("Filters")
    class BookPublishedFilterSpec {

        @Test
        @DisplayName("is after specified year")
        public void validateBookPublishedDatePostAskedYear() {
            BookFilter filter = BookPublishedYearFilter.After(2007);
            assertTrue(filter.apply(cleanCode));
            assertFalse(filter.apply(codeComplete));
        }
    }

}

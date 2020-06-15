package org.neoa.bookstore.model;

import java.time.LocalDate;
import java.util.function.Function;

public class BookPublishedYearFilter implements BookFilter {

    private Function<LocalDate, Boolean> comparasion;

    public static BookPublishedYearFilter After(int year) {
        LocalDate date = LocalDate.of(year,12, 31);
        BookPublishedYearFilter filter = new BookPublishedYearFilter();
        filter.comparasion = date::isAfter;
        return filter;
    }

    public static BookPublishedYearFilter Before(int year) {
        LocalDate date = LocalDate.of(year, 1, 1);
        BookPublishedYearFilter filter = new BookPublishedYearFilter();
        filter.comparasion = date::isBefore;
        return filter;
    }

    @Override
    public boolean apply(final Book book) {
        return book != null && comparasion.apply(book.getPublishedOn());
    }
}

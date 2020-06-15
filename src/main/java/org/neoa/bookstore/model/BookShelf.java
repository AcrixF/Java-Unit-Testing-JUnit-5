package org.neoa.bookstore.model;

import java.time.Year;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class BookShelf {

    private final List<Book> books = new ArrayList<>();

    public List<Book> books() {
        return Collections.unmodifiableList(this.books);
    }

    public void add(Book... bookToAdd) {
        Arrays.stream(bookToAdd).forEach(books::add);
    }

    public List<Book> arrange() {
        return arrange(Comparator.<Book>naturalOrder());
    }

    public List<Book> arrange(Comparator<Book> criteria) {
        return books.stream()
                .sorted(criteria)
                .collect(Collectors.toList());
    }

    public Map<Year, List<Book>> groupByPublicationYear() {
       return groupBy(book -> Year.of(book.getPublishedOn().getYear()));
    }

    public <K> Map<K, List<Book>> groupBy(Function<Book, K> groupByFunction) {
        return this.books.stream()
                .collect(Collectors.groupingBy(groupByFunction));
    }

}

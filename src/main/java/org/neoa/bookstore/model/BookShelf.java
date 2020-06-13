package org.neoa.bookstore.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class BookShelf {

    private final List<String> books = new ArrayList<>();

    public List<String> books() {
        return Collections.unmodifiableList(this.books);
    }

    public void add(String... bookToAdd) {
        Arrays.stream(bookToAdd).forEach(books::add);
    }

    public List<String> arrange() {
        return books.stream().sorted(Comparator.naturalOrder())
                .collect(Collectors.toList());
    }

    @Override
    public String toString() {
        int count  = this.books.size();
        String books = this.books.stream().collect(Collectors.joining(" "));
        return books +  " " + count + " books found";
    }
}

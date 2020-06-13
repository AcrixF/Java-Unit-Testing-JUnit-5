package org.neoa.bookstore.model;

import lombok.ToString;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class BookShelf {

    private final List<String> books = new ArrayList<>();
    private final int capacity;

    public BookShelf() {
        this.capacity  = Integer.MAX_VALUE;
    }

    public BookShelf(int capacity) {
        this.capacity = capacity;
    }

    public List<String> books() {
        return books;
    }

    public void add(String book) {
        this.books.add(book);
    }

    @Override
    public String toString() {
        int count  = this.books.size();
        String books = this.books.stream().collect(Collectors.joining(" "));
        return books +  " " + count + " books found";
    }
}

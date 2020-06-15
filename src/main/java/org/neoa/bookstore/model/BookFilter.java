package org.neoa.bookstore.model;

@FunctionalInterface
public interface BookFilter {
    boolean apply(Book book);
}

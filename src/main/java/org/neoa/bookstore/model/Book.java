package org.neoa.bookstore.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Objects;

@Getter
@Setter
public class Book implements Comparable<Book> {

    private final String title;
    private final String author;
    private final LocalDate publishedOn;
    private LocalDate startedReadingOn;
    private LocalDate finishedReadingOn;

    public Book(String title, String author, LocalDate publishedOn) {
        this.title = title;
        this.author = author;
        this.publishedOn = publishedOn;
    }

    public boolean isRead() {
        return startedReadingOn != null && finishedReadingOn != null;
    }

    public boolean isProgress() {
        return startedReadingOn != null && finishedReadingOn == null;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (this == null || getClass() != o.getClass()) return false;

        Book book = (Book) o;

        if (!Objects.equals(this.title, book.title)) return false;
        if (!Objects.equals(author, book.author)) return false;
        return Objects.equals(publishedOn, book.publishedOn);
    }

    @Override
    public int hashCode() {
        int result = title != null ? title.hashCode() : 0;
        result = 31 * result + (author != null ? author.hashCode(): 0);
        result = 31 * result + (publishedOn != null ? publishedOn.hashCode(): 0);
        return result;
    }

    @Override
    public int compareTo(Book that) {
        return this.title.compareTo(that.title);
    }

    @Override
    public String toString() {
        return "Book{" +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", publishedOn=" + publishedOn +
                '}';
    }
}

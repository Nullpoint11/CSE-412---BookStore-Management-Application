package com.example.phase3;

public class Book {
    private String isbn;
    private int copyId;
    private String name;
    private String author;
    private int publishedYear;
    private int copiesAvailable;

    // Constructor
    public Book(String isbn, int copyId, String name, String author, int publishedYear, int copiesAvailable) {
        this.isbn = isbn;
        this.copyId = copyId;
        this.name = name;
        this.author = author;
        this.publishedYear = publishedYear;
        this.copiesAvailable = copiesAvailable;
    }

    // Getters and Setters
    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public int getCopyId() {
        return copyId;
    }

    public void setCopyId(int copyId) {
        this.copyId = copyId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getPublishedYear() {
        return publishedYear;
    }

    public void setPublishedYear(int publishedYear) {
        this.publishedYear = publishedYear;
    }

    public int getCopiesAvailable() {
        return copiesAvailable;
    }

    public void setCopiesAvailable(int copiesAvailable) {
        this.copiesAvailable = copiesAvailable;
    }

    // toString method to display book information
    @Override
    public String toString() {
        return "Book{" +
                "isbn='" + isbn + '\'' +
                ", copyId=" + copyId +
                ", name='" + name + '\'' +
                ", author='" + author + '\'' +
                ", publishedYear=" + publishedYear +
                ", copiesAvailable=" + copiesAvailable +
                '}';
    }

    // Additional methods as necessary
}

package model;

import java.util.ArrayList;
import java.util.List;

public class Category {
    private String name;
    private int index;
    private List<Book> books;

    public Category() {}

    public Category(String name, int index) {
        this.name = name;
        this.index = index;
        this.books = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void addBook(Book book) {
        books.add(book);
    }

    public void removeBook(Book book) {
        books.remove(book);
    }

    @Override
    public String toString() {
        return "Category{" +
                "name='" + name + '\'' +
                ", index=" + index +
                ", books=" + books +
                '}';
    }
}

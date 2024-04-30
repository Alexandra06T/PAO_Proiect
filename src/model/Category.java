package model;

import java.util.ArrayList;
import java.util.List;

public class Category {
    private String name;
    private int categoryIndex;
    private List<Book> books;

    public Category() {}

    public Category(String name, int categoryIndex) {
        this.name = name;
        this.categoryIndex = categoryIndex;
        this.books = new ArrayList<>();
    }

    public Category(Category category) {
        this.name = category.getName();
        this.categoryIndex = category.getCategoryIndex();
        this.books = category.getBooks();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCategoryIndex() {
        return categoryIndex;
    }

    public void setCategoryIndex(int categoryIndex) {
        this.categoryIndex = categoryIndex;
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
        return name +
                " (" + categoryIndex + ")";
    }
}

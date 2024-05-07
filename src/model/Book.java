package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Book {

    private String title;
    private List<String> authors;
    private String ISBN;
    private String publishingHouse;
    private int publishedDate;
    private int numberOfPages;
    private int categoryID;
    private List<BookCopy> bookCopies;
    private List<Reservation> reservations;

    public Book() {
        this.bookCopies = new ArrayList<>();
        this.reservations = new ArrayList<>();
    }


    public Book(String title, List<String> authors, String ISBN, String publishingHouse, int publishedDate, int numberOfPages) {
        this.title = title;
        this.authors = authors;
        this.ISBN = ISBN;
        this.publishingHouse = publishingHouse;
        this.publishedDate = publishedDate;
        this.numberOfPages = numberOfPages;
        this.bookCopies = new ArrayList<>();
        this.reservations = new ArrayList<>();
        this.categoryID = -1;
    }

    public Book(Book book) {
        this.title = book.getTitle();
        this.authors = book.getAuthors();
        this.ISBN = book.getISBN();
        this.publishingHouse = book.getPublishingHouse();
        this.publishedDate = book.getPublishedDate();
        this.numberOfPages = book.getNumberOfPages();
        this.reservations = book.getReservations();
        this.bookCopies = new ArrayList<>();
        for(BookCopy b : book.getBookCopies()) {
            this.bookCopies.add(new BookCopy(b));
        }
        this.categoryID = -1;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getAuthors() {
        return authors;
    }

    public void setAuthors(List<String> authors) {
        this.authors = authors;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public String getPublishingHouse() {
        return publishingHouse;
    }

    public void setPublishingHouse(String publishingHouse) {
        this.publishingHouse = publishingHouse;
    }

    public int getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(int publishedDate) {
        this.publishedDate = publishedDate;
    }

    public int getNumberOfPages() {
        return numberOfPages;
    }

    public void setNumberOfPages(int numberOfPages) {
        this.numberOfPages = numberOfPages;
    }

    public int getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(int category) {
        this.categoryID = category;
    }

    public List<BookCopy> getBookCopies() {
        return bookCopies;
    }

    public void addBookCopy(BookCopy bookCopy) {
        bookCopies.add(new BookCopy(bookCopy));
    }

    public void removeBookCopy(BookCopy bookCopy) {
        bookCopies.remove(bookCopy);
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void addReservation(Reservation reservation) {
        reservations.add(reservation);
    }

    public void removeReservation(Reservation reservation) {
        reservations.remove(reservation);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return publishedDate == book.publishedDate && numberOfPages == book.numberOfPages && Objects.equals(title, book.title) && Objects.equals(authors, book.authors) && Objects.equals(ISBN, book.ISBN) && Objects.equals(publishingHouse, book.publishingHouse) && categoryID == book.getCategoryID();
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, authors, ISBN, publishingHouse, publishedDate, numberOfPages, categoryID);
    }

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder(title + '\n');
        for(String a : authors) {
            res.append(a);
            res.append("; ");
        }
        res.append("\nISBN: ").append(ISBN).append('\n').append(publishingHouse).append(", ").append(publishedDate).append("\nnumber of pages: ").append(numberOfPages).append("\n");
        return res.toString();
    }
}

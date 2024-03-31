package model;

import service.CategoryService;
import service.ReservationService;

import java.util.ArrayList;
import java.util.List;

public class Book {

    private String title;
    private List<String> authors;
    private String ISBN;
    private String publishingHouse;
    private int publishedDate;
    private int numberOfPages;
    private Category category;
    private List<Copy> copies;
    private List<Reservation> reservations;

    public Book() {}

    public Book(String title, List<String> authors, String ISBN, String publishingHouse, int publishedDate, int numberOfPages) {
        this.title = title;
        this.authors = authors;
        this.ISBN = ISBN;
        this.publishingHouse = publishingHouse;
        this.publishedDate = publishedDate;
        this.numberOfPages = numberOfPages;
        this.category = new Category();
        this.copies = new ArrayList<>();
        this.reservations = new ArrayList<>();
    }

    public Book(Book book) {
        this.title = book.getTitle();
        this.authors = book.getAuthors();
        this.ISBN = book.getISBN();
        this.publishingHouse = book.getPublishingHouse();
        this.publishedDate = book.getPublishedDate();
        this.numberOfPages = book.getNumberOfPages();
        this.category = book.category;
        this.copies = book.getCopies();
        this.reservations = book.getReservations();
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

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public List<Copy> getCopies() {
        return copies;
    }

    public void addCopy(Copy copy) {
        copies.add(copy);
    }

    public void removeCopy(Copy copy) {
        copies.remove(copy);
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
    public String toString() {
        return title + '\n' +
                authors + "\nISBN: " +
                ISBN + '\n' +
                publishingHouse + ", " +
                publishedDate +
                "\nnumber of pages: " + numberOfPages +
                "\n" + category +
                ", copies=" + copies;
    }
}

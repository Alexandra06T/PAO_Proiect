package model;

import java.util.Date;

public class Reservation {

    private LibraryMember libraryMember;
    private Book book;
    private Date expiryDate;
    private Location pickupLocation;

    public Reservation() {}

    public Reservation(LibraryMember libraryMember, Book book, Date expiryDate, Location pickupLocation) {
        this.libraryMember = new LibraryMember(libraryMember);
        this.book = book;
        this.expiryDate = expiryDate;
        this.pickupLocation = new Location(pickupLocation);
    }

    public LibraryMember getLibraryMember() {
        return libraryMember;
    }

    public void setLibraryMember(LibraryMember libraryMember) {
        this.libraryMember = libraryMember;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    public Location getPickupLocation() {
        return pickupLocation;
    }

    public void setPickupLocation(Location pickupLocation) {
        this.pickupLocation = pickupLocation;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "libraryMember=" + libraryMember +
                ", book=" + book +
                ", expiryDate=" + expiryDate +
                ", pickupLocation=" + pickupLocation +
                '}';
    }
}

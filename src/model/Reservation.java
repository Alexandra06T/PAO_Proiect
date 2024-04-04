package model;

import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;

public class Reservation {

    private static int nrReservations = 0;
    private int id;
    private LibraryMember libraryMember;
    private Book book;
    private LocalDate expiryDate;
    private Location pickupLocation;

    public Reservation() {}

    public Reservation(LibraryMember libraryMember, Book book, LocalDate expiryDate, Location pickupLocation) {
        this.libraryMember = libraryMember;
        this.book = book;
        this.expiryDate = expiryDate;
        this.pickupLocation = new Location(pickupLocation);
        nrReservations++;
        this.id = nrReservations;
    }

    public int getId() {
        return id;
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

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }

    public Location getPickupLocation() {
        return pickupLocation;
    }

    public void setPickupLocation(Location pickupLocation) {
        this.pickupLocation = pickupLocation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reservation that = (Reservation) o;
        return id == that.id && Objects.equals(libraryMember, that.libraryMember) && Objects.equals(book, that.book) && Objects.equals(expiryDate, that.expiryDate) && Objects.equals(pickupLocation, that.pickupLocation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, libraryMember, book, expiryDate, pickupLocation);
    }

    @Override
    public String toString() {
        return "ID: " + id +
                "\nLIBRARY MEMBER:\n" + libraryMember +
                "\nBOOK:\n" + book +
                "\nEXPIRES ON: " + expiryDate +
                "\nPICK UP LOCATION:\n" + pickupLocation;
    }
}

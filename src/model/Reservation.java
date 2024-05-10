package model;

import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;

public class Reservation {
    private int reservationID;
    private int libraryMemberID;
    private String bookID;
    private LocalDate expiryDate;
    private int pickupLocationID;

    public Reservation() {}

    public Reservation(int libraryMemberID, String bookID, LocalDate expiryDate, int pickupLocationID) {
        this.libraryMemberID = libraryMemberID;
        this.bookID = bookID;
        this.expiryDate = expiryDate;
        this.pickupLocationID = pickupLocationID;
    }

    public int getReservationID() {
        return reservationID;
    }

    public void setReservationID(int reservationID) {
        this.reservationID = reservationID;
    }

    public int getLibraryMemberID() {
        return libraryMemberID;
    }

    public void setLibraryMemberID(int libraryMemberID) {
        this.libraryMemberID = libraryMemberID;
    }

    public String getBookID() {
        return bookID;
    }

    public void setBook(String bookID) {
        this.bookID = bookID;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }

    public int getPickupLocationID() {
        return pickupLocationID;
    }

    public void setPickupLocation(int pickupLocationID) {
        this.pickupLocationID = pickupLocationID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reservation that = (Reservation) o;
        return reservationID == that.reservationID && libraryMemberID == that.libraryMemberID && bookID.equals(that.bookID) && Objects.equals(expiryDate, that.expiryDate) && pickupLocationID == that.pickupLocationID;
    }

    @Override
    public int hashCode() {
        return Objects.hash(reservationID, libraryMemberID, bookID, expiryDate, pickupLocationID);
    }

    @Override
    public String toString() {
        return "ID: " + reservationID +
                "\nEXPIRES ON: " + expiryDate;
    }
}

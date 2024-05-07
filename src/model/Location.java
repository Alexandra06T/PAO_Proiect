package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Location {

    private int locationID;
    private String name;
    private int branchLibraryID;
    private List<BookCopy> bookCopies;
    private List<Reservation> reservations;

    public Location() {}

    public Location(String name, int branchLibraryID) {
        this.name = name;
        this.branchLibraryID = branchLibraryID;
        this.bookCopies = new ArrayList<>();
        this.reservations = new ArrayList<>();
    }

    public Location(Location location) {
        this.name = location.getName();
        this.branchLibraryID = location.getBranchLibraryID();
        this.locationID = location.getLocationID();
        this.bookCopies = location.getBookCopies();
        this.reservations = location.getReservations();
    }

    public int getLocationID() {
        return locationID;
    }

    public void setLocationID(int locationID) {
        this.locationID = locationID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBranchLibraryID() {
        return branchLibraryID;
    }

    public void setBranchLibrary(int branchLibraryID) {
        this.branchLibraryID = branchLibraryID;
    }

    public List<BookCopy> getBookCopies() {
        return bookCopies;
    }

    public void addBookCopy(BookCopy bookCopy) {
        bookCopies.add(bookCopy);
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
        Location location = (Location) o;
        return Objects.equals(name, location.name) && branchLibraryID == location.branchLibraryID;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, branchLibraryID);
    }

    @Override
    public String toString() {
        return name + '\n';
    }
}

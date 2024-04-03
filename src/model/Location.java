package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Location {

    private String name;
    private BranchLibrary branchLibrary;
    private List<BookCopy> bookCopies;
    private List<Reservation> reservations;

    public Location() {}

    public Location(String name, BranchLibrary branchLibrary) {
        this.name = name;
        this.branchLibrary = branchLibrary;
        this.bookCopies = new ArrayList<>();
        this.reservations = new ArrayList<>();
    }

    public Location(Location location) {
        this.name = location.getName();
        this.branchLibrary = location.getBranchLibrary();
        this.bookCopies = location.getBookCopies();
        this.reservations = location.getReservations();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BranchLibrary getBranchLibrary() {
        return branchLibrary;
    }

    public void setBranchLibrary(BranchLibrary branchLibrary) {
        this.branchLibrary = branchLibrary;
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
        return Objects.equals(name, location.name) && Objects.equals(branchLibrary, location.branchLibrary);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, branchLibrary);
    }

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder(name + '\n' + branchLibrary+ '\n');
        for(BookCopy b : bookCopies) {
            res.append(b);
            res.append('\n');
        }
        return res.toString();
    }
}

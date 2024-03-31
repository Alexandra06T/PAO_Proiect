package model;

import service.ReservationService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Location {

    private String name;
    private BranchLibrary branchLibrary;
    private List<Copy> copies;
    private List<Reservation> reservations;

    public Location() {}

    public Location(String name, BranchLibrary branchLibrary) {
        this.name = name;
        this.branchLibrary = new BranchLibrary(branchLibrary);
        this.copies = new ArrayList<>();
        this.reservations = new ArrayList<>();
    }

    public Location(Location location) {
        this.name = location.getName();
        this.branchLibrary = new BranchLibrary(location.getBranchLibrary());
        this.copies = location.getCopies();
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
        return "Location{" +
                "name='" + name + '\'' +
                ", branchLibrary=" + branchLibrary +
                ", copies=" + copies +
                '}';
    }
}

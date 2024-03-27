package model;

import java.util.Date;

public class Reservation {

    private LibraryMember libraryMember;
    private Copy copy;
    private Date expiryDate;
    private Location pickupLocation;

    public Reservation() {}

    public Reservation(LibraryMember libraryMember, Copy copy, Date expiryDate, Location pickupLocation) {
        this.libraryMember = new LibraryMember(libraryMember);
        this.copy = copy;
        this.expiryDate = expiryDate;
        this.pickupLocation = new Location(pickupLocation);
    }

    public LibraryMember getLibraryMember() {
        return libraryMember;
    }

    public void setLibraryMember(LibraryMember libraryMember) {
        this.libraryMember = libraryMember;
    }

    public Copy getCopy() {
        return copy;
    }

    public void setCopy(Copy copy) {
        this.copy = copy;
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
                ", copy=" + copy +
                ", expiryDate=" + expiryDate +
                ", pickupLocation=" + pickupLocation +
                '}';
    }
}

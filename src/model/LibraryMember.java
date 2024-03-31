package model;

import java.util.ArrayList;
import java.util.List;

public class LibraryMember {

    private static int nrMembers = 0;
    private int memberID;
    private String name;
    private String emailAddress;
    private String phoneNumber;
    private String address;
    private List<Reservation> reservations;
    private List<Transaction> transactions;

    public LibraryMember() {}

    public LibraryMember(String name, String emailAddress, String phoneNumber, String address) {
        this.memberID = ++nrMembers;
        this.name = name;
        this.emailAddress = emailAddress;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.reservations = new ArrayList<>();
        this.transactions = new ArrayList<>();
    }

    public LibraryMember(LibraryMember libraryMember) {
        this.memberID = libraryMember.getMemberID();
        this.name = libraryMember.getName();
        this.emailAddress = libraryMember.getEmailAddress();
        this.phoneNumber = libraryMember.getPhoneNumber();
        this.address = libraryMember.getAddress();
        this.transactions = libraryMember.getTransactions();
        this.reservations = libraryMember.getReservations();
    }

    public int getMemberID() {
        return memberID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void addReservation(Reservation reservation) {
        reservations.add(reservation);
    }

    public void removeReservation(Reservation reservation) {
        reservations.remove(reservation);
    }

    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
    }

    public void removeTransaction(Transaction transaction) {
        transactions.remove(transaction);
    }

    @Override
    public String toString() {
        return "LibraryMember{" +
                "memberID='" + memberID + '\'' +
                ", name='" + name + '\'' +
                ", emailAddress='" + emailAddress + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}

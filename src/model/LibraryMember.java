package model;

import java.util.ArrayList;
import java.util.List;

public class LibraryMember {

    private String memberID;
    private String name;
    private String emailAddress;
    private String phoneNumber;
    private String address;
    private List<Reservation> reservations;
    private List<Transaction> transactions;

    public LibraryMember() {}

    public LibraryMember(String memberID, String name, String emailAddress, String phoneNumber, String address) {
        this.memberID = memberID;
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

    public String getMemberID() {
        return memberID;
    }

    public void setMemberID(String memberID) {
        this.memberID = memberID;
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

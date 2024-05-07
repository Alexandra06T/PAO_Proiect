package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class LibraryMember {

    private int memberID;
    private String name;
    private String emailAddress;
    private String phoneNumber;
    private String address;

    public LibraryMember() {}

    public LibraryMember(String name, String emailAddress, String phoneNumber, String address) {
        this.name = name;
        this.emailAddress = emailAddress;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    public LibraryMember(LibraryMember libraryMember) {
        this.memberID = libraryMember.getMemberID();
        this.name = libraryMember.getName();
        this.emailAddress = libraryMember.getEmailAddress();
        this.phoneNumber = libraryMember.getPhoneNumber();
        this.address = libraryMember.getAddress();
    }

    public int getMemberID() {
        return memberID;
    }

    public void setMemberID(int memberID) { this.memberID = memberID; }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LibraryMember that = (LibraryMember) o;
        return memberID == that.memberID && Objects.equals(name, that.name) && Objects.equals(emailAddress, that.emailAddress) && Objects.equals(phoneNumber, that.phoneNumber) && Objects.equals(address, that.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(memberID, name, emailAddress, phoneNumber, address);
    }

    @Override
    public String toString() {

        return "ID: " + memberID + "\n" + name +
                "\nCONTACT DETAILS:\n" + emailAddress +
                "\n" + phoneNumber +
                "\n" + address + "\n";
    }
}

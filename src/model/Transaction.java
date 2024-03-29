package model;

import java.util.Date;

public class Transaction {

    private static int nrTransactions = 0;
    private int id;
    private LibraryMember libraryMember;
    private Copy copy;
    private Date date;

    public Transaction() {
    }

    public Transaction(LibraryMember libraryMember, Copy copy, Date date) {
        this.libraryMember = new LibraryMember(libraryMember);
        this.copy = new Copy(copy);
        this.date = date;
        nrTransactions++;
        this.id = nrTransactions;
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

    public Copy getCopy() {
        return copy;
    }

    public void setCopy(Copy copy) {
        this.copy = copy;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "libraryMember=" + libraryMember +
                ", copy=" + copy +
                ", date=" + date +
                '}';
    }
}

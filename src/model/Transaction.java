package model;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class Transaction {
    private int transactionID;
    private int libraryMemberID;
    private int bookCopyID;
    private LocalDate date;

    public Transaction() {

    }

    public Transaction(int libraryMemberID, int bookCopyID, LocalDate date) {
        this.libraryMemberID = libraryMemberID;
        this.bookCopyID = bookCopyID;
        this.date = date;
    }

    public Transaction(Transaction transaction) {
        this.libraryMemberID = transaction.getLibraryMemberID();
        this.bookCopyID = transaction.getBookCopyID();
        this.date = transaction.getDate();
        this.transactionID = transaction.getTransactionID();
    }

    public int getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(int transactionID) {
        this.transactionID = transactionID;
    }

    public int getLibraryMemberID() {
        return libraryMemberID;
    }

    public void setLibraryMemberID(int libraryMemberID) {
        this.libraryMemberID = libraryMemberID;
    }

    public int getBookCopyID() {
        return bookCopyID;
    }

    public void setBookCopyID(int bookCopyID) {
        this.bookCopyID = bookCopyID;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return transactionID == that.transactionID && libraryMemberID == that.libraryMemberID && bookCopyID == that.bookCopyID && Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(transactionID, libraryMemberID, bookCopyID, date);
    }

    @Override
    public String toString() {
        return "ID: " + transactionID + "\nMade on: " + date + '\n';
    }
}

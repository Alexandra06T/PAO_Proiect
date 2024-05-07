package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BookCopy {

    private int bookCopyID;
    private String bookISBN;
    private String barcode;
    private String index;
    private int locationID;
    private boolean available;
    private List<Transaction> transactions;

    public BookCopy() {
        this.transactions = new ArrayList<>();
    }

    public BookCopy(String bookISBN, String barcode, String index, int locationID, boolean available) {
        this.bookISBN = bookISBN;
        this.barcode = barcode;
        this.index = index;
        this.locationID = locationID;
        this.available = available;
        this.transactions = new ArrayList<>();
    }

    public BookCopy(BookCopy bookCopy) {
        this.bookCopyID = bookCopy.getBookCopyID();
        this.bookISBN = bookCopy.getBookISBN();
        this.barcode = bookCopy.getBarcode();
        this.index = bookCopy.getIndex();
        this.locationID = bookCopy.getLocationID();
        this.available = bookCopy.isAvailable();
        this.transactions = bookCopy.getTransactions();
    }

    public int getBookCopyID() {
        return bookCopyID;
    }

    public void setBookCopyID(int id) {
        this.bookCopyID = id;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public int getLocationID() {
        return locationID;
    }

    public void setLocationID(int locationID) {
        this.locationID = locationID;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public String getBookISBN() {
        return bookISBN;
    }

    public void setBookISBN(String bookISBN) {
        this.bookISBN = bookISBN;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
    }

    public void removeTransaction(Transaction transaction) {
        transactions.remove(transaction);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookCopy bookCopy = (BookCopy) o;
        return bookCopyID == bookCopy.getBookCopyID() && available == bookCopy.available && bookISBN.equals(bookCopy.getBookISBN()) && Objects.equals(barcode, bookCopy.barcode) && Objects.equals(index, bookCopy.index) && locationID == bookCopy.getLocationID();
    }

    @Override
    public int hashCode() {
        return Objects.hash(bookCopyID, bookISBN, barcode, index, locationID, available);
    }

    @Override
    public String toString() {
        String av;
        if(available) av = "available";
        else av = "not available";

        return "ID: " + bookCopyID + '\n' +
                "barcode: " + barcode + '\n' +
                "index: " + index + '\n' + "Availability: " + av;
    }
}

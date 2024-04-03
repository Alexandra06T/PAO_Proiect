package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BookCopy {

    private int id;
    private Book book;
    private String barcode;
    private String index;
    private Location location;
    private boolean available;
    private List<Transaction> transactions;

    public BookCopy() {}

    public BookCopy(int id, Book book, String barcode, String index, Location location, boolean available) {
        this.id = id;
        this.book = book;
        this.barcode = barcode;
        this.index = index;
        this.location = location;
        this.available = available;
        this.transactions = new ArrayList<>();
    }

    public BookCopy(BookCopy bookCopy) {
        this.id = bookCopy.getId();
        this.book = bookCopy.getBook();
        this.barcode = getBarcode();
        this.index = bookCopy.getIndex();
        this.location = bookCopy.getLocation();
        this.available = bookCopy.isAvailable();
        this.transactions = bookCopy.getTransactions();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
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
        return id == bookCopy.id && available == bookCopy.available && Objects.equals(book, bookCopy.book) && Objects.equals(barcode, bookCopy.barcode) && Objects.equals(index, bookCopy.index) && Objects.equals(location, bookCopy.location);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, book, barcode, index, location, available);
    }

    @Override
    public String toString() {
        String av;
        if(available) av = "available";
        else av = "not available";

        return "ID: " + id + '\n' +
                book +
                "barcode: " + barcode + '\n' +
                "index: " + index + '\n' +
                location + av;
    }
}

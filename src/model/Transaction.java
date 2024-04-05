package model;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class Transaction {

    private static int nrTransactions = 0;
    private int id;
    private LibraryMember libraryMember;
    private BookCopy bookCopy;
    private LocalDate date;

    public Transaction() {

    }

    public Transaction(LibraryMember libraryMember, BookCopy bookCopy, LocalDate date) {
        this.libraryMember = libraryMember;
        this.bookCopy = bookCopy;
        this.date = date;
        nrTransactions++;
        this.id = nrTransactions;
    }

    public Transaction(Transaction transaction) {
        this.libraryMember = transaction.getLibraryMember();
        this.bookCopy = transaction.getBookCopy();
        this.date = transaction.getDate();
        this.id = transaction.getId();
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

    public BookCopy getBookCopy() {
        return bookCopy;
    }

    public void setBookCopy(BookCopy bookCopy) {
        this.bookCopy = bookCopy;
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
        return id == that.id && Objects.equals(libraryMember, that.libraryMember) && Objects.equals(bookCopy, that.bookCopy) && Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, libraryMember, bookCopy, date);
    }

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder("ID: " + id +
                "\nLIBRARY MEMBER:\n" + libraryMember.getName() + ", ID: " + libraryMember.getMemberID() +
                "\nBOOK COPY:\n" + bookCopy.getBook().getTitle() + '\n');
        List<String> authors = bookCopy.getBook().getAuthors();
        for(String a : authors) {
            res.append(a);
            res.append("; ");
        }
        res.append('\n' + bookCopy.getLocation().getBranchLibrary().getName() + ", " + bookCopy.getLocation().getName() +
                "\nON: " + date);

        return res.toString();
    }
}

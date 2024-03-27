package model;

import java.util.Date;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CheckOut extends Transaction {

    private String bookStatus;
    private int overdueDays;
    private double penalty;

    public CheckOut(Transaction transaction) {
        super(transaction.getLibraryMember(), transaction.getCopy(), transaction.getDate());
    }

    public void setOverdueDays(int overdueDays) {
        this.overdueDays = overdueDays;
    }

    public void setBookStatus(String bookStatus) {
        this.bookStatus = bookStatus;
    }

    public void setPenalty(double penalty) {
        this.penalty = penalty;
    }

    @Override
    public String toString() {
        return "CheckOut{" +
                "bookStatus='" + bookStatus + '\'' +
                ", overdueDays=" + overdueDays +
                ", penalty=" + penalty +
                '}';
    }
}

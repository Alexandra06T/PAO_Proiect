package model;

public class CheckOut extends Transaction {

    private int checkOutID;
    private int checkinID;
    private String bookStatus;
    private int overdueDays;
    private double penalty;

    public CheckOut() {
    }

    public CheckOut(Transaction transaction) {
        super(transaction);
        this.checkinID = transaction.getTransactionID();
    }

    public int getCheckOutID() {
        return checkOutID;
    }

    public void setCheckOutID(int checkOutID) {
        this.checkOutID = checkOutID;
    }

    public String getBookStatus() {
        return bookStatus;
    }

    public int getOverdueDays() {
        return overdueDays;
    }

    public double getPenalty() {
        return penalty;
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

    public int getCheckinID() {
        return checkinID;
    }

    public void setCheckinID(int checkinID) {
        this.checkinID = checkinID;
    }

    @Override
    public String toString() {
        return  super.toString() +
                "ID of the check in: " + checkinID +
                "\nBook state: " + bookStatus +
                "\nOverdue days: " + overdueDays + " days" +
                "\nPenalty: " + penalty;
    }
}

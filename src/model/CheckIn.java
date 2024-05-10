package model;

public class CheckIn extends Transaction {

    private int checkInID;
    private int numberDays;
    private String type;
    private boolean checkedOut = false;

    public CheckIn() {

    }

    public CheckIn(Transaction transaction) {
        super(transaction);
        this.checkInID = transaction.getTransactionID();
    }

    public int getCheckInID() {
        return checkInID;
    }

    public void setCheckInID(int checkInID) {
        this.checkInID = checkInID;
    }

    public int getNumberDays() {
        return numberDays;
    }

    public String getType() {
        return type;
    }

    public boolean isCheckedOut() {
        return checkedOut;
    }

    public void setNumberDays(int numberDays) {
        this.numberDays = numberDays;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setCheckedOut(boolean checkedOut) {
        this.checkedOut = checkedOut;
    }

    @Override
    public String toString() {
        return  super.toString() +
                "Check in for: " + numberDays + " days" +
                "\nType of the check in: " + type +
                "\nChecked out: " + checkedOut;
    }
}

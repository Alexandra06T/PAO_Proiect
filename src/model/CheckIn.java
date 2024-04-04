package model;

public class CheckIn extends Transaction {
    private int numberDays;
    private String type;
    private boolean checkedOut = false;

    public CheckIn(Transaction transaction) {
        super(transaction.getLibraryMember(), transaction.getBookCopy(), transaction.getDate());
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
        return  "Check in for: " + numberDays +
                "\nType of the check in: " + type +
                "\nchecked out: " + checkedOut;
    }
}

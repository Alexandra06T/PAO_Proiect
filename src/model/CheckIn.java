package model;

import java.util.Date;

public class CheckIn extends Transaction {
    private int numberDays;
    private String type;
    private boolean checkedOut = false;

    public CheckIn(Transaction transaction) {
        super(transaction.getLibraryMember(), transaction.getCopy(), transaction.getDate());
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
        return "CheckIn{" +
                "numberDays=" + numberDays +
                ", type='" + type + '\'' +
                ", checkedOut=" + checkedOut +
                '}';
    }
}

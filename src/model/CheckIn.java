package model;

import java.util.Date;

public class CheckIn extends Transaction {
    private int numberDays;
    private String type;

    public CheckIn(Transaction transaction) {
        super(transaction.getLibraryMember(), transaction.getCopy(), transaction.getDate());
    }

    public void setNumberDays(int numberDays) {
        this.numberDays = numberDays;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "CheckIn{" +
                "numberDays=" + numberDays +
                ", type='" + type + '\'' +
                '}';
    }
}

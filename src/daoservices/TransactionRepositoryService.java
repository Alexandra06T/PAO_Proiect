package daoservices;

import model.*;
import dao.CheckInDao;
import dao.CheckOutDao;

import static utils.Constants.CHECKIN;

public class TransactionRepositoryService {

    private CheckInDao checkInDao;
    private CheckOutDao checkOutDao;

    public TransactionRepositoryService() {
        this.checkInDao = new CheckInDao();
        this.checkOutDao = new CheckOutDao();
    }

    public CheckIn getCheckInById(int id){
        CheckIn checkIn = checkInDao.read(id);
        if(checkIn != null){
            System.out.println(checkIn);
        }else {
            System.out.println("No check in having this id");
        }

        return checkIn;
    }

    public CheckOut getCheckOutById(int id){
        CheckOut checkOut = checkOutDao.read(id);
        if(checkOut != null){
            System.out.println(checkOut);
        }else {
            System.out.println("No check out having this id");
        }
        return checkOut;
    }

    public void removeTransaction(String typeOfTransaction, int id) {
        Transaction transaction = getTransaction(typeOfTransaction, id);
        if (transaction == null) return;

        switch (transaction){
            case CheckIn checkIn -> checkInDao.delete(checkIn);
            case CheckOut checkOut -> checkOutDao.delete(checkOut);
            default -> throw new IllegalStateException("Unexpected value: " + transaction);
        }

        System.out.println("Removed " + transaction);
    }

    public void addTransaction(Transaction transaction) {
        if(transaction != null){
            switch (transaction){
                case CheckIn checkIn-> checkInDao.create(checkIn);
                case CheckOut checkOut -> checkOutDao.create(checkOut);
                default -> throw new IllegalStateException("Unexpected value: " + transaction);
            }
        }
    }

    public Transaction getTransaction(String typeOfTransaction, int id) {
        Transaction transaction;
        if(typeOfTransaction.equals(CHECKIN)){
            transaction = getCheckInById(id);
        }else{
            transaction = getCheckOutById(id);
        }
        if(transaction == null) {
            System.out.println("No transaction having id " + id);
            return null;
        }
        return transaction;
    }
}

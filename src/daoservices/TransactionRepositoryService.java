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
        return checkInDao.read(id);

    }

    public CheckOut getCheckOutById(int id){
        return checkOutDao.read(id);

    }

    public void removeTransaction(String typeOfTransaction, int id) {
        Transaction transaction = getTransaction(typeOfTransaction, id);
        if (transaction == null) return;

        switch (transaction){
            case CheckIn checkIn -> checkInDao.delete(checkIn);
            case CheckOut checkOut -> checkOutDao.delete(checkOut);
            default -> throw new IllegalStateException("Unexpected value: " + transaction);
        }
        transaction.getLibraryMember().removeTransaction(transaction);
        transaction.getBookCopy().removeTransaction(transaction);

    }

    public void addTransaction(Transaction transaction) {
        if(transaction != null){
            switch (transaction){
                case CheckIn checkIn -> {checkInDao.create(checkIn); checkIn.getBookCopy().setAvailable(false);}
                case CheckOut checkOut -> {checkOutDao.create(checkOut); checkOut.getBookCopy().setAvailable(true);}
                default -> throw new IllegalStateException("Unexpected value: " + transaction);
            }
            transaction.getLibraryMember().addTransaction(transaction);
            transaction.getBookCopy().addTransaction(transaction);
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

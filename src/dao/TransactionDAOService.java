package dao;

import model.CheckIn;
import model.CheckOut;
import model.Transaction;
import repository.CheckInRepository;
import repository.CheckOutRepository;

import static utils.Constants.CHECKIN;

public class TransactionDAOService {

    private CheckInRepository checkInRepository;
    private CheckOutRepository checkOutRepository;

    public TransactionDAOService() {
        this.checkInRepository = new CheckInRepository();
        this.checkOutRepository = new CheckOutRepository();
    }

    public CheckIn getCheckInById(int id){
        CheckIn checkIn = checkInRepository.read(id);
        if(checkIn != null){
            System.out.println(checkIn);
        }else {
            System.out.println("No check in having this id");
        }

        return checkIn;
    }

    public CheckOut getCheckOutById(int id){
        CheckOut checkOut = checkOutRepository.read(id);
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
            case CheckIn checkIn -> checkInRepository.delete(checkIn);
            case CheckOut checkOut -> checkOutRepository.delete(checkOut);
            default -> throw new IllegalStateException("Unexpected value: " + transaction);
        }

        System.out.println("Removed " + transaction);
    }

    public void addTransaction(Transaction transaction) {
        if(transaction != null){
            switch (transaction){
                case CheckIn checkIn-> checkInRepository.create(checkIn);
                case CheckOut checkOut -> checkOutRepository.create(checkOut);
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

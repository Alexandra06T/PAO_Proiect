package daoservices;

import dao.*;
import model.*;
import utils.InvalidDataException;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import static utils.Constants.*;

public class TransactionRepositoryService {

    private CheckInDao checkInDao = CheckInDao.getInstance();
    private CheckOutDao checkOutDao = CheckOutDao.getInstance();
    private BookDao bookDao = BookDao.getInstance();
    private BookCopyDao bookCopyDao = BookCopyDao.getInstance();
    private BranchLibraryDao branchLibraryDao = BranchLibraryDao.getInstance();
    private LocationDao locationDao = LocationDao.getInstance();
    private LibraryMemberDao libraryMemberDao = LibraryMemberDao.getInstance();

    public TransactionRepositoryService() throws SQLException {
    }

    public void printTransactionDetails(Transaction transaction) {
        try {
            BookCopy bookCopy = bookCopyDao.read(String.valueOf(transaction.getBookCopyID()));
            Book book = bookDao.read(bookCopy.getBookISBN());
            LibraryMember libraryMember = libraryMemberDao.read(String.valueOf(transaction.getLibraryMemberID()));
            Location location = locationDao.read(String.valueOf(bookCopy.getLocationID()));
            BranchLibrary branchLibrary = branchLibraryDao.readByID(String.valueOf(location.getBranchLibraryID()));
            System.out.println("Checked in " + book.getTitle() + " by " + String.join("; ", book.getAuthors()) + ", Book copy: " + bookCopy.getBookCopyID());
            System.out.println("Checked in by " + libraryMember.getName() + " (Member ID: " + libraryMember.getMemberID() + ")");
            System.out.println("From " + branchLibrary.getName() + ", " + location.getName());
            System.out.println();
        } catch (SQLException e) {
            System.out.println("SQLException " + e.getSQLState() + " " + e.getMessage());
        }

    }
    public void printAll(String type) throws InvalidDataException {
        try {
            switch (type) {
                case CHECKIN:
                    List<CheckIn> checkIns = checkInDao.getAll();
                    if(checkIns == null)
                        throw new InvalidDataException("There is no check in");
                    checkIns.forEach(c -> {
                        System.out.println(c);
                        printTransactionDetails(c);
                    });

                    break;
                case CHECKOUT:
                    List<CheckOut> checkOuts = checkOutDao.getAll();
                    if(checkOuts == null)
                        throw new InvalidDataException("There is no check out");
                    checkOuts.forEach(c -> {
                        System.out.println(c);
                        printTransactionDetails(c);
                    });
                    break;
                default:
                    throw new InvalidDataException("Wrong type of transaction");
            }
        } catch (SQLException e) {
            System.out.println("SQLException " + e.getSQLState() + " " + e.getMessage());
        }
    }

    public List<Transaction> getAll(String type) throws InvalidDataException {
        try {
            switch (type) {
                case CHECKIN:
                    List<CheckIn> checkIns = checkInDao.getAll();
                    if(checkIns == null)
                        throw new InvalidDataException("There is no check in");
                    return checkIns.stream().map(c -> (Transaction) c).toList();
                case CHECKOUT:
                    List<CheckOut> checkOuts = checkOutDao.getAll();
                    if(checkOuts == null)
                        throw new InvalidDataException("There is no check out");
                    return checkOuts.stream().map(c -> (Transaction) c).toList();
                default:
                    throw new InvalidDataException("Wrong type of transaction");
            }
        } catch (SQLException e) {
            System.out.println("SQLException " + e.getSQLState() + " " + e.getMessage());
        }
        return null;
    }

    public CheckIn getCheckInById(int id) {
        try {
            return checkInDao.read(String.valueOf(id));
        } catch (SQLException e) {
            System.out.println("SQLException " + e.getSQLState() + " " + e.getMessage());
        }
        return null;
    }

    public CheckOut getCheckOutById(int id) {
        try {
            return checkOutDao.read(String.valueOf(id));
        } catch (SQLException e) {
            System.out.println("SQLException " + e.getSQLState() + " " + e.getMessage());
        }
        return null;
    }

    public Transaction getTransaction(String typeOfTransaction, int id) throws InvalidDataException {
        Transaction transaction;
        if(typeOfTransaction.equals(CHECKIN)){
            transaction = getCheckInById(id);
        }else{
            transaction = getCheckOutById(id);
        }
        if(transaction == null) {
            throw new InvalidDataException("No transaction having id " + id);
        }
        return transaction;
    }

    public void printTransactionFiltered(String typeOfTransaction, int libraryMemberID, int locationID, String bookID) throws InvalidDataException {
        try {
            if(typeOfTransaction.equals(CHECKIN)){
                List<CheckIn> checkIns = checkInDao.getCheckIn(libraryMemberID, locationID, bookID);
                if(checkIns == null)
                    throw new InvalidDataException("Couldn't find the check in");
                checkIns.forEach(c -> {
                    System.out.println(c);
                    printTransactionDetails(c);
                });
            }else {
                List<CheckOut> checkOuts = checkOutDao.getCheckOut(libraryMemberID, locationID, bookID);
                if (checkOuts == null)
                    throw new InvalidDataException("Couldn't find the check out");
                checkOuts.forEach(c -> {
                    System.out.println(c);
                    printTransactionDetails(c);
                });
            }
        } catch (SQLException e) {
            System.out.println("SQLException " + e.getSQLState() + " " + e.getMessage());
        }
    }

    public void removeTransaction(String typeOfTransaction, int id) throws InvalidDataException {
        try {
            Transaction transaction = getTransaction(typeOfTransaction, id);
            if (transaction == null) throw new InvalidDataException("Invalid transaction");

            switch (transaction){
                case CheckIn checkIn -> {checkInDao.delete(checkIn); bookCopyDao.setAvailability(checkIn.getBookCopyID(), true);}
                case CheckOut checkOut -> {checkOutDao.delete(checkOut); bookCopyDao.setAvailability(checkOut.getBookCopyID(), false);}
                default -> throw new InvalidDataException("Unexpected value: " + transaction);
            }
        } catch (SQLException e) {
            System.out.println("SQLException " + e.getSQLState() + " " + e.getMessage());
        }
    }

    public void addTransaction(Transaction transaction) throws InvalidDataException {
        try {
            if(transaction == null) throw new InvalidDataException("Invalid transaction");
            switch (transaction){
                case CheckIn checkIn -> {checkInDao.add(checkIn); bookCopyDao.setAvailability(checkIn.getBookCopyID(), false); }
                case CheckOut checkOut -> {checkOutDao.add(checkOut); bookCopyDao.setAvailability(checkOut.getBookCopyID(), true);}
                default -> throw new InvalidDataException("Unexpected value: " + transaction);
            }
        } catch (SQLException e) {
            System.out.println("SQLException " + e.getSQLState() + " " + e.getMessage());
        }
    }

    public void updateTransaction(Transaction transaction) throws InvalidDataException {
        try {
            if(transaction == null) throw new InvalidDataException("Invalid transaction");
            switch (transaction){
                case CheckIn checkIn -> checkInDao.update(checkIn);
                case CheckOut checkOut -> checkOutDao.update(checkOut);
                default -> throw new InvalidDataException("Unexpected value: " + transaction);
            }
        } catch (SQLException e) {
            System.out.println("SQLException " + e.getSQLState() + " " + e.getMessage());
        }
    }

    public int getNrCurrentCheckIns(int memberID) {
        try {
            return checkInDao.getNrCurrentCheckIns(memberID);
        }
        catch (SQLException e) {
            System.out.println("SQLException " + e.getSQLState() + " " + e.getMessage());
        }
        return 0;
    }

    public boolean hasOverdueCopies(int memberID, LocalDate currentDate) {
        try {
            return checkInDao.getNrOverdueCopies(memberID, currentDate) > 0;
        }
        catch (SQLException e) {
            System.out.println("SQLException " + e.getSQLState() + " " + e.getMessage());
            return false;
        }
    }

    public List<CheckIn> getCurrentCheckIns(int libraryMemberID, int locationID, String boookID) throws InvalidDataException{
        try {
            List<CheckIn> checkIns = checkInDao.getCurrentCheckIns(libraryMemberID, locationID, boookID);
            if(checkIns == null) throw new InvalidDataException("There are no check ins for this library member and location");
            return checkIns;
        } catch (SQLException e) {
            System.out.println("SQLException " + e.getSQLState() + " " + e.getMessage());
            return null;
        }
    }

    public boolean checkAllowCheckIn(int memberID, LocalDate currentDate) {
        //are voie daca nu depaseste numarul de exemplare imprumutate permise si nu are carti nereturnate care depasesc
        //data pentru returnare
        return getNrCurrentCheckIns(memberID) < maxNrBorrowedBooks && !hasOverdueCopies(memberID, currentDate);
    }


}

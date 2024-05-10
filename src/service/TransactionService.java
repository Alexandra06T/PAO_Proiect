package service;

import daoservices.*;
import model.*;
import utils.InvalidDataException;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

import static java.time.Period.between;
import static java.util.stream.Collectors.groupingBy;
import static utils.Constants.*;

public class TransactionService {
    private TransactionRepositoryService databaseService;
    private BookRepositoryService bookRepositoryService;
    private BookCopyRepositoryService bookCopyRepositoryService;
    private LibraryMemberRepositoryService libraryMemberRepositoryService;
    private ReservationRepositoryService reservationRepositoryService;
    private BranchLibraryRepositoryService branchLibraryRepositoryService;
    private LocationRepositoryService locationRepositoryService;

    public TransactionService() throws SQLException {
        this.databaseService = new TransactionRepositoryService();
        this.bookRepositoryService = new BookRepositoryService();
        this.bookCopyRepositoryService = new BookCopyRepositoryService();
        this.libraryMemberRepositoryService = new LibraryMemberRepositoryService();
        this.reservationRepositoryService = new ReservationRepositoryService();
        this.branchLibraryRepositoryService = new BranchLibraryRepositoryService();
        this.locationRepositoryService = new LocationRepositoryService();
    }

    public void create(Scanner scanner) {
        System.out.println("Enter type of transaction [check in/check out]:");
        String typeOfTransaction = scanner.nextLine().toLowerCase();
        try {
            typeOfTransactionValidation(typeOfTransaction);
            transactionInit(scanner, typeOfTransaction);
        } catch (InvalidDataException e) {
            System.out.println("Creation failed: " + e.getMessage());
        }
    }

    public void view() {
        try {
            System.out.println("TRANSACTIONS:");
            System.out.println("CHECK INS:");
            databaseService.printAll(CHECKIN);
            System.out.println();
            System.out.println("CHECK OUTS:");
            databaseService.printAll(CHECKOUT);
            System.out.println();
        } catch (InvalidDataException e) {
            System.out.println(e.getMessage());
        }
    }

    public void read(Scanner scanner) {
        System.out.println("Enter the type of the transaction:");
        String typeOfTransaction = scanner.nextLine();
        try {
            typeOfTransactionValidation(typeOfTransaction);
            Transaction transaction = chooseTransaction(typeOfTransaction, scanner);
            databaseService.printTransactionDetails(transaction);
        } catch (InvalidDataException e) {
            System.out.println(e.getMessage());
        }
    }

    public void delete(Scanner scanner) {
        System.out.println("Enter the type of the transaction:");
        String typeOfTransaction = scanner.nextLine();
        try {
            typeOfTransactionValidation(typeOfTransaction);
            Transaction transaction = chooseTransaction(CHECKIN, scanner);
            databaseService.removeTransaction(CHECKIN, transaction.getTransactionID());
        } catch (InvalidDataException e) {
            System.out.println("Removal failed :" + e.getMessage());
        }
    }

    public void update(Scanner scanner) {
        System.out.println("Enter the type of the transaction:");
        String typeOfTransaction = scanner.nextLine();
        try {
            typeOfTransactionValidation(typeOfTransaction);
            Transaction transaction = chooseTransaction(typeOfTransaction, scanner);

            if(typeOfTransaction.equals(CHECKIN)){
                CheckIn checkIn = (CheckIn) transaction;
                System.out.println("Enter the number of the days to add to the set period:");
                int numberDays = scanner.nextInt();
                scanner.nextLine();
                if(numberDays > 0 && checkIn.getNumberDays() + numberDays > maxNrBorrowDays)
                    throw new InvalidDataException("The period exceeds the maximum period allowed");
                checkIn.setNumberDays(numberDays);
                databaseService.updateTransaction(checkIn);

            }else{
                CheckOut checkOut = (CheckOut) transaction;
                System.out.println("Enter the book status:");
                String bookStatus = scanner.nextLine();
                checkOut.setBookStatus(bookStatus);
                databaseService.updateTransaction(checkOut);
            }
        } catch (InvalidDataException e) {
            System.out.println("Update failed: " + e.getMessage());
        }
    }

    public void typeOfTransactionValidation(String typeOfTransaction) throws InvalidDataException{

        if(! typeOfTransaction.equals(CHECKIN) && !typeOfTransaction.equals(CHECKOUT)){
            throw new InvalidDataException("Wrong type");
        }
    }

    public Transaction chooseTransaction(String typeOfTransaction, Scanner scanner) throws InvalidDataException {
        LibraryMember libraryMember = chooseLibraryMember(scanner);
        Location location = chooseLocation(scanner);
        Book book = chooseBook(scanner);
        databaseService.printTransactionFiltered(typeOfTransaction, libraryMember.getMemberID(), location.getLocationID(), book.getISBN());
        System.out.println("Enter the ID of the transaction:");
        int id = scanner.nextInt();
        scanner.nextLine();
        return databaseService.getTransaction(typeOfTransaction, id);
    }

    private Book chooseBook(Scanner scanner) throws InvalidDataException {
        System.out.println("How do you want to search the book? [title/author]");
        String option = scanner.nextLine().toLowerCase();
        System.out.println("Enter:");
        String search = scanner.nextLine();
        List<Book> searchedBooks;

        switch (option) {
            case "title":
                searchedBooks = bookRepositoryService.getBooksByTitle(search);
                //daca avem o singura carte, o returnam
                if(searchedBooks.size() == 1) return searchedBooks.getFirst();
                //daca avem mai multe carti, cerem isbn-ul
                break;
            case "author":
                searchedBooks = bookRepositoryService.getBooksByAuthor(search);
                if(searchedBooks.size() == 1) return searchedBooks.getFirst();
                break;
            default:
                throw new InvalidDataException("Wrong option");
        }
        System.out.println("Results for '" + search + "':");
        for(Book b : searchedBooks) {
            System.out.println(b);
            System.out.println("-------------------------------");
        }
        System.out.println("Enter the ISBN of the book:");
        String isbn = scanner.nextLine();
        Book book = bookRepositoryService.getBookByISBN(isbn);
        return book;
    }

    private BookCopy chooseCopy(Scanner scanner) throws InvalidDataException {
//        Location location = chooseLocation(scanner);
        Book book = chooseBook(scanner);
//        List<BookCopy> availableBookCopies = bookCopyRepositoryService.printAvailableBookCopiesByLocation(book.getISBN(), location.getLocationID());
        List<BookCopy> availableBookCopies = bookCopyRepositoryService.getBookCopiesByBook(book.getISBN()).stream().filter(BookCopy::isAvailable).toList();
//        if(availableBookCopies.size() == 1) return availableBookCopies.getFirst();
        availableBookCopies.stream().collect(groupingBy(b -> {
                try{
            return locationRepositoryService.getLocationByID(b.getLocationID()).getBranchLibraryID();
            } catch (InvalidDataException e) {
                    System.out.println(e.getMessage());
            }
                return null;
    }, groupingBy(BookCopy::getLocationID))).forEach((br, map) -> {
        try {
            BranchLibrary branchLibrary = branchLibraryRepositoryService.getBranchLibraryByID(br);
            System.out.println(branchLibrary.getName());
            map.forEach((l, bc) -> {
                try {
                    Location location = locationRepositoryService.getLocationByID(l);
                    System.out.println(location);
                    bc.forEach(System.out::println);
                } catch (InvalidDataException e) {
                    System.out.println(e.getMessage());
                }
            });
        } catch (InvalidDataException e) {
            System.out.println(e.getMessage());
        }
        });

        System.out.println("Enter the id of the book copy:");
        int id = scanner.nextInt();
        scanner.nextLine();
        BookCopy bookCopy = bookCopyRepositoryService.getBookCopyById(id);
        //daca a fost ales un exemplar nepermis, exceptie
        if(!bookCopy.isAvailable())
            throw new InvalidDataException("Wrong book copy ID");
        return bookCopy;
    }

    private Location chooseLocation(Scanner scanner) throws InvalidDataException{
        System.out.println("Enter the branch library's name:");
        String name = scanner.nextLine();
        BranchLibrary branchLibrary = branchLibraryRepositoryService.getBranchLibrary(name);
        System.out.println("Enter the location in the branch library:");
        String loc = scanner.nextLine();
        return locationRepositoryService.getLocationByBranchAndName(loc, branchLibrary.getBranchLibraryID());
    }

    private LibraryMember chooseLibraryMember(Scanner scanner) throws InvalidDataException {
        System.out.println("Enter the ID of the library member:");
        int memberId = scanner.nextInt();
        scanner.nextLine();
        return libraryMemberRepositoryService.getLibraryMemberById(memberId);
    }

    private CheckIn chooseCheckIn(Scanner scanner) throws InvalidDataException {
        LibraryMember libraryMember = chooseLibraryMember(scanner);
        Location location = chooseLocation(scanner);
        Book book = chooseBook(scanner);
        List<CheckIn> checkIns = databaseService.getCurrentCheckIns(libraryMember.getMemberID(), location.getLocationID(), book.getISBN());
        for(CheckIn checkIn : checkIns) {
            System.out.println(checkIn);
            System.out.println("-----------------------");
        }
        if(checkIns.size() == 1)
            return checkIns.getFirst();
        System.out.println("Enter the id of the check in:");
        int id = scanner.nextInt();
        scanner.nextLine();

        CheckIn currentCheckIn = databaseService.getCheckInById(id);
        if(!checkIns.contains(currentCheckIn))
            throw new InvalidDataException("Wrong check in ID");
        return currentCheckIn;
    }

    private void transactionInit(Scanner scanner, String typeOfTransaction) throws InvalidDataException {

        Transaction transaction;

        if(typeOfTransaction.equals(CHECKIN)) {

            transaction = setGeneralInfo(scanner);
            CheckIn checkIn = new CheckIn(transaction);
            checkInInit(scanner, checkIn);
            transaction = checkIn;
            BookCopy bookCopy = bookCopyRepositoryService.getBookCopyById(checkIn.getBookCopyID());
            reservationRepositoryService.cancelReservation(checkIn.getLibraryMemberID(), bookCopy.getLocationID(), bookCopy.getBookISBN(), LocalDate.now());
        }
        else {
            CheckIn currentCheckIn = chooseCheckIn(scanner);
            CheckOut checkOut = new CheckOut(currentCheckIn);
            checkOutInit(scanner, currentCheckIn, checkOut);
            transaction = checkOut;

        }

        databaseService.addTransaction(transaction);
        System.out.println("Created " + transaction + '\n');
    }

    private Transaction setGeneralInfo(Scanner scanner) throws InvalidDataException {
        LibraryMember libraryMember = chooseLibraryMember(scanner);
        if(!databaseService.checkAllowCheckIn(libraryMember.getMemberID(), java.time.LocalDate.now())) {
            throw new InvalidDataException("The library member is not allowed to check in books");
        }
        BookCopy bookCopy = chooseCopy(scanner);
        LocalDate localDate = java.time.LocalDate.now();
        return new Transaction(libraryMember.getMemberID(), bookCopy.getBookCopyID(), localDate);
    }

    private void checkInInit(Scanner scanner, CheckIn checkIn) throws InvalidDataException {
        System.out.println("Enter the number of days:");
        int nrDays = scanner.nextInt();
        scanner.nextLine();
        if(nrDays > maxNrBorrowDays) {
            throw new InvalidDataException("The period exceeds the maximum period allowed");
        }
        if(nrDays < 0) {
            throw new InvalidDataException("A negative number is not allowed");
        }
        System.out.println("Enter the type of the check in:");
        String type = scanner.nextLine();
        checkIn.setNumberDays(nrDays);
        checkIn.setType(type);
        checkIn.setCheckedOut(false);
    }

    private void checkOutInit(Scanner scanner, CheckIn currentCheckIn, CheckOut checkOut) {

        //calculeaza penalitatea
        LocalDate expectedDate = currentCheckIn.getDate().plusDays(currentCheckIn.getNumberDays());
        int delay;
        if(expectedDate.isBefore(java.time.LocalDate.now()))
            delay = between(expectedDate, java.time.LocalDate.now()).getDays();
        else delay = 0;
        double penalty = delay * penaltyPerDay;
        checkOut.setPenalty(penalty);
        checkOut.setOverdueDays(delay);
        System.out.println("Enter the state of the book:");
        String state = scanner.nextLine();
        checkOut.setBookStatus(state);
    }
}

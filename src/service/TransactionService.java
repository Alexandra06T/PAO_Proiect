package service;

import daoservices.*;
import model.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

import static java.time.Period.between;
import static utils.Constants.*;

public class TransactionService {
    private TransactionRepositoryService databaseService;
    private BookRepositoryService bookRepositoryService;
    private CopyRepositoryService copyRepositoryService;
    private LibraryMemberRepositoryService libraryMemberRepositoryService;
    private ReservationRepositoryService reservationRepositoryService;

    public TransactionService(){
        this.databaseService = new TransactionRepositoryService();
        this.bookRepositoryService = new BookRepositoryService();
        this.copyRepositoryService = new CopyRepositoryService();
        this.libraryMemberRepositoryService = new LibraryMemberRepositoryService();
        this.reservationRepositoryService = new ReservationRepositoryService();
    }

    public void create(Scanner scanner) {
        System.out.println("Enter type of transaction [check in/check out]:");
        String typeOfTransaction = scanner.nextLine().toLowerCase();
        if(!typeOfTransactionValidation(typeOfTransaction)) { return; }
        transactionInit(scanner, typeOfTransaction);
    }

    public void read(Scanner scanner) {

    }

    public void delete(Scanner scanner) {

    }

    public void update(Scanner scanner) {

    }

    public boolean typeOfTransactionValidation(String typeOfTransaction) {

        if(! typeOfTransaction.equals(CHECKIN) && !typeOfTransaction.equals(CHECKOUT)){
            System.out.println("Wrong type");
            return false;
        }
        return true;
    }

    private Book chooseBook(Scanner scanner) {
        System.out.println("How do you want to search the book? [title/author]");
        String option = scanner.nextLine().toLowerCase();
        System.out.println("Enter:");
        String search = scanner.nextLine();
        switch (option) {
            case "title":
                bookRepositoryService.getBooksByTitle(search);
            case "author":
                bookRepositoryService.getBooksByAuthor(search);
            default:
                System.out.println("wrong option");
        }
        System.out.println("Enter the ISBN of the book:");
        String isbn = scanner.nextLine();
        Book book = bookRepositoryService.getBookByISBN(isbn);
        if(book == null) {
            System.out.println("wrong ISBN");
        }
        return book;
    }

    private Copy chooseCopy(Scanner scanner) {

        Book book = chooseBook(scanner);
        if(book == null) {
            System.out.println("Couldn't find the book");
            return null;
        }
        List<Copy> availableCopies = copyRepositoryService.getAvailableCopies(book);

        if(availableCopies.isEmpty()){
            System.out.println("The book has no available copies");
        }
        System.out.println("Enter the id of the copy");
        int id = scanner.nextInt();
        scanner.nextLine();
        Copy copy = copyRepositoryService.getCopyByBookAndId(book, id);
        if(copy == null || !copy.isAvailable()) {
            System.out.println("wrong id");
            return null;
        }
        return copy;
    }

    private LibraryMember chooseLibraryMember(Scanner scanner) {
        System.out.println("Enter the ID of the library member:");
        int memberId = scanner.nextInt();
        scanner.nextLine();
        LibraryMember libraryMember = libraryMemberRepositoryService.getLibraryMemberById(memberId);
        if(libraryMember == null) {
            System.out.println("There is no library member having this ID");
            return null;
        }
        return libraryMember;
    }

    private void transactionInit(Scanner scanner, String typeOfTransaction) {

        Transaction transaction = setGeneralInfo(scanner);

        if(typeOfTransaction.equals(CHECKIN)) {
            LibraryMember libraryMember = transaction.getLibraryMember();

            //verificam daca are voie sa imprumute
            if(libraryMemberRepositoryService.getCurrentCheckIns(libraryMember.getMemberID()).size() >= maxNrBorrowedBooks ||
            libraryMemberRepositoryService.hasOverdueCopies(libraryMember.getMemberID(), java.time.LocalDate.now())) {
                System.out.println("The library member is not allowed to borrow books");
                return;
            }

            CheckIn checkIn = new CheckIn(transaction);
            checkInInit(scanner, checkIn);
            transaction = checkIn;
            transaction.getCopy().setAvailable(false);

            //daca exista rezervare, o anulam
            List<Reservation> reservations = reservationRepositoryService.getReservationByMember(libraryMember);
            for(Reservation r : reservations) {
                if(r.getBook().equals(transaction.getCopy().getBook()) && r.getExpiryDate().isBefore(java.time.LocalDate.now())) {
                    r.setExpiryDate(java.time.LocalDate.now());
                }
            }
        }
        else {
            LibraryMember libraryMember = transaction.getLibraryMember();

            CheckIn currentCheckIn = null;
            //cautam daca exista imprumut
            List<CheckIn> checkIns = libraryMemberRepositoryService.getCurrentCheckIns(libraryMember.getMemberID());
            for(CheckIn checkIn : checkIns) {
                if(checkIn.getCopy().equals(transaction.getCopy())) {
                    checkIn.setCheckedOut(true);
                    currentCheckIn = new CheckIn(checkIn);
                    break;
                }
            }

            if(currentCheckIn == null) {
                System.out.println("There is no such check in");
                return;
            }

            CheckOut checkOut = new CheckOut(transaction);
            checkOutInit(scanner, currentCheckIn, checkOut);
            transaction = checkOut;

            transaction.getCopy().setAvailable(true);
        }

        databaseService.addTransaction(transaction);
        System.out.println("Created " + transaction);
        transaction.getLibraryMember().addTransaction(transaction);
        transaction.getCopy().addTransaction(transaction);
    }

    private Transaction setGeneralInfo(Scanner scanner) {
        Copy copy = chooseCopy(scanner);
        LibraryMember libraryMember = chooseLibraryMember(scanner);
        LocalDate localDate = java.time.LocalDate.now();
        return new Transaction(libraryMember, copy, localDate);
    }

    private void checkInInit(Scanner scanner, CheckIn checkIn) {
        checkIn.setCheckedOut(false);
        System.out.println("Enter the number of days:");
        int nrDays = scanner.nextInt();
        scanner.nextLine();
        if(nrDays > maxNrBorrowDays) {
            System.out.println("The period exceeds the maximum period");
            return;
        }
        System.out.println("Enter the type of the check in:");
        String type = scanner.nextLine();
        checkIn.setNumberDays(nrDays);
        checkIn.setType(type);
    }

    private void checkOutInit(Scanner scanner, CheckIn currentCheckIn, CheckOut checkOut) {

        //calculeaza penalitatea
        LocalDate expectedDate = currentCheckIn.getDate().plusDays(currentCheckIn.getNumberDays());
        int delay = between(expectedDate, java.time.LocalDate.now()).getDays();
        double penalty = delay * penaltyPerDay;
        checkOut.setPenalty(penalty);
        checkOut.setOverdueDays(delay);
        System.out.println("Enter the state of the book:");
        String state = scanner.nextLine();
        checkOut.setBookStatus(state);
    }
}

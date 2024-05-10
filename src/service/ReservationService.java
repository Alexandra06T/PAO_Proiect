package service;

import daoservices.*;
import model.*;
import utils.InvalidDataException;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class ReservationService {
    private BookRepositoryService bookRepositoryService;
    private LocationRepositoryService locationRepositoryService;
    private BranchLibraryRepositoryService branchLibraryRepositoryService;
    private LibraryMemberRepositoryService libraryMemberRepositoryService;
    private ReservationRepositoryService databaseService;

    public ReservationService() throws SQLException {
        this.databaseService = new ReservationRepositoryService();
        this.bookRepositoryService = new BookRepositoryService();
        this.locationRepositoryService = new LocationRepositoryService();
        this.branchLibraryRepositoryService = new BranchLibraryRepositoryService();
        this.libraryMemberRepositoryService = new LibraryMemberRepositoryService();
    }

    public void create(Scanner scanner) {
        try {
            Book book = chooseBook(scanner);
            LibraryMember libraryMember = chooseLibraryMember(scanner);
            databaseService.printLocationsWithBookCopies(book.getISBN());
            Location location = chooseLocation(scanner);
            System.out.println("Enter the number of days until the reservation expires:");
            int nrDays = scanner.nextInt();
            scanner.nextLine();

            Reservation reservation = new Reservation(libraryMember.getMemberID(), book.getISBN(), java.time.LocalDate.now().plusDays(nrDays), location.getLocationID());
            databaseService.addReservation(reservation);
            System.out.println("Reservation saved");
        } catch (InvalidDataException e) {
            System.out.println("Creation failed: " + e.getMessage());
        }
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

    private Location chooseLocation(Scanner scanner) throws InvalidDataException{
        System.out.println("Enter the branch library's name:");
        String name = scanner.nextLine();
        BranchLibrary branchLibrary = branchLibraryRepositoryService.getBranchLibrary(name);
        if(branchLibrary == null) {
            throw new InvalidDataException("No branch library having this name");
        }
        System.out.println("Enter the location in the branch library:");
        String loc = scanner.nextLine();
        Location location = locationRepositoryService.getLocationByBranchAndName(loc, branchLibrary.getBranchLibraryID());
        if(location == null) {
            throw new InvalidDataException("No location in the branch library having this name");
        }
        return location;
    }

    private LibraryMember chooseLibraryMember(Scanner scanner) throws InvalidDataException {
        System.out.println("Enter the ID of the library member:");
        int memberId = scanner.nextInt();
        scanner.nextLine();
        return libraryMemberRepositoryService.getLibraryMemberById(memberId);
    }

    private Reservation findReservation(Scanner scanner) throws InvalidDataException{
        System.out.println("Enter the search option for the reservation [book/library member/location]");
        String option = scanner.nextLine().toLowerCase();
        Reservation reservation = new Reservation();
        List<Reservation> reservations = new ArrayList<>();
        int reservationId;

        switch (option) {
            case "book":
                Book book = chooseBook(scanner);
                databaseService.printReservationsByBook(book.getISBN());
                reservations = databaseService.getReservationsByBook(book.getISBN());
                break;
            case "library member":
                LibraryMember libraryMember = chooseLibraryMember(scanner);
                databaseService.printReservationsByMember(libraryMember.getMemberID());
                reservations = databaseService.getReservationsByMember(libraryMember.getMemberID());
                break;
            case "location":
                Location location = chooseLocation(scanner);
                databaseService.printReservationsByLocation(location.getLocationID());
                reservations = databaseService.getReservationsByLocation(location.getLocationID());
                break;
            default:
                throw new InvalidDataException("Wrong option");
        }
        if(reservations.size() == 1) return reservations.getFirst();
        System.out.println("Enter reservation ID:");
        reservationId = scanner.nextInt();
        scanner.nextLine();
        reservation = databaseService.getReservationById(reservationId);
        return reservation;
    }

    public void view() {
        try {
            System.out.println("RESERVATIONS:");
            databaseService.printAll();
            System.out.println();
        } catch (InvalidDataException e) {
            System.out.println(e.getMessage());
        }

    }

    public void read(Scanner scanner) {
        try {
            Reservation reservation = findReservation(scanner);
            System.out.println(reservation);
            Book book = bookRepositoryService.getBookByISBN(reservation.getBookID());
            System.out.println(book.getTitle() + " by " + book.getAuthors().stream().collect(Collectors.joining("; ")));
            Location location = locationRepositoryService.getLocationByID(reservation.getPickupLocationID());
            BranchLibrary branchLibrary = branchLibraryRepositoryService.getBranchLibraryByID(location.getBranchLibraryID());
            System.out.println(location.getName() + ", " + branchLibrary.getName());
            LibraryMember libraryMember = libraryMemberRepositoryService.getLibraryMemberById(reservation.getLibraryMemberID());
            System.out.println("Library member's details: " + libraryMember);
            System.out.println();
            System.out.println();
        } catch (InvalidDataException e) {
            System.out.println(e.getMessage());
        }
    }

    public void delete(Scanner scanner) {
        try {
            Reservation reservation = findReservation(scanner);
            databaseService.removeReservation(reservation);
            System.out.println("Reservation cancelled successfully!");
        } catch (InvalidDataException e) {
            System.out.println("cancellation failed: " + e.getMessage());
        }
    }

    public void update(Scanner scanner) {
        try {
            Reservation reservation = findReservation(scanner);
            System.out.println("Update the information:");
            databaseService.printLocationsWithBookCopies(reservation.getBookID());
            Location newLocation = chooseLocation(scanner);
            System.out.println("Enter the number of days you want to add to the expiring date:");
            int nrDays = scanner.nextInt();
            scanner.nextLine();
            reservation.setPickupLocation(newLocation.getLocationID());
            reservation.setExpiryDate(reservation.getExpiryDate().plusDays(nrDays));
        } catch (InvalidDataException e) {
            System.out.println("Update failed: " + e.getMessage());
        }

    }
}

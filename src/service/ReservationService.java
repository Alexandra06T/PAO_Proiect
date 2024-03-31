package service;

import dao.*;
import model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ReservationService {
    private BookDAOService bookDAOService;
    private LocationDAOService locationDAOService;
    private BranchLibraryDAOService branchLibraryDAOService;
    private LibraryMemberDAOService libraryMemberDAOService;
    private ReservationDAOService databaseService;

    public ReservationService(){
        this.databaseService = new ReservationDAOService();
    }

    public void create(Scanner scanner) {
        Book book = chooseBook(scanner);
        if(book == null) {
            System.out.println("Couldn't find the book");
            return;
        }
        LibraryMember libraryMember = chooseLibraryMember(scanner);
        if(libraryMember == null) {
            System.out.println("Couldn't find the library member");
            return;
        }
        Location location = chooseLocation(scanner);
        if(location == null) {
            System.out.println("Couldn't find the location");
            return;
        }
        System.out.println("Enter the number of days until the reservation expires:");
        int nrDays = scanner.nextInt();
        scanner.nextLine();

        Reservation reservation = new Reservation(libraryMember, book, java.time.LocalDate.now().plusDays(nrDays), location);
        libraryMember.addReservation(reservation);
        book.addReservation(reservation);
        location.addReservation(reservation);
    }

    private Book chooseBook(Scanner scanner) {
        System.out.println("How do you want to search the book? [title/author]");
        String option = scanner.nextLine().toLowerCase();
        System.out.println("Enter:");
        String search = scanner.nextLine();
        switch (option) {
            case "title":
                bookDAOService.getBooksByTitle(search);
            case "author":
                bookDAOService.getBooksByAuthor(search);
            default:
                System.out.println("wrong option");
        }
        System.out.println("Enter the ISBN of the book:");
        String isbn = scanner.nextLine();
        Book book = bookDAOService.getBookByISBN(isbn);
        if(book == null) {
            System.out.println("wrong ISBN");
        }
        return book;
    }

    private Location chooseLocation(Scanner scanner) {
        System.out.println("Enter the branch library's name:");
        String name = scanner.nextLine().toLowerCase();
        System.out.println("Enter the location in the branch library:");
        String loc = scanner.nextLine().toLowerCase();
        BranchLibrary branchLibrary = branchLibraryDAOService.getBranchLibrary(name);
        if(branchLibrary == null) {
            System.out.println("There is no branch library having this name");
            return null;
        }
        Location location = locationDAOService.getLocationByBranchAndName(branchLibrary, loc);
        if(location == null) {
            System.out.println("There is no location having this name");
            return null;
        }
        return location;
    }

    private LibraryMember chooseLibraryMember(Scanner scanner) {
        System.out.println("Enter the ID of the library member:");
        int memberId = scanner.nextInt();
        scanner.nextLine();
        LibraryMember libraryMember = libraryMemberDAOService.getLibraryMemberById(memberId);
        if(libraryMember == null) {
            System.out.println("There is no library member having this ID");
            return null;
        }
        return libraryMember;
    }

    private Reservation findReservation(Scanner scanner) {
        System.out.println("Enter the search option for the reservation [book/library member/location");
        String option = scanner.nextLine().toLowerCase();
        Reservation reservation = new Reservation();
        List<Reservation> reservations = new ArrayList<>();
        int reservationId;

        switch (option) {
            case "book":
                Book book = chooseBook(scanner);
                reservations = databaseService.getReservationByBook(book);
                if(reservations.isEmpty()) {
                    System.out.println("There are no reservations for thos book");
                    return null;
                }
                System.out.println("Enter reservation ID:");
                reservationId = scanner.nextInt();
                scanner.nextLine();
                reservation = databaseService.getReservationById(reservationId);
                if(reservation == null) {
                    System.out.println("wrong ID");
                    return null;
                }
                break;
            case "library member":
                LibraryMember libraryMember = chooseLibraryMember(scanner);
                reservations = databaseService.getReservationByMember(libraryMember);
                if(reservations.isEmpty()) {
                    System.out.println("There are no reservations for thos book");
                    return null;
                }
                System.out.println("Enter reservation ID:");
                reservationId = scanner.nextInt();
                scanner.nextLine();
                reservation = databaseService.getReservationById(reservationId);
                if(reservation == null) {
                    System.out.println("wrong ID");
                    return null;
                }
                break;
            case "location":
                Location location = chooseLocation(scanner);
                reservations = databaseService.getReservationByLocation(location);
                if(reservations.isEmpty()) {
                    System.out.println("There are no reservations for thos book");
                    return null;
                }
                System.out.println("Enter reservation ID:");
                reservationId = scanner.nextInt();
                scanner.nextLine();
                reservation = databaseService.getReservationById(reservationId);
                if(reservation == null) {
                    System.out.println("wrong ID");
                    return null;
                }
                break;
            default:
                System.out.println("wrong option");
                return null;
        }

        return reservation;
    }

    public void read(Scanner scanner) {
        Reservation reservation = findReservation(scanner);
        if(reservation == null) {
            System.out.println("Couldn't find the reservation");
            return;
        }
        System.out.println(reservation);
    }

    public void delete(Scanner scanner) {
        Reservation reservation = findReservation(scanner);
        if(reservation == null) {
            System.out.println("Couldn't find the reservation");
        }
        databaseService.removeReservation(reservation.getId());
        reservation.getBook().removeReservation(reservation);
        reservation.getLibraryMember().removeReservation(reservation);
        reservation.getPickupLocation().removeReservation(reservation);
    }

    public void update(Scanner scanner) {
        Reservation reservation = findReservation(scanner);
        if(reservation == null) {
            System.out.println("Couldn't find the reservation");
            return;
        }
        Location newLocation = chooseLocation(scanner);
        System.out.println("Enter the number of days you want to add to the expiring date:");
        int nrDays = scanner.nextInt();
        scanner.nextLine();
        reservation.setPickupLocation(newLocation);
        reservation.setExpiryDate(reservation.getExpiryDate().plusDays(nrDays));
    }
}

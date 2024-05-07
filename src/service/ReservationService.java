package service;

import daoservices.*;
import model.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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

        List<Reservation> reservations = databaseService.getReservationByMember(libraryMember);
        if(reservations != null) {
            for (Reservation r : reservations) {
                if (r.getBook().equals(book) && r.getPickupLocation().equals(location) && r.getExpiryDate().isAfter(java.time.LocalDate.now())) {
                    System.out.println("There is already a reservation");
                    return;
                }
            }
        }
        System.out.println("Enter the number of days until the reservation expires:");
        int nrDays = scanner.nextInt();
        scanner.nextLine();

        Reservation reservation = new Reservation(libraryMember, book, java.time.LocalDate.now().plusDays(nrDays), location);
        databaseService.addReservation(reservation);
    }

    private Book chooseBook(Scanner scanner) {
//        System.out.println("How do you want to search the book? [title/author]");
//        String option = scanner.nextLine().toLowerCase();
//        System.out.println("Enter:");
//        String search = scanner.nextLine();
//        switch (option) {
//            case "title":
//                bookRepositoryService.getBooksByTitle(search);
//                break;
//            case "author":
//                bookRepositoryService.getBooksByAuthor(search);
//                break;
//            default:
//                System.out.println("wrong option");
//                return null;
//        }
//        System.out.println("Enter the ISBN of the book:");
//        String isbn = scanner.nextLine();
//        Book book = bookRepositoryService.getBookByISBN(isbn);
//        if(book == null) {
//            System.out.println("wrong ISBN");
//        }
//        return book;
        return null;
    }

    private Location chooseLocation(Scanner scanner) {
//        System.out.println("Enter the branch library's name:");
//        String name = scanner.nextLine();
//        System.out.println("Enter the location in the branch library:");
//        String loc = scanner.nextLine();
//        BranchLibrary branchLibrary = branchLibraryRepositoryService.getBranchLibrary(name);
//        if(branchLibrary == null) {
//            return null;
//        }
//        Location location = locationRepositoryService.getLocationByBranchAndName(branchLibrary, loc);
//        if(location == null) {
//            return null;
//        }
//        return location;
        return null;
    }

    private LibraryMember chooseLibraryMember(Scanner scanner) {
//        System.out.println("Enter the ID of the library member:");
//        int memberId = scanner.nextInt();
//        scanner.nextLine();
//        LibraryMember libraryMember = libraryMemberRepositoryService.getLibraryMemberById(memberId);
//        if(libraryMember == null) {
//            System.out.println("There is no library member having this ID");
//            return null;
//        }
//        return libraryMember;
        return null;
    }

    private Reservation findReservation(Scanner scanner) {
        System.out.println("Enter the search option for the reservation [book/library member/location]");
        String option = scanner.nextLine().toLowerCase();
        Reservation reservation = new Reservation();
        List<Reservation> reservations = new ArrayList<>();
        int reservationId;

        switch (option) {
            case "book":
                Book book = chooseBook(scanner);
                if (book == null) return null;
                reservations = databaseService.getReservationByBook(book);
                if(reservations == null) {
                    System.out.println("There are no reservations for this book");
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
                if(libraryMember == null) return null;
                reservations = databaseService.getReservationByMember(libraryMember);
                if(reservations != null){
                    for(Reservation r: reservations) {
                        System.out.println(r.getId());
                        System.out.println(r.getBook());
                        System.out.println(r.getPickupLocation());
                        System.out.println(r.getExpiryDate());
                        System.out.println("---------------------------");
                    }
                } else {
                    System.out.println("There are no reservations for this library member");
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
                if(location == null) return null;
                reservations = databaseService.getReservationByLocation(location);
                if(reservations == null) {
                    System.out.println("There are no reservations for this location");
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
        reservation.getPickupLocation().removeReservation(reservation);
    }

    public void update(Scanner scanner) {
        Reservation reservation = findReservation(scanner);
        if(reservation == null) {
            System.out.println("Couldn't find the reservation");
            return;
        }
        System.out.println("Update the information:");
        Location newLocation = chooseLocation(scanner);
        System.out.println("Enter the number of days you want to add to the expiring date:");
        int nrDays = scanner.nextInt();
        scanner.nextLine();
        reservation.setPickupLocation(newLocation);
        reservation.setExpiryDate(reservation.getExpiryDate().plusDays(nrDays));
    }
}

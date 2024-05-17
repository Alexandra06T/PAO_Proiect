package daoservices;

import dao.*;
import model.*;
import org.w3c.dom.CDATASection;
import utils.InvalidDataException;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

public class ReservationRepositoryService {

    private ReservationDao reservationDao = ReservationDao.getInstance();
    private LibraryMemberDao libraryMemberDao = LibraryMemberDao.getInstance();
    private BranchLibraryDao branchLibraryDao = BranchLibraryDao.getInstance();
    private LocationDao locationDao = LocationDao.getInstance();
    private BookDao bookDao = BookDao.getInstance();

    public ReservationRepositoryService() throws SQLException {}

    public void printAll() throws InvalidDataException {
        try {
            List<Reservation> reservations = reservationDao.getAll();
            if(reservations == null)
                throw new InvalidDataException("There is no reservation.");

            reservations.stream().collect(groupingBy(Reservation::getLibraryMemberID)).forEach((lm, rs) -> {
                try {
                    LibraryMember libraryMember = libraryMemberDao.read(String.valueOf(lm));
                    System.out.println(libraryMember.getMemberID() + " " + libraryMember.getName());
                    System.out.println("----------------------------------");
                    rs.forEach(r -> {
                        try {
                            System.out.println(r);
                            Book book = bookDao.read(r.getBookID());
                            System.out.println(book.getTitle() + " by " + book.getAuthors().stream().collect(Collectors.joining("; ")));
                            Location location = locationDao.read(String.valueOf(r.getPickupLocationID()));
                            BranchLibrary b = branchLibraryDao.readByID(String.valueOf(location.getBranchLibraryID()));
                            System.out.println(b.getName().toUpperCase() + ", " + location.getName());
                        } catch (SQLException e) {
                        System.out.println("SQLException " + e.getSQLState() + " " + e.getMessage());
                    }
                    });
                    System.out.println('\n');
                } catch (SQLException e) {
                    System.out.println("SQLException " + e.getSQLState() + " " + e.getMessage());
                }
            });

        } catch (SQLException e) {
            System.out.println("SQLException " + e.getSQLState() + " " + e.getMessage());
        }
    }

    public List<Reservation> getAll() throws InvalidDataException {
        try {
            List<Reservation> reservations = reservationDao.getAll();
            if(reservations == null)
                throw new InvalidDataException("There is no reservation.");
            return reservations;

        } catch (SQLException e) {
            System.out.println("SQLException " + e.getSQLState() + " " + e.getMessage());
        }
        return null;
    }

    public Reservation getReservationById(int id) throws InvalidDataException{
        try {
            Reservation reservation = reservationDao.read(String.valueOf(id));
            if(reservation == null){
                throw new InvalidDataException("There is no reservation having this id");
            }
            return reservation;
        } catch (SQLException e) {
            System.out.println("SQLException " + e.getSQLState() + " " + e.getMessage());
        }
        return null;
    }

    public void printReservationsByBook(String bookISBN) throws InvalidDataException {
        try {
            List<Reservation> reservations = reservationDao.readByBook(bookISBN);
            if(reservations == null)
                throw new InvalidDataException("There is no reservation for this book.");

            reservations.stream().collect(groupingBy(Reservation::getLibraryMemberID)).forEach((lm, rs) -> {
                try {
                    LibraryMember libraryMember = libraryMemberDao.read(String.valueOf(lm));
                    System.out.println(libraryMember.getMemberID() + " " + libraryMember.getName());
                    System.out.println("----------------------------------");
                    rs.forEach(r -> {
                        try {
                            Location location = locationDao.read(String.valueOf(r.getPickupLocationID()));
                            BranchLibrary branchLibrary = branchLibraryDao.readByID(String.valueOf(location.getBranchLibraryID()));
                            System.out.println(r);
                            System.out.println(branchLibrary.getName().toUpperCase() + ", " + location);
                        } catch (SQLException e) {
                            System.out.println("SQLException " + e.getSQLState() + " " + e.getMessage());
                        }
                    });
                    System.out.println();
                } catch (SQLException e) {
                    System.out.println("SQLException " + e.getSQLState() + " " + e.getMessage());
                }
            });

        } catch (SQLException e) {
            System.out.println("SQLException " + e.getSQLState() + " " + e.getMessage());
        }
    }
    public List<Reservation> getReservationsByBook(String bookISBN) throws InvalidDataException {
        try {
            List<Reservation> reservations = reservationDao.readByBook(bookISBN);
            if(reservations == null)
                throw new InvalidDataException("There is no reservation for this book.");

            return reservations;

        } catch (SQLException e) {
            System.out.println("SQLException " + e.getSQLState() + " " + e.getMessage());
        }
        return null;
    }

    public void printReservationsByMember(int libraryMemberID) throws InvalidDataException{
        try {
            List<Reservation> reservations = reservationDao.readByLibraryMember(libraryMemberID);
            if(reservations == null)
                throw new InvalidDataException("There is no reservation for this library member.");

            reservations.stream().collect(groupingBy(Reservation::getPickupLocationID)).forEach((l, rs) -> {
                try {
                    Location location = locationDao.read(String.valueOf(l));
                    BranchLibrary b = branchLibraryDao.readByID(String.valueOf(location.getBranchLibraryID()));
                    System.out.println(b.getName().toUpperCase() + ", " + location.getName());
                    System.out.println("----------------------------------");
                    rs.forEach(r -> {
                        try {
                            Book book = bookDao.read(String.valueOf(r.getBookID()));
                            System.out.println(r);
                            System.out.println(book.getTitle() + " by " + String.join(";", book.getAuthors()));
                            System.out.println();
                        } catch (SQLException e) {
                            System.out.println("SQLException " + e.getSQLState() + " " + e.getMessage());
                        }
                    });
                    System.out.println();
                } catch (SQLException e) {
                    System.out.println("SQLException " + e.getSQLState() + " " + e.getMessage());
                }
            });

        } catch (SQLException e) {
            System.out.println("SQLException " + e.getSQLState() + " " + e.getMessage());
        }
    }

    public List<Reservation> getReservationsByMember(int libraryMemberID) throws InvalidDataException{
        try {
            List<Reservation> reservations = reservationDao.readByLibraryMember(libraryMemberID);
            if(reservations == null)
                throw new InvalidDataException("There is no reservation for this library member.");

            return reservations;

        } catch (SQLException e) {
            System.out.println("SQLException " + e.getSQLState() + " " + e.getMessage());
        }

        return null;
    }

    public void printReservationsByLocation(int locationID) throws InvalidDataException {
        try {
            List<Reservation> reservations = reservationDao.readByLocation(locationID);
            if(reservations == null)
                throw new InvalidDataException("There is no reservation for this location.");

            reservations.stream().collect(groupingBy(Reservation::getLibraryMemberID)).forEach((lm, rs) -> {
                try {
                    LibraryMember libraryMember = libraryMemberDao.read(String.valueOf(lm));
                    System.out.println(libraryMember.getMemberID() + " " + libraryMember.getName());
                    System.out.println("----------------------------------");
                    rs.forEach(r -> {
                        try {
                            Book book = bookDao.read(String.valueOf(r.getBookID()));
                            System.out.println(r);
                            System.out.println(book.getTitle() + " by " + String.join(";", book.getAuthors()));
                            System.out.println();
                        } catch (SQLException e) {
                            System.out.println("SQLException " + e.getSQLState() + " " + e.getMessage());
                        }
                    });
                    System.out.println();
                } catch (SQLException e) {
                    System.out.println("SQLException " + e.getSQLState() + " " + e.getMessage());
                }
            });

        } catch (SQLException e) {
            System.out.println("SQLException " + e.getSQLState() + " " + e.getMessage());
        }
    }

    public List<Reservation> getReservationsByLocation(int locationID) throws InvalidDataException {
        try {
            List<Reservation> reservations = reservationDao.readByLocation(locationID);
            if(reservations == null)
                throw new InvalidDataException("There is no reservation for this location.");

            return reservations;

        } catch (SQLException e) {
            System.out.println("SQLException " + e.getSQLState() + " " + e.getMessage());
        }
        return null;
    }

    public void removeReservation(Reservation reservation) throws InvalidDataException {
        if (reservation == null) {
            throw new InvalidDataException("Invalid reservation");
        }
        try {
            reservationDao.delete(reservation);
        } catch (SQLException e) {
            System.out.println("SQLException " + e.getSQLState() + " " + e.getMessage());
        }
    }

    public void addReservation(Reservation reservation) throws InvalidDataException {
        if (reservation == null)
            throw new InvalidDataException("Invalid reservation");
        try {
            Location location = locationDao.read(String.valueOf(reservation.getPickupLocationID()));
            if(!checkBookCopiesExistence(location, reservation.getBookID())) {
                throw new InvalidDataException("There is no copy of the book in this location.");
            }
            Optional<Reservation> dupl = reservationDao.getAll()
                    .stream().filter(x -> x.getBookID().equals(reservation.getBookID()) && x.getLibraryMemberID() == reservation.getLibraryMemberID() && x.getPickupLocationID() == reservation.getPickupLocationID())
                    .findFirst();
            if(dupl.isPresent()) {
                throw new InvalidDataException("There is already a reservation for this book and library member in this location");
            }
            reservationDao.add(reservation);
        } catch (SQLException e) {
            System.out.println("SQLException " + e.getSQLState() + " " + e.getMessage());
        }
    }

    private boolean checkBookCopiesExistence(Location location, String bookID) throws SQLException {
        Map<BranchLibrary, Map<Location, Integer>> blWithBC = reservationDao.locationsWithBookCopies(bookID);
        Map<Location, Integer> locWithBC = blWithBC.get(branchLibraryDao.readByID(String.valueOf(location.getBranchLibraryID())));
        return locWithBC != null && locWithBC.containsKey(location);
    }

    public void printLocationsWithBookCopies(String bookID) throws InvalidDataException {
        try {
            Map<BranchLibrary, Map<Location, Integer>> locWithBC = reservationDao.locationsWithBookCopies(bookID);
            if(locWithBC == null) {
                throw new InvalidDataException("Couldn't find copies of this book");
            }
            locWithBC.forEach((bl,m) -> {
                System.out.println(bl.getName().toUpperCase());
                m.forEach((l, n) -> {
                    System.out.println(l + "   " + n + " book copies");
                });
                System.out.println();
            });
        } catch (SQLException e) {
            System.out.println("SQLException " + e.getSQLState() + " " + e.getMessage());
        }
    }

    public void updateReservation(Reservation reservation) throws InvalidDataException {
        if(reservation == null)
            throw new InvalidDataException("Invalid reservation");
        try {
            Location location = locationDao.read(String.valueOf(reservation.getPickupLocationID()));
            if(!checkBookCopiesExistence(location, reservation.getBookID())) {
                throw new InvalidDataException("There is no copy of the book in this location.");
            }
            Optional<Reservation> dupl = reservationDao.readByBook(reservation.getBookID())
                    .stream().filter(x -> x.getLibraryMemberID() == reservation.getLibraryMemberID() && x.getPickupLocationID() == reservation.getPickupLocationID())
                    .findFirst();

            if(dupl.isPresent() && dupl.get().getReservationID() != reservation.getReservationID())
                throw new InvalidDataException("There is already a reservation for this book and library member in this location");
            reservationDao.update(reservation);
            System.out.println("Reservation updated successfully!");
        } catch (SQLException e) {
            System.out.println("SQLException " + e.getSQLState() + " " + e.getMessage());
        }
    }

    public void cancelReservation(int libraryMemberID, int locationID, String bookID, LocalDate localDate) {
        try {
            List<Reservation> reservations = getReservationsByMember(libraryMemberID).stream().
                    filter(r -> r.getPickupLocationID() == locationID && r.getBookID().equals(bookID)).toList();
            if(!reservations.isEmpty()) {
                Reservation reservation = reservations.getFirst();
                reservation.setExpiryDate(localDate);
                reservationDao.update(reservation);
            }
        } catch (SQLException e) {
            System.out.println("SQLException " + e.getSQLState() + " " + e.getMessage());
        } catch (InvalidDataException e) {
            return;
        }

    }
}

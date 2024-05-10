package dao;

import daoservices.DatabaseConnection;
import model.*;
import utils.FileManagement;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.time.LocalDate;
import java.util.Map;

import static utils.Constants.AUDIT_FILE;

public class ReservationDao implements DaoInterface<Reservation> {
    private static ReservationDao reservationDao;

    private Connection connection = DatabaseConnection.getConnection();

    private ReservationDao() throws SQLException {}

    public static ReservationDao getInstance() throws SQLException {
        if(reservationDao == null) {
            reservationDao = new ReservationDao();
        }
        return reservationDao;
    }

    @Override
    public List<Reservation> getAll() throws SQLException{
        String sql = "SELECT * FROM libraryms.reservation";
        ResultSet rs = null;
        List<Reservation> reservations = new ArrayList<>();
        try(PreparedStatement statement = connection.prepareStatement(sql)) {
            rs = statement.executeQuery();
            while (rs.next()) {
                Reservation reservation = new Reservation(rs.getInt("librarymemberID"), rs.getString("bookID"), rs.getDate("expirydate").toLocalDate(), rs.getInt("pickuplocation"));
                reservation.setReservationID(rs.getInt("ID"));
                reservations.add(reservation);
            }

        } finally {
            if(rs != null) {
                rs.close();
            }
            FileManagement.writeIntoFile(AUDIT_FILE, "Reservation: get all " + java.time.Instant.now());

        }
        if(reservations.isEmpty()) return null;
        return reservations;
    }

    @Override
    public Reservation read(String id) throws SQLException{
        String sql = "SELECT * " +
                "FROM libraryms.reservation WHERE ID = ?";
        ResultSet rs = null;
        try(PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, id);
            rs = statement.executeQuery();
            FileManagement.writeIntoFile(AUDIT_FILE, "Reservation: read " + java.time.Instant.now());

            if(rs.next()) {
                Reservation reservation = new Reservation(rs.getInt("librarymemberID"), rs.getString("bookID"), rs.getDate("expirydate").toLocalDate(), rs.getInt("pickuplocation"));
                reservation.setReservationID(rs.getInt("ID"));
                return reservation;
            }
            return null;
        }
    }

    public List<Reservation> readByBook(String bookISBN) throws SQLException{
        String sql = "SELECT * " +
                "FROM libraryms.reservation WHERE bookID LIKE ?";
        ResultSet rs = null;
        List<Reservation> reservations = new ArrayList<>();
        try(PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, bookISBN);
            rs = statement.executeQuery();
            FileManagement.writeIntoFile(AUDIT_FILE, "Reservation: read by book " + java.time.Instant.now());

            while(rs.next()) {
                Reservation reservation = new Reservation(rs.getInt("librarymemberID"), rs.getString("bookID"), rs.getDate("expirydate").toLocalDate(), rs.getInt("pickuplocation"));
                reservation.setReservationID(rs.getInt("ID"));
                reservations.add(reservation);
            }
            if(reservations.isEmpty()) return null;
            return reservations;
        }
    }

    public List<Reservation> readByLibraryMember(int libraryMemberID) throws SQLException{
        String sql = "SELECT * " +
                "FROM libraryms.reservation WHERE librarymemberID = ?";
        ResultSet rs = null;
        List<Reservation> reservations = new ArrayList<>();
        try(PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, libraryMemberID);
            rs = statement.executeQuery();
            FileManagement.writeIntoFile(AUDIT_FILE, "Reservation: read by library member " + java.time.Instant.now());

            while(rs.next()) {
                Reservation reservation = new Reservation(rs.getInt("librarymemberID"), rs.getString("bookID"), rs.getDate("expirydate").toLocalDate(), rs.getInt("pickuplocation"));
                reservation.setReservationID(rs.getInt("ID"));
                reservations.add(reservation);
            }
            if(reservations.isEmpty()) return null;
            return reservations;
        }
    }

    public List<Reservation> readByLocation(int locationID) throws SQLException{
        String sql = "SELECT * " +
                "FROM libraryms.reservation WHERE reservation.pickuplocation = ?";
        ResultSet rs = null;
        List<Reservation> reservations = new ArrayList<>();
        try(PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, locationID);
            rs = statement.executeQuery();
            FileManagement.writeIntoFile(AUDIT_FILE, "Reservation: read by location " + java.time.Instant.now());

            while(rs.next()) {
                Reservation reservation = new Reservation(rs.getInt("librarymemberID"), rs.getString("bookID"), rs.getDate("expirydate").toLocalDate(), rs.getInt("pickuplocation"));
                reservation.setReservationID(rs.getInt("ID"));
                reservations.add(reservation);
            }
            if(reservations.isEmpty()) return null;
            return reservations;
        }
    }

    @Override
    public void delete(Reservation reservation) throws SQLException {
        String sql = "DELETE FROM libraryms.reservation WHERE ID = ?";
        try(PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, reservation.getReservationID());
            statement.executeUpdate();
        }
        FileManagement.writeIntoFile(AUDIT_FILE, "Reservation: delete " + java.time.Instant.now());

    }

    @Override
    public void add(Reservation reservation) throws SQLException {
        String sql = "INSERT INTO libraryms.reservation (expirydate, bookID, librarymemberID, pickuplocation) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setDate(1, Date.valueOf(reservation.getExpiryDate()));
            statement.setString(2, reservation.getBookID());
            statement.setInt(3, reservation.getLibraryMemberID());
            statement.setInt(4, reservation.getPickupLocationID());

            statement.executeUpdate();
        }
        FileManagement.writeIntoFile(AUDIT_FILE, "Reservation: add " + java.time.Instant.now());

    }

    public Map<BranchLibrary, Map<Location, Integer>> locationsWithBookCopies(String bookID) throws SQLException {
        String sql = "SELECT l.ID AS locationID, l.name AS locationName, b2.ID AS branchLibraryID, b2.name AS branchLibraryName, b2.address AS branchLibraryAddress, COUNT(*) AS nr " +
                "FROM libraryms.location l INNER JOIN libraryms.bookcopy b on l.ID = b.locationID " +
                "INNER JOIN libraryms.branchlibrary b2 on l.branchlibraryID = b2.ID " +
                "WHERE b.bookID LIKE ? " +
                "GROUP BY b2.ID, b2.name, b2.address, l.ID, l.name";
        ResultSet rs = null;
        Map<BranchLibrary, Map<Location, Integer>> bookCopiesPerLocation = new HashMap<>();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, bookID);

            rs = statement.executeQuery();
            while(rs.next()) {
                BranchLibrary branchLibrary = new BranchLibrary();
                branchLibrary.setBranchLibraryID(rs.getInt("branchLibraryID"));
                branchLibrary.setName(rs.getString("branchLibraryName"));
                branchLibrary.setAddress(rs.getString("branchLibraryAddress"));
                Location rslocation = new Location();
                rslocation.setLocationID(rs.getInt("locationID"));
                rslocation.setName(rs.getString("locationName"));
                rslocation.setBranchLibrary(rs.getInt("branchLibraryID"));
                if(!bookCopiesPerLocation.containsKey(branchLibrary))
                    bookCopiesPerLocation.put(branchLibrary, new HashMap<Location, Integer>());
                bookCopiesPerLocation.get(branchLibrary).put(rslocation, rs.getInt("nr"));

            }
            FileManagement.writeIntoFile(AUDIT_FILE, "Reservation: get locations with book copies " + java.time.Instant.now());

            if(bookCopiesPerLocation.isEmpty()) return null;
            return bookCopiesPerLocation;
        }
    }

    @Override
    public void update(Reservation reservation) throws SQLException {
        String sql = "UPDATE libraryms.reservation set expirydate = ?, pickuplocation = ? where ID = ?";
        try(PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setDate(1, Date.valueOf(reservation.getExpiryDate()));
            statement.setInt(2, reservation.getPickupLocationID());
            statement.setInt(3, reservation.getReservationID());
            statement.executeUpdate();
        }
        FileManagement.writeIntoFile(AUDIT_FILE, "Reservation: update " + java.time.Instant.now());

    }
}

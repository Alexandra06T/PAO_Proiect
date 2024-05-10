package dao;

import daoservices.DatabaseConnection;
import model.Book;
import model.BookCopy;
import model.Location;
import utils.FileManagement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static utils.Constants.AUDIT_FILE;

public class BookCopyDao implements DaoInterface<BookCopy> {
    private static BookCopyDao bookCopyDao;

    private Connection connection = DatabaseConnection.getConnection();

    private BookCopyDao() throws SQLException {}

    public static BookCopyDao getInstance() throws SQLException {
        if(bookCopyDao == null) {
            bookCopyDao = new BookCopyDao();
        }
        return bookCopyDao;
    }

    @Override
    public List<BookCopy> getAll() throws SQLException{
        String sql = "SELECT * FROM libraryms.bookcopy";
        ResultSet rs = null;
        List<BookCopy> bookCopies = new ArrayList<>();
        try(PreparedStatement statement = connection.prepareStatement(sql)) {
            rs = statement.executeQuery();
            while (rs.next()) {
                BookCopy bookCopy = new BookCopy(rs.getString("bookID"), rs.getString("barcode"), rs.getString("index"),
                        rs.getInt("locationID"), rs.getBoolean("available"));
                bookCopy.setBookCopyID(rs.getInt("ID"));
                bookCopies.add(bookCopy);
            }

        } finally {
            if(rs != null) {
                rs.close();
            }
            else bookCopies = null;
        }
        FileManagement.writeIntoFile(AUDIT_FILE, "BookCopy: get all " + java.time.Instant.now());
        return bookCopies;
    }

    @Override
    public BookCopy read(String id) throws SQLException{
        String sql = "SELECT * FROM libraryms.bookcopy WHERE ID = ?";
        ResultSet rs = null;
        BookCopy bookCopy = null;
        try(PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, id);
            rs = statement.executeQuery();
            if(rs.next()) {
                bookCopy = new BookCopy(rs.getString("bookID"), rs.getString("barcode"), rs.getString("index"),
                        rs.getInt("locationID"), rs.getBoolean("available"));
                bookCopy.setBookCopyID(rs.getInt("ID"));
                return bookCopy;
            }
        } finally {
            if(rs != null) {
                rs.close();
            }
        }
        FileManagement.writeIntoFile(AUDIT_FILE, "BookCopy: read " + java.time.Instant.now());
        return bookCopy;
    }

    public List<BookCopy> readByBook(String bookISBN) throws SQLException {
        String sql = "SELECT * FROM libraryms.bookcopy WHERE bookID LIKE ?";
        ResultSet rs = null;
        List<BookCopy> bookCopies = new ArrayList<>();
        try(PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, bookISBN);
            rs = statement.executeQuery();
            while(rs.next()) {
                BookCopy bookCopy = new BookCopy(rs.getString("bookID"), rs.getString("barcode"), rs.getString("index"),
                        rs.getInt("locationID"), rs.getBoolean("available"));
                bookCopy.setBookCopyID(rs.getInt("ID"));
                bookCopies.add(bookCopy);
            }
        } finally {
            if(rs != null) {
                rs.close();
            }
        }
        FileManagement.writeIntoFile(AUDIT_FILE, "BookCopy: read by book " + java.time.Instant.now());
        if(bookCopies.isEmpty()) return null;
        return bookCopies;
    }

    @Override
    public void delete(BookCopy bookCopy) throws SQLException {
        String sql = "DELETE FROM libraryms.bookcopy WHERE ID = ?";
        try(PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, bookCopy.getBookCopyID());
            statement.executeUpdate();
        }
        FileManagement.writeIntoFile(AUDIT_FILE, "BookCopy: delete " + java.time.Instant.now());
    }

    @Override
    public void add(BookCopy bookCopy) throws SQLException {
        String sql = "INSERT INTO libraryms.bookcopy (barcode, `index`, available, bookID, locationID) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, bookCopy.getBarcode());
            statement.setString(2, bookCopy.getIndex());
            statement.setBoolean(3, bookCopy.isAvailable());
            statement.setString(4, bookCopy.getBookISBN());
            statement.setInt(5, bookCopy.getLocationID());

            statement.executeUpdate();
        }
        FileManagement.writeIntoFile(AUDIT_FILE, "BookCopy: add " + java.time.Instant.now());
    }

    @Override
    public void update(BookCopy bookCopy) throws SQLException {
        String sql = "UPDATE libraryms.bookcopy set barcode = ?, `index` = ?, available = ?, locationID = ? where ID = ?";
        try(PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, bookCopy.getBarcode());
            statement.setString(2, bookCopy.getIndex());
            statement.setBoolean(3, bookCopy.isAvailable());
            statement.setInt(4, bookCopy.getLocationID());
            statement.setInt(5, bookCopy.getBookCopyID());
            statement.executeUpdate();
        }
        FileManagement.writeIntoFile(AUDIT_FILE, "BookCopy: update " + java.time.Instant.now());
    }

    public void setAvailability(int bookCopyID, boolean availability) throws SQLException {
        String sql = "UPDATE libraryms.bookcopy b SET b.available = ? WHERE b.ID = ?";
        try(PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setBoolean(1, availability);
            statement.setInt(2, bookCopyID);

            statement.executeUpdate();
        }
        FileManagement.writeIntoFile(AUDIT_FILE, "BookCopy: set availability " + java.time.Instant.now());
    }

}

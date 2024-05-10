package dao;

import daoservices.DatabaseConnection;
import model.Book;
import model.Category;
import utils.FileManagement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static utils.Constants.AUDIT_FILE;

public class BookDao implements DaoInterface<Book> {
    private static BookDao bookDao;

    private Connection connection = DatabaseConnection.getConnection();

    private BookDao() throws SQLException {}

    public static BookDao getInstance() throws SQLException {
        if(bookDao == null) {
            bookDao = new BookDao();
        }
        return bookDao;
    }


    @Override
    public List<Book> getAll() throws SQLException{
        String sql = "SELECT * FROM libraryms.book b JOIN libraryms.category c ON c.categoryIndex = b.categoryID " +
                "ORDER BY b.title";
        ResultSet rs = null;
        List<Book> books = new ArrayList<>();
        try(PreparedStatement statement = connection.prepareStatement(sql)) {
            rs = statement.executeQuery();
            while (rs.next()) {
                List<String> a = Arrays.stream(rs.getString("authors").split(";")).toList();
                Book book = new Book(rs.getString("title"), a, rs.getString("ISBN"),
                        rs.getString("publishingHouse"), rs.getInt("publishedDate"), rs.getInt("numberOfPages"));
                book.setCategoryID(rs.getInt("categoryID"));
                books.add(book);
            }

        } finally {
            if(rs != null) {
                rs.close();
            }
            else books = null;
        }
        FileManagement.writeIntoFile(AUDIT_FILE, "Book: get all " + java.time.Instant.now());
        return books;
    }

    @Override
    public Book read(String isbn) throws SQLException{
        String sql = "SELECT * " +
                "FROM libraryms.book b INNER JOIN libraryms.category c ON (b.categoryID = c.categoryIndex) " +
                "WHERE b.ISBN LIKE ?";
        ResultSet rs = null;
        try(PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, isbn);
            rs = statement.executeQuery();
            if(rs.next()) {
                List<String> a = Arrays.stream(rs.getString("authors").split(";")).toList();
                Book book = new Book(rs.getString("title"), a, rs.getString("ISBN"),
                        rs.getString("publishingHouse"), rs.getInt("publishedDate"), rs.getInt("numberOfPages"));
                book.setCategoryID(rs.getInt("categoryID"));
                return book;
            }
            FileManagement.writeIntoFile(AUDIT_FILE, "Book: read " + java.time.Instant.now());
            return null;
        }
    }

    public List<Book> readTitle(String title) throws SQLException {
        String sql = "SELECT * FROM libraryms.book b INNER JOIN libraryms.category c ON c.categoryIndex = b.categoryID " +
                "WHERE UPPER(b.title) LIKE UPPER(?) ORDER BY b.title";
        ResultSet rs = null;
        List<Book> books = new ArrayList<>();
        try(PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, title);
            rs = statement.executeQuery();

            while (rs.next()) {
                List<String> a = Arrays.stream(rs.getString("authors").split(";")).toList();
                Book book = new Book(rs.getString("title"), a, rs.getString("ISBN"),
                        rs.getString("publishingHouse"), rs.getInt("publishedDate"), rs.getInt("numberOfPages"));
                book.setCategoryID(rs.getInt("categoryID"));
                books.add(book);
            }
            FileManagement.writeIntoFile(AUDIT_FILE, "Book: read by title " + java.time.Instant.now());
            if(books.isEmpty()) return null;
            return books;
        } finally {
            if(rs != null) {
                rs.close();
            }
        }
    }

    public List<Book> readAuthor(String author) throws SQLException {
        String sql = "SELECT * FROM libraryms.book b INNER JOIN libraryms.category c ON (c.categoryIndex = b.categoryID) " +
                "WHERE b.authors LIKE ?";
        ResultSet rs = null;
        List<Book> books = new ArrayList<>();
        try(PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, "%" + author.toUpperCase() + "%");
            rs = statement.executeQuery();
            while (rs.next()) {
                List<String> a = Arrays.stream(rs.getString("authors").split(";")).toList();
                Book book = new Book(rs.getString("title"), a, rs.getString("ISBN"),
                        rs.getString("publishingHouse"), rs.getInt("publishedDate"), rs.getInt("numberOfPages"));
                book.setCategoryID(rs.getInt("categoryID"));
                books.add(book);
            }
            FileManagement.writeIntoFile(AUDIT_FILE, "Book: read by author " + java.time.Instant.now());
            if(books.isEmpty()) return null;
            return books;
        }
    }

    @Override
    public void delete(Book book) throws SQLException {
        String sql = "DELETE FROM libraryms.book b WHERE b.ISBN LIKE ?";
        try(PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, book.getISBN());
            statement.executeUpdate();
        }
        FileManagement.writeIntoFile(AUDIT_FILE, "Book: delete " + java.time.Instant.now());
    }

    @Override
    public void add(Book book) throws SQLException {
        String sql = "INSERT INTO libraryms.book VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, book.getISBN());
            statement.setString(2, book.getTitle());
            statement.setString(3, String.join(";", book.getAuthors()));
            statement.setString(4, book.getPublishingHouse());
            statement.setInt(5, book.getPublishedDate());
            statement.setInt(6, book.getNumberOfPages());
            statement.setInt(7, book.getCategoryID());

            statement.executeUpdate();
        }
        FileManagement.writeIntoFile(AUDIT_FILE, "Book: add " + java.time.Instant.now());
    }

    @Override
    public void update(Book book) throws SQLException {
        String sql = "UPDATE libraryms.book set title = ?, authors = ?, publishingHouse = ?, publishedDate = ?, " +
                "numberOfPages = ?, categoryID = ? where ISBN LIKE ?";
        try(PreparedStatement statement = connection.prepareStatement(sql);) {
            statement.setString(1, book.getTitle());
            statement.setString(2, String.join(";", book.getAuthors()));
            statement.setString(3, book.getPublishingHouse());
            statement.setInt(4, book.getPublishedDate());
            statement.setInt(5, book.getNumberOfPages());
            statement.setInt(6, book.getCategoryID());
            statement.setString(7, book.getISBN());
            statement.executeUpdate();
        }
        FileManagement.writeIntoFile(AUDIT_FILE, "Book: update" + java.time.Instant.now());
    }
}
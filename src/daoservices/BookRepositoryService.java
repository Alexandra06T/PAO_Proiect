package daoservices;

import dao.BookCopyDao;
import dao.CategoryDao;
import dao.ReservationDao;
import model.Book;
import dao.BookDao;
import model.BookCopy;
import model.Category;
import model.Reservation;
import utils.InvalidDataException;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookRepositoryService {

    private BookDao bookDao = BookDao.getInstance();
    private CategoryDao categoryDao;
    private ReservationDao reservationDao;
    private BookCopyDao bookCopyDao;

    public BookRepositoryService() throws SQLException {
        this.reservationDao = new ReservationDao();
        this.bookCopyDao = new BookCopyDao();
    }

    public void printAll() throws InvalidDataException {
        try {
            List<Book> books = bookDao.getAll();
            if(books == null)
                throw new InvalidDataException("There is no book.");
            books.forEach(x ->
            {
                try {
                    System.out.println(x.toString() + categoryDao.read(String.valueOf(x.getCategoryID())).toString() + '\n');
                } catch (SQLException e) {
                    System.out.println("SQLException " + e.getSQLState() + " " + e.getMessage());
                }
            });

        } catch (SQLException e) {
            System.out.println("SQLException " + e.getSQLState() + " " + e.getMessage());
        }
    }

    public List<Book> getAll() throws InvalidDataException {
        try {
            List<Book> books = bookDao.getAll();
            if(books == null)
                throw new InvalidDataException("There is no book.");
            return books;

        } catch (SQLException e) {
            System.out.println("SQLException " + e.getSQLState() + " " + e.getMessage());
        }
        return null;
    }

    public Book getBookByISBN(String isbn) throws InvalidDataException {
        try {
            Book book = bookDao.read(isbn);
            if(book == null)
                throw new InvalidDataException("No book having this ISBN");
            return book;
        } catch (SQLException e) {
            System.out.println("SQLException " + e.getSQLState() + " " + e.getMessage());
        }
        return null;
    }

    public List<Book> getBooksByTitle(String title) throws InvalidDataException {
        try {
            List<Book> bookList = bookDao.readTitle(title);
            if(bookList == null) {
                throw new InvalidDataException("No book having this title");
            }
            return bookList;
        }
        catch (SQLException e) {
            System.out.println("SQLException " + e.getSQLState() + " " + e.getMessage());
        }
        return null;
    }

    public List<Book> getBooksByAuthor(String author) throws InvalidDataException {
        try {
            List<Book> bookList = bookDao.readAuthor(author);
            if(bookList == null) {
                throw new InvalidDataException("No book of this author");
            }
            return bookList;
        }
        catch (SQLException e) {
            System.out.println("SQLException " + e.getSQLState() + " " + e.getMessage());
        }
        return null;
    }

    public void removeBook(Book book) throws InvalidDataException {
        if (book == null) throw new InvalidDataException("Invalid book");
        try {
            bookDao.delete(book);
        } catch (SQLException e) {
            System.out.println("SQLException " + e.getSQLState() + " " + e.getMessage());
        }
    }

    public void addBook (Book book) throws InvalidDataException {
        if (book == null)
            throw new InvalidDataException("Invalid book");
        try {
            if(bookDao.read(book.getISBN()) != null) {
                throw new InvalidDataException("There is already a book having this ISBN");
            }
            if(book.getNumberOfPages() <= 0) {
                throw new InvalidDataException("Invalid number of pages");
            }
            bookDao.add(book);
        } catch (SQLException e) {
            System.out.println("SQLException " + e.getSQLState() + " " + e.getMessage());
        }
    }

    public void updateBook(Book book) throws InvalidDataException {
        if(book == null)
            throw new InvalidDataException("Invalid book");
        try {
            bookDao.update(book);
            System.out.println("Book updated successfully!");
        } catch (SQLException e) {
            System.out.println("SQLException " + e.getSQLState() + " " + e.getMessage());
        }
    }
}

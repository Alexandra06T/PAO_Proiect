package daoservices;

import dao.*;
import model.*;
import utils.InvalidDataException;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

public class BookCopyRepositoryService {

    private BookCopyDao bookCopyDao = BookCopyDao.getInstance();
    private LocationDao locationDao = LocationDao.getInstance();
    private BranchLibraryDao branchLibraryDao = BranchLibraryDao.getInstance();
    private BookDao bookDao = BookDao.getInstance();
    private CheckInDao checkInDao;
    private CheckOutDao checkOutDao;

    public BookCopyRepositoryService() throws SQLException {}

    public void printAll() throws InvalidDataException {
        try {
            List<BookCopy> bookCopies = bookCopyDao.getAll();
            if(bookCopies == null)
                throw new InvalidDataException("There is no book copy.");

            bookCopies.stream().collect(groupingBy(BookCopy::getLocationID, groupingBy(BookCopy::getBookISBN))).forEach((lid, gr) -> {
                try {
                    Location l = locationDao.read(String.valueOf(lid));
                    BranchLibrary bl = branchLibraryDao.readByID(String.valueOf(l.getBranchLibraryID()));
                    System.out.println(bl.getName() + ", " + l.getName().toUpperCase() + "\n#############################################\n");
                    gr.forEach((b, lb) -> {
                        try {
                            Book book = bookDao.read(b);
                            System.out.println(book.getTitle() + " by " + book.getAuthors().stream().collect(Collectors.joining("; ")) + "\n--------------------------------");
                            lb.forEach(System.out::println);
                        } catch (SQLException e) {
                            System.out.println("SQLException " + e.getSQLState() + " " + e.getMessage());
                        }
                        System.out.println();
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

    public List<BookCopy> getAll() throws InvalidDataException {
        try {
            List<BookCopy> bookCopies = bookCopyDao.getAll();
            if(bookCopies == null)
                throw new InvalidDataException("There is no book copy.");
            return bookCopies;

        } catch (SQLException e) {
            System.out.println("SQLException " + e.getSQLState() + " " + e.getMessage());
        }
        return null;
    }

    public BookCopy getBookCopyById(int id) throws InvalidDataException {
        try {
            BookCopy bookCopy = bookCopyDao.read(String.valueOf(id));
            if(bookCopy == null){
                throw new InvalidDataException("There is no book copy having this ID");
            }
            return bookCopy;
        } catch (SQLException e) {
            System.out.println("SQLException " + e.getSQLState() + " " + e.getMessage());
        }
        return null;
    }

    public List<BookCopy> getBookCopiesByBook(String bookISBN) throws InvalidDataException {
        try {
            List<BookCopy> bookCopies = bookCopyDao.readByBook(String.valueOf(bookISBN));
            if(bookCopies == null){
                throw new InvalidDataException("There is no copy of this book");
            }
            return bookCopies;
        } catch (SQLException e) {
            System.out.println("SQLException " + e.getSQLState() + " " + e.getMessage());
        }
        return null;
    }


    public List<BookCopy> printAvailableBookCopies(String bookISBN) throws InvalidDataException {
        try {
            List<BookCopy> bookCopies = bookCopyDao.readByBook(bookISBN).stream().filter(BookCopy::isAvailable).toList();
            if(bookCopies == null)
                throw new InvalidDataException("No available copies for this book");
            bookCopies.stream().collect(groupingBy(BookCopy::getLocationID, groupingBy(BookCopy::getBookISBN))).forEach((lid, gr) -> {
                try {
                    Location l = locationDao.read(String.valueOf(lid));
                    BranchLibrary bl = branchLibraryDao.readByID(String.valueOf(l.getBranchLibraryID()));
                    System.out.println(bl.getName() + ", " + l.getName().toUpperCase() + ": ");
                    gr.forEach((b, lb) -> {
                        try {
                            Book book = bookDao.read(b);
                            System.out.println(book.getTitle() + " by " + book.getAuthors() + "--------------------------------");
                            lb.forEach(System.out::println);
                            System.out.println();
                        } catch (SQLException e) {
                            System.out.println("SQLException " + e.getSQLState() + " " + e.getMessage());
                        }
                        System.out.println();
                    });
                    System.out.println();
                } catch (SQLException e) {
                    System.out.println("SQLException " + e.getSQLState() + " " + e.getMessage());
                }
            });
        } catch (SQLException e) {
            System.out.println("SQLException " + e.getSQLState() + " " + e.getMessage());
        }

        return null;
    }

    public void removeBookCopy(BookCopy bookCopy) throws InvalidDataException {
        if (bookCopy == null) throw new InvalidDataException("Invalid book copy");
        if(!bookCopy.isAvailable()) throw new InvalidDataException("The book copy is not available yet");
        try {
            bookCopyDao.delete(bookCopy);
        } catch (SQLException e) {
            System.out.println("SQLException " + e.getSQLState() + " " + e.getMessage());
        }
    }

    public void addBookCopy(BookCopy bookCopy) throws InvalidDataException {
        if (bookCopy == null)
            throw new InvalidDataException("Invalid book copy");
        try {
            bookCopyDao.add(bookCopy);
        } catch (SQLException e) {
            System.out.println("SQLException " + e.getSQLState() + " " + e.getMessage());
        }
    }

    public void updateBookCopy(BookCopy bookCopy) throws InvalidDataException {
        if(bookCopy == null)
            throw new InvalidDataException("Invalid book copy");
        try {
            bookCopyDao.update(bookCopy);
        } catch (SQLException e) {
            System.out.println("SQLException " + e.getSQLState() + " " + e.getMessage());
        }
    }

}

package daoservices;

import dao.BookCopyDao;
import dao.ReservationDao;
import model.Book;
import dao.BookDao;
import model.BookCopy;
import model.Reservation;

import java.util.List;

public class BookRepositoryService {

    private BookDao bookDao;
    private ReservationDao reservationDao;
    private BookCopyDao bookCopyDao;

    public BookRepositoryService() {
        this.bookDao = new BookDao();
        this.reservationDao = new ReservationDao();
        this.bookCopyDao = new BookCopyDao();
    }

    public Book getBookByISBN(String isbn){
        Book book = bookDao.readISBN(isbn);
        if(book == null)
            System.out.println("No book having this ISBN");

        return book;
    }

    public List<Book> getBooksByTitle(String title){
        List<Book> bookList = bookDao.readTitle(title);
        if(bookList != null){
            for(Book b : bookList) {
                System.out.println(b);
                System.out.println("-------------------------------");
            }
        }else {
            System.out.println("No book having this title");
        }

        return bookList;
    }

    public List<Book> getBooksByAuthor(String author){
        List<Book> bookList = bookDao.readAuthor(author);

        if(bookList != null){
            for(Book b : bookList) {
                System.out.println(b);
                System.out.println("-------------------------------");
            }
        }else {
            System.out.println("No book having this title");
        }

        return bookList;
    }

    public void removeBook(String isbn) {
        Book book = getBookByISBN(isbn);
        if (book == null) return;

        List<Reservation> reservationList = book.getReservations();
        //stergem toate rezervarile pentru carte
        for(Reservation r : reservationList) {
            reservationDao.delete(r);
        }

        //stergem toate copiile cartii
        List<BookCopy> bookCopyList = book.getBookCopies();
        for(BookCopy b : bookCopyList) {
            bookCopyDao.delete(b);
        }

        bookDao.delete(book);

        System.out.println("Removed " + book);
    }

    public void addBook (Book book) {
        if(book != null){
            if(bookDao.readISBN(book.getISBN()) != null) {
                System.out.println("There is already a book having this ISBN");
                return;
            }
            if(book.getNumberOfPages() <= 0) {
                System.out.println("Invalid number of pages");
                return;
            }
            bookDao.create(book);
        }
    }
}

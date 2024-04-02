package daoservices;

import model.Book;
import dao.BookDao;

import java.util.List;

public class BookRepositoryService {

    private BookDao bookDao;

    public BookRepositoryService() {
        this.bookDao = new BookDao();
    }

    public Book getBookByISBN(String isbn){
        Book book = bookDao.readISBN(isbn);
        if(book != null){
            System.out.println(book);
        }else {
            System.out.println("No book having this ISBN");
        }

        return book;
    }

    public List<Book> getBooksByTitle(String title){
        List<Book> bookList = bookDao.readTitle(title);
        if(bookList != null){
            for(Book b : bookList) {
                System.out.println(b.getTitle());
                System.out.println(b.getAuthors());
                System.out.println(b.getCategory());
                System.out.println(b.getISBN());
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
                System.out.println(b.getTitle());
                System.out.println(b.getAuthors());
                System.out.println(b.getCategory());
                System.out.println(b.getISBN());
            }
        }else {
            System.out.println("No book having this title");
        }

        return bookList;
    }

    public void removeBook(String isbn) {
        Book book = getBookByISBN(isbn);
        if (book == null) return;

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

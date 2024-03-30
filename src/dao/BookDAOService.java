package dao;

import model.Book;
import repository.BookRepository;

import java.util.List;

public class BookDAOService {

    private BookRepository bookRepository;

    public BookDAOService() {
        this.bookRepository = new BookRepository();
    }

    public Book getBookByISBN(String isbn){
        Book book = bookRepository.readISBN(isbn);
        if(book != null){
            System.out.println(book);
        }else {
            System.out.println("No book having this ISBN");
        }

        return book;
    }

    public List<Book> getBooksByTitle(String title){
        List<Book> bookList = bookRepository.readTitle(title);
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
        List<Book> bookList = bookRepository.readAuthor(author);

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

        bookRepository.delete(book);

        System.out.println("Removed " + book);
    }

    public void addBook (Book book) {
        if(book != null){
            if(bookRepository.readISBN(book.getISBN()) != null) {
                System.out.println("There is already a book having this ISBN");
                return;
            }
            if(book.getNumberOfPages() <= 0) {
                System.out.println("Invalid number of pages");
                return;
            }
            bookRepository.create(book);
        }
    }
}

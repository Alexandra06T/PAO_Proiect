package repository;

import model.Book;

import java.util.ArrayList;
import java.util.List;

public class BookRepository {
    private static List<Book> books = new ArrayList<>();

    public List<Book> readAll() {
        if(!books.isEmpty()){
            return books;
        }
        return null;
    }

    public Book readISBN(String isbn) {
        if(!books.isEmpty()){
            for(Book b : books){
                if(b.getISBN().equals(isbn)){
                    return b;
                }
            }
        }
        return null;
    }

    public List<Book> readTitle(String title) {
        List<Book> bookList = new ArrayList<>();

        if(!books.isEmpty()){
            for(Book b : books){
                if(b.getTitle().equals(title)){
                    bookList.add(b);
                }
            }
        }

        if(bookList.isEmpty()) return null;
        return bookList;
    }

    public List<Book> readAuthor(String author) {
        List<Book> bookList = new ArrayList<>();
        if(!books.isEmpty()){
            for(Book b : books){
                if(b.getAuthors().contains(author)){
                    bookList.add(b);
                }
            }
        }

        if(bookList.isEmpty()) return null;
        return bookList;
    }

    public void delete(Book book) {
        books.remove(book);
    }

    public void create(Book book) {
        books.add(book);
    }
}

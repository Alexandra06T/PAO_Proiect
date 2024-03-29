package repository;

import model.Book;

import java.util.ArrayList;
import java.util.List;

public class BookRepository {
    private static List<Book> books = new ArrayList<>();

    public Book read(String isbn) {
        if(!books.isEmpty()){
            for(Book b : books){
                if(b.getISBN().equals(isbn)){
                    return b;
                }
            }
        }
        return null;
    }

    public void delete(Book book) {
        books.remove(book);
    }

    public void create(Book book) {
        books.add(book);
    }
}

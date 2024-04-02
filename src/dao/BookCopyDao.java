package dao;

import model.Book;
import model.BookCopy;

import java.util.ArrayList;
import java.util.List;

public class BookCopyDao {
    private static List<BookCopy> copies = new ArrayList<>();

    public BookCopy read(Book book, int id) {
        if(!copies.isEmpty()){
            for(BookCopy c : copies){
                if(c.getBook().equals(book) && c.getId() == id){
                    return c;
                }
            }
        }
        return null;
    }


    public List<BookCopy> readAvailable(Book book) {
        List<BookCopy> bookCopyList = new ArrayList<>();
        if(!copies.isEmpty()){
            for(BookCopy c : copies){
                if(c.getBook().equals(book) && c.isAvailable()){
                    bookCopyList.add(c);
                }
            }
        }
        if(bookCopyList.isEmpty()) return null;
        return bookCopyList;
    }

    public void delete(BookCopy bookCopy) {
        copies.remove(bookCopy);
    }

    public void create(BookCopy bookCopy) {
        copies.add(bookCopy);
    }
}

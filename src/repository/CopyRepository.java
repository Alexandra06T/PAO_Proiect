package repository;

import model.Book;
import model.CheckIn;
import model.Copy;

import java.util.ArrayList;
import java.util.List;

public class CopyRepository {
    private static List<Copy> copies = new ArrayList<>();

    public Copy read(Book book, int id) {
        if(!copies.isEmpty()){
            for(Copy c : copies){
                if(c.getBook().equals(book) && c.getId() == id){
                    return c;
                }
            }
        }
        return null;
    }

    public List<Copy> readAvailable(Book book) {
        List<Copy> copyList = new ArrayList<>();
        if(!copies.isEmpty()){
            for(Copy c : copies){
                if(c.getBook().equals(book) && c.isAvailable()){
                    copyList.add(c);
                }
            }
        }
        if(copyList.isEmpty()) return null;
        return copyList;
    }

    public void delete(Copy copy) {
        copies.remove(copy);
    }

    public void create(Copy copy) {
        copies.add(copy);
    }
}

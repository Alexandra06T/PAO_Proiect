package daoservices;

import model.Book;
import model.Copy;
import dao.CopyDao;

import java.util.List;

public class CopyRepositoryService {

    private CopyDao copyDao;

    public CopyRepositoryService() {
        this.copyDao = new CopyDao();
    }

    public Copy getCopyByBookAndId(Book book, int id){
        Copy copy = copyDao.read(book, id);
        if(copy != null){
            System.out.println(copy);
        }else {
            System.out.println("No copy of the specified book having this id");
        }

        return copy;
    }


    public List<Copy> getAvailableCopies(Book book){
        List<Copy> copyList = copyDao.readAvailable(book);
        if(copyList != null){
            for(Copy c : copyList) {
                System.out.print(c.getId());
                System.out.println(c.getLocation() + c.getIndex());
            }
        }else {
            System.out.println("No available copies for this book");
        }
        return copyList;
    }

    public void removeCopy(Book book, int id) {
        Copy copy = getCopyByBookAndId(book, id);
        if (copy == null) return;

        copyDao.delete(copy);

        System.out.println("Removed " + copy);
    }

    public void addCopy(Copy copy) {
        if(copy != null){
            if(copyDao.read(copy.getBook(), copy.getId()) != null) {
                System.out.println("There is already a copy of this book having this id");
                return;
            }
            copyDao.create(copy);
        }
    }

}

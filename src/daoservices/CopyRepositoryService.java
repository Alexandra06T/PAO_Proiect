package daoservices;

import model.Book;
import model.BookCopy;
import dao.CopyDao;

import java.util.List;

public class CopyRepositoryService {

    private CopyDao copyDao;

    public CopyRepositoryService() {
        this.copyDao = new CopyDao();
    }

    public BookCopy getCopyByBookAndId(Book book, int id){
        BookCopy bookCopy = copyDao.read(book, id);
        if(bookCopy != null){
            System.out.println(bookCopy);
        }else {
            System.out.println("No bookCopy of the specified book having this id");
        }

        return bookCopy;
    }


    public List<BookCopy> getAvailableCopies(Book book){
        List<BookCopy> bookCopyList = copyDao.readAvailable(book);
        if(bookCopyList != null){
            for(BookCopy c : bookCopyList) {
                System.out.print(c.getId());
                System.out.println(c.getLocation() + c.getIndex());
            }
        }else {
            System.out.println("No available copies for this book");
        }
        return bookCopyList;
    }

    public void removeCopy(Book book, int id) {
        BookCopy bookCopy = getCopyByBookAndId(book, id);
        if (bookCopy == null) return;

        copyDao.delete(bookCopy);

        System.out.println("Removed " + bookCopy);
    }

    public void addCopy(BookCopy bookCopy) {
        if(bookCopy != null){
            if(copyDao.read(bookCopy.getBook(), bookCopy.getId()) != null) {
                System.out.println("There is already a bookCopy of this book having this id");
                return;
            }
            copyDao.create(bookCopy);
        }
    }

}

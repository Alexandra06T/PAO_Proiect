package daoservices;

import dao.CheckInDao;
import dao.CheckOutDao;
import model.*;
import dao.BookCopyDao;

import java.util.List;

public class BookCopyRepositoryService {

    private BookCopyDao bookCopyDao;
    private CheckInDao checkInDao;
    private CheckOutDao checkOutDao;

    public BookCopyRepositoryService() {
        this.bookCopyDao = new BookCopyDao();
    }

    public BookCopy getCopyByBookAndId(Book book, int id){
        BookCopy bookCopy = bookCopyDao.read(book, id);
        if(bookCopy == null)
            System.out.println("No book copy of the specified book having this id");

        return bookCopy;
    }


    public List<BookCopy> getAvailableCopies(Book book){
        List<BookCopy> bookCopyList = bookCopyDao.readAvailable(book);
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

        //stergem toate tranzactiile pentu copie
        List<Transaction> transactionList = bookCopy.getTransactions();
        if(!transactionList.isEmpty()) {
            for(Transaction t : transactionList) {
                switch (t){
                    case CheckIn checkIn -> checkInDao.delete(checkIn);
                    case CheckOut checkOut -> checkOutDao.delete(checkOut);
                    default -> throw new IllegalStateException("Unexpected value: " + t);
                }
            }
        }

        bookCopyDao.delete(bookCopy);

        System.out.println("Removed " + bookCopy);
    }

    public void addCopy(BookCopy bookCopy) {
        if(bookCopy != null){
            if(bookCopyDao.read(bookCopy.getBook(), bookCopy.getId()) != null) {
                System.out.println("There is already a bookCopy of this book having this id");
                return;
            }
            bookCopyDao.create(bookCopy);
        }
    }

}

package service;

import daoservices.*;
import model.*;

import java.util.Scanner;

public class BookCopyService {

    private BookRepositoryService bookRepositoryService;
    private LocationRepositoryService locationRepositoryService;
    private BranchLibraryRepositoryService branchLibraryRepositoryService;
    private BookCopyRepositoryService databaseService;

    public BookCopyService(){
        this.databaseService = new BookCopyRepositoryService();
        this.bookRepositoryService = new BookRepositoryService();
        this.locationRepositoryService = new LocationRepositoryService();
        this.branchLibraryRepositoryService = new BranchLibraryRepositoryService();
    }

    public void create(Scanner scanner) {
        BookCopy bookCopy = new BookCopy();
        setGeneralInfo(scanner, bookCopy);
        Book book = chooseBook(scanner);
        if(book == null) {
            System.out.println("Couldn't set the book");
            return;
        }
        bookCopy.setBook(book);
        Location location = chooseLocation(scanner);
        if(location == null) {
            System.out.println("Couldn't set the location");
            return;
        }
        bookCopy.setLocation(location);
        int nr = book.getBookCopies().size();
        bookCopy.setId(nr+1);
        databaseService.addCopy(bookCopy);
        System.out.println("The bookCopy was added to the catalogue");
        book.addBookCopy(bookCopy);
    }

    private void setGeneralInfo(Scanner scanner, BookCopy bookCopy) {
        System.out.println("Enter the barcode of the bookCopy:");
        String barcode = scanner.nextLine();
        System.out.println("Enter the index of the bookCopy:");
        String index = scanner.nextLine();
        bookCopy.setBarcode(barcode);
        bookCopy.setIndex(index);
        bookCopy.setAvailable(false);
    }


    private Book chooseBook(Scanner scanner) {
        System.out.println("How do you want to search the book? [title/author]");
        String option = scanner.nextLine().toLowerCase();
        System.out.println("Enter:");
        String search = scanner.nextLine();
        switch (option) {
            case "title":
                bookRepositoryService.getBooksByTitle(search);
                break;
            case "author":
                bookRepositoryService.getBooksByAuthor(search);
                break;
            default:
                System.out.println("wrong option");
        }
        System.out.println("Enter the ISBN of the book:");
        String isbn = scanner.nextLine();
        Book book = bookRepositoryService.getBookByISBN(isbn);
        if(book == null) {
            System.out.println("wrong ISBN");
        }
        return book;
    }

    private Location chooseLocation(Scanner scanner) {
        System.out.println("Enter the branch library's name:");
        String name = scanner.nextLine();
        BranchLibrary branchLibrary = branchLibraryRepositoryService.getBranchLibrary(name);
        if(branchLibrary == null) {
            System.out.println("There is no branch library having this name");
            return null;
        }
        System.out.println("Enter the location in the branch library:");
        String loc = scanner.nextLine();
        Location location = locationRepositoryService.getLocationByBranchAndName(branchLibrary, loc);
        if(location == null) {
            System.out.println("There is no location having this name");
            return null;
        }
        return location;
    }

    private BookCopy findCopy(Scanner scanner) {
        Book book = chooseBook(scanner);
        if(book == null) {
            System.out.println("Couldn't find the book");
            return null;
        }
        if(book.getBookCopies().isEmpty()){
            System.out.println("The book has no copies");
            return null;
        }
        System.out.println(book.getBookCopies());
        System.out.println("Enter the id of the bookCopy");
        int id = scanner.nextInt();
        scanner.nextLine();
        BookCopy bookCopy = databaseService.getCopyByBookAndId(book, id);
        if(bookCopy == null) {
            System.out.println("wrong id");
            return null;
        }
        return bookCopy;
    }

    public void read(Scanner scanner) {
        BookCopy bookCopy = findCopy(scanner);
        if(bookCopy == null) {
            System.out.println("Couldn't find the bookCopy");
            return;
        }
        System.out.println(bookCopy);
    }

    public void delete(Scanner scanner) {
        BookCopy bookCopy = findCopy(scanner);
        if(bookCopy == null) {
            System.out.println("Couldn't find the bookCopy");
        }
        databaseService.removeCopy(bookCopy.getBook(), bookCopy.getId());
        bookCopy.getBook().removeBookCopy(bookCopy);
    }

    public void update(Scanner scanner) {
        BookCopy bookCopy = findCopy(scanner);
        if(bookCopy == null) {
            System.out.println("Couldn't find the bookCopy");
        }
        BookCopy newcopy = new BookCopy();
        setGeneralInfo(scanner, newcopy);
        bookCopy.setBarcode(newcopy.getBarcode());
        bookCopy.setIndex(newcopy.getIndex());
        Location location = chooseLocation(scanner);
        if(location == null) {
            System.out.println("Couldn't set the location");
            return;
        }
        bookCopy.setLocation(location);
    }
}

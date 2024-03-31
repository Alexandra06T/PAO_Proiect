package service;

import dao.*;
import model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CopyService {

    private BookDAOService bookDAOService;
    private LocationDAOService locationDAOService;
    private BranchLibraryDAOService branchLibraryDAOService;
    private CopyDAOService databaseService;

    public CopyService(){
        this.databaseService = new CopyDAOService();
        this.bookDAOService = new BookDAOService();
        this.locationDAOService = new LocationDAOService();
        this.branchLibraryDAOService = new BranchLibraryDAOService();
    }

    public void create(Scanner scanner) {
        Copy copy = new Copy();
        setGeneralInfo(scanner, copy);
        String isbn = chooseBook(scanner);
        Book book = bookDAOService.getBookByISBN(isbn);
        if(book == null) {
            System.out.println("Couldn't set the book");
            return;
        }
        copy.setBook(book);
        Location location = chooseLocation(scanner);
        if(location == null) {
            System.out.println("Couldn't set the location");
            return;
        }
        copy.setLocation(location);
        int nr = book.getCopies().size();
        copy.setId(nr+1);
        databaseService.addCopy(copy);
        System.out.println("The copy was added to the catalogue");
        book.addCopy(copy);
    }

    private void setGeneralInfo(Scanner scanner, Copy copy) {
        System.out.println("Enter the barcode of the copy:");
        String barcode = scanner.nextLine();
        System.out.println("Enter the index of the copy:");
        String index = scanner.nextLine();
        copy.setBarcode(barcode);
        copy.setIndex(index);
        copy.setAvailable(false);
    }


    private String chooseBook(Scanner scanner) {
        System.out.println("How do you want to search the book? [title/author]");
        String option = scanner.nextLine().toLowerCase();
        System.out.println("Enter:");
        String search = scanner.nextLine();
        switch (option) {
            case "title":
                bookDAOService.getBooksByTitle(search);
            case "author":
                bookDAOService.getBooksByAuthor(search);
            default:
                System.out.println("wrong option");
        }
        System.out.println("Enter the ISBN of the book:");
        String isbn = scanner.nextLine();
        if(bookDAOService.getBookByISBN(isbn) == null) {
            System.out.println("wrong ISBN");
        }
        return isbn;
    }

    private Location chooseLocation(Scanner scanner) {
        System.out.println("Enter the branch library's name:");
        String name = scanner.nextLine().toLowerCase();
        System.out.println("Enter the location in the branch library:");
        String loc = scanner.nextLine().toLowerCase();
        BranchLibrary branchLibrary = branchLibraryDAOService.getBranchLibrary(name);
        if(branchLibrary == null) {
            System.out.println("There is no branch library having this name");
            return null;
        }
        Location location = locationDAOService.getLocationByBranchAndName(branchLibrary, loc);
        if(location == null) {
            System.out.println("There is no location having this name");
            return null;
        }
        return location;
    }

    private Copy findCopy(Scanner scanner) {
        String isbn = chooseBook(scanner);
        Book book = bookDAOService.getBookByISBN(isbn);
        if(book == null) {
            System.out.println("Couldn't find the copy");
            return null;
        }
        if(book.getCopies().isEmpty()){
            System.out.println("The book has no copies");
        }
        System.out.println(book.getCopies());
        System.out.println("Enter the id of the copy");
        int id = scanner.nextInt();
        scanner.nextLine();
        Copy copy = databaseService.getCopyByBookAndId(book, id);
        if(copy == null) {
            System.out.println("wrong id");
            return null;
        }
        return copy;
    }

    public void read(Scanner scanner) {
        Copy copy = findCopy(scanner);
        if(copy == null) {
            System.out.println("Couldn't find the copy");
            return;
        }
        System.out.println(copy);
    }

    public void delete(Scanner scanner) {
        Copy copy = findCopy(scanner);
        if(copy == null) {
            System.out.println("Couldn't find the copy");
        }
        databaseService.removeCopy(copy.getBook(), copy.getId());
        copy.getBook().removeCopy(copy);
    }

    public void update(Scanner scanner) {
        Copy copy = findCopy(scanner);
        if(copy == null) {
            System.out.println("Couldn't find the copy");
        }
        Copy newcopy = new Copy();
        setGeneralInfo(scanner, newcopy);
        copy.setBarcode(newcopy.getBarcode());
        copy.setIndex(newcopy.getIndex());
        Location location = chooseLocation(scanner);
        if(location == null) {
            System.out.println("Couldn't set the location");
            return;
        }
        copy.setLocation(location);
    }
}

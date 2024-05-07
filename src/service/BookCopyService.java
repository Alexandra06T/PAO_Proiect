package service;

import daoservices.*;
import model.*;
import utils.InvalidDataException;

import javax.print.attribute.standard.Copies;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class BookCopyService {

    private BookRepositoryService bookRepositoryService;
    private LocationRepositoryService locationRepositoryService;
    private BranchLibraryRepositoryService branchLibraryRepositoryService;
    private BookCopyRepositoryService databaseService;

    public BookCopyService() throws SQLException {
        this.databaseService = new BookCopyRepositoryService();
        this.bookRepositoryService = new BookRepositoryService();
        this.locationRepositoryService = new LocationRepositoryService();
        this.branchLibraryRepositoryService = new BranchLibraryRepositoryService();
    }

    public void create(Scanner scanner) {
        BookCopy bookCopy = new BookCopy();
        try {
            setGeneralInfo(scanner, bookCopy);
            Book book = chooseBook(scanner);
            bookCopy.setBookISBN(book.getISBN());
            Location location = chooseLocation(scanner);
            bookCopy.setLocationID(location.getLocationID());
            databaseService.addBookCopy(bookCopy);
            System.out.println("The book copy was added to the catalogue");
        } catch (InvalidDataException e) {
            System.out.println("Creation failed: " + e.getMessage());
        }
    }

    private void setGeneralInfo(Scanner scanner, BookCopy bookCopy) throws InvalidDataException {
        System.out.println("Enter the barcode of the bookCopy:");
        String barcode = scanner.nextLine();
        if(barcode.isEmpty()) throw new InvalidDataException("Barcode missing");
        System.out.println("Enter the index of the bookCopy:");
        String index = scanner.nextLine();
        if(index.isEmpty()) throw new InvalidDataException("Index missing");
        bookCopy.setBarcode(barcode);
        bookCopy.setIndex(index);
        System.out.println("Is the book copy available? [y/n]");
        String option = scanner.nextLine();
        switch (option) {
            case "y":
                bookCopy.setAvailable(true);
                break;
            case "n":
                bookCopy.setAvailable(false);
                break;
            default:
                throw new InvalidDataException("Wrong option");
        }

    }

    public void view() {
        try {
            System.out.println("BOOK COPIES:");
            databaseService.printAll();
            System.out.println();
        } catch (InvalidDataException e) {
            System.out.println(e.getMessage());
        }
    }


    private Book chooseBook(Scanner scanner) throws InvalidDataException {
        System.out.println("How do you want to search the book? [title/author]");
        String option = scanner.nextLine().toLowerCase();
        System.out.println("Enter:");
        String search = scanner.nextLine();
        List<Book> searchedBooks;

        switch (option) {
            case "title":
                searchedBooks = bookRepositoryService.getBooksByTitle(search);
                //daca avem o singura carte, o returnam
                if(searchedBooks.size() == 1) return searchedBooks.getFirst();
                //daca avem mai multe carti, cerem isbn-ul
                break;
            case "author":
                searchedBooks = bookRepositoryService.getBooksByAuthor(search);
                if(searchedBooks.size() == 1) return searchedBooks.getFirst();
                break;
            default:
                throw new InvalidDataException("Wrong option");
        }
        System.out.println("Results for '" + search + "':");
        for(Book b : searchedBooks) {
            System.out.println(b);
            System.out.println("-------------------------------");
        }
        System.out.println("Enter the ISBN of the book:");
        String isbn = scanner.nextLine();
        Book book = bookRepositoryService.getBookByISBN(isbn);
        return book;

    }

    private Location chooseLocation(Scanner scanner) throws InvalidDataException {
        System.out.println("Enter the branch library's name:");
        String name = scanner.nextLine();
        BranchLibrary branchLibrary = branchLibraryRepositoryService.getBranchLibrary(name);
        if(branchLibrary == null) {
            throw new InvalidDataException("No branch library having this name");
        }
        System.out.println("Enter the location in the branch library:");
        String loc = scanner.nextLine();
        Location location = locationRepositoryService.getLocationByBranchAndName(loc, branchLibrary.getBranchLibraryID());
        if(location == null) {
            throw new InvalidDataException("No location in the branch library having this name");
        }
        return location;
    }

    private BookCopy findCopy(Scanner scanner) throws InvalidDataException {
        Book book = chooseBook(scanner);
        List<BookCopy> bookCopyList = databaseService.getBookCopiesByBook(book.getISBN());
        if(bookCopyList.size() == 1) return bookCopyList.getFirst();
        for(BookCopy b : bookCopyList) {
            System.out.println(b);
            System.out.println("-------------------------");
        }
        System.out.println("Enter the id of the book copy");
        int id = scanner.nextInt();
        scanner.nextLine();
        BookCopy bookCopy = databaseService.getBookCopyById(id);
        return bookCopy;
    }

    public void read(Scanner scanner) {
        try {
            BookCopy bookCopy = findCopy(scanner);
            Book book = bookRepositoryService.getBookByISBN(bookCopy.getBookISBN());
            System.out.println(book.getTitle() + " by " + book.getAuthors().stream().collect(Collectors.joining("; ")));
            System.out.println(bookCopy);
            Location location = locationRepositoryService.getLocationByID(bookCopy.getLocationID());
            BranchLibrary branchLibrary = branchLibraryRepositoryService.getBranchLibraryByID(location.getBranchLibraryID());
            System.out.println(location.getName() + ", " + branchLibrary.getName());
            System.out.println();
            System.out.println();
        } catch (InvalidDataException e) {
            System.out.println(e.getMessage());
        }
    }

    public void delete(Scanner scanner) {
        try {
            BookCopy bookCopy = findCopy(scanner);
            databaseService.removeBookCopy(bookCopy);
            System.out.println("Book copy removed successfully!");
        } catch (InvalidDataException e) {
            System.out.println("Removal failed: " + e.getMessage());
        }
    }

    public void update(Scanner scanner) {
        try {
            BookCopy bookCopy = findCopy(scanner);
            BookCopy newcopy = new BookCopy();
            setGeneralInfo(scanner, newcopy);
            bookCopy.setBarcode(newcopy.getBarcode());
            bookCopy.setIndex(newcopy.getIndex());
            bookCopy.setAvailable(newcopy.isAvailable());
            Location location = chooseLocation(scanner);
            bookCopy.setLocationID(location.getLocationID());

            databaseService.updateBookCopy(bookCopy);
            System.out.println("Book copy updated successfully!");

        } catch (InvalidDataException e) {
            System.out.println("Update failed: " + e.getMessage());
        }
    }
}

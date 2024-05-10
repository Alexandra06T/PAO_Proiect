import dao.*;
import daoservices.*;
import model.*;
import service.*;
import utils.InvalidDataException;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class Application {

    public static void main(String[] args) throws SQLException, InvalidDataException {
        Scanner scanner = new Scanner(System.in);

        BookService bookService = new BookService();
        BranchLibraryService branchLibraryService = new BranchLibraryService();
        CategoryService categoryService = new CategoryService();
        BookCopyService bookCopyService = new BookCopyService();
        LibraryMemberService libraryMemberService = new LibraryMemberService();
        LocationService locationService = new LocationService();
        ReservationService reservationService = new ReservationService();
        TransactionService transactionService = new TransactionService();

        //ObjectsExamples();

        while (true){

            menuTarget();
            String target = scanner.nextLine().toLowerCase();
            switch (target) {
                case "category":
                    LibraryService.manageCategories(scanner, categoryService);
                    break;
                case "book":
                    LibraryService.manageBooks(scanner, bookService);
                    break;
                case "book copy":
                    LibraryService.manageBookCopies(scanner, bookCopyService);
                    break;
                case "library member":
                    LibraryService.manageLibraryMembers(scanner, libraryMemberService);
                    break;
                case "branch library":
                    LibraryService.manageBranchLibraries(scanner, branchLibraryService);
                    break;
                case "location":
                    LibraryService.manageLocations(scanner, locationService);
                    break;
                case "reservation":
                    LibraryService.manageReservations(scanner, reservationService);
                    break;
                case "transaction":
                    LibraryService.manageTransactions(scanner, transactionService);
                    break;
                case "quit":
                    System.out.println("Exiting");
                    return;
                default:
                    System.out.println("The chosen target couldn't be recognized.");
            }
        }
    }

    private static void menuTarget() {
        System.out.println("Choose the target of your operation:");
        System.out.println("Category");
        System.out.println("Book");
        System.out.println("Book Copy");
        System.out.println("Library Member");
        System.out.println("Location");
        System.out.println("Branch Library");
        System.out.println("Reservation");
        System.out.println("Transaction");
        System.out.println("quit");
        System.out.println("Enter target:");
    }


}

//TODO: create audit system
//TODO: throw and handle exceptions
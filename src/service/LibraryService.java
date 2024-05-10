package service;

import java.util.Scanner;

public class LibraryService {

    private static void menuCRUD() {
        System.out.println("Choose the operation you want to perform:");
        System.out.println("create");
        System.out.println("list");
        System.out.println("read");
        System.out.println("update");
        System.out.println("delete");
        System.out.println("quit");
        System.out.println("Enter command:");
    }

    public static void manageCategories(Scanner scanner, CategoryService categoryService) {
        menuCRUD();

        String command = scanner.nextLine().toLowerCase();
        switch (command){
            case "create":
                categoryService.create(scanner);
                break;
            case "list":
                categoryService.view();
                break;
            case "read":
                categoryService.read(scanner);
                break;
            case "delete":
                categoryService.delete(scanner);
                break;
            case "update":
                categoryService.update(scanner);
                break;
            case "quit":
                System.out.println("Exiting");
                return;
            default:
                System.out.println("The chosen command couldn't be recognized");
        }
    }

    public static void manageBooks(Scanner scanner, BookService bookService) {
        menuCRUD();

        String command = scanner.nextLine().toLowerCase();
        switch (command){
            case "create":
                bookService.create(scanner);
                break;
            case "list":
                bookService.view();
                break;
            case "read":
                bookService.read(scanner);
                break;
            case "delete":
                bookService.delete(scanner);
                break;
            case "update":
                bookService.update(scanner);
                break;
            case "quit":
                System.out.println("Exiting");
                return;
            default:
                System.out.println("The chosen command couldn't be recognized");
        }
    }

    public static void manageBookCopies(Scanner scanner, BookCopyService bookCopyService) {
        menuCRUD();

        String command = scanner.nextLine().toLowerCase();
        switch (command){
            case "create":
                bookCopyService.create(scanner);
                break;
            case "list":
                bookCopyService.view();
                break;
            case "read":
                bookCopyService.read(scanner);
                break;
            case "delete":
                bookCopyService.delete(scanner);
                break;
            case "update":
                bookCopyService.update(scanner);
                break;
            case "quit":
                System.out.println("Exiting");
                return;
            default:
                System.out.println("The chosen command couldn't be recognized");
        }
    }

    public static void manageLibraryMembers(Scanner scanner, LibraryMemberService libraryMemberService) {
        menuCRUD();

        String command = scanner.nextLine().toLowerCase();
        switch (command){
            case "create":
                libraryMemberService.create(scanner);
                break;
            case "list":
                libraryMemberService.view();
                break;
            case "read":
                libraryMemberService.read(scanner);
                break;
            case "delete":
                libraryMemberService.delete(scanner);
                break;
            case "update":
                libraryMemberService.update(scanner);
                break;
            case "quit":
                System.out.println("Exiting");
                return;
            default:
                System.out.println("The chosen command couldn't be recognized");
        }
    }

    public static void manageBranchLibraries(Scanner scanner, BranchLibraryService branchLibraryService) {
        menuCRUD();

        String command = scanner.nextLine().toLowerCase();
        switch (command){
            case "create":
                branchLibraryService.create(scanner);
                break;
            case "list":
                branchLibraryService.view();
                break;
            case "read":
                branchLibraryService.read(scanner);
                break;
            case "delete":
                branchLibraryService.delete(scanner);
                break;
            case "update":
                branchLibraryService.update(scanner);
                break;
            case "quit":
                System.out.println("Exiting");
                return;
            default:
                System.out.println("The chosen command couldn't be recognized");
        }
    }

    public static void manageLocations(Scanner scanner, LocationService locationService) {
        menuCRUD();

        String command = scanner.nextLine().toLowerCase();
        switch (command){
            case "create":
                locationService.create(scanner);
                break;
            case "list":
                locationService.view();
                break;
            case "read":
                locationService.read(scanner);
                break;
            case "delete":
                locationService.delete(scanner);
                break;
            case "update":
                locationService.update(scanner);
                break;
            case "quit":
                System.out.println("Exiting");
                return;
            default:
                System.out.println("The chosen command couldn't be recognized");
        }
    }

    public static void manageReservations(Scanner scanner, ReservationService reservationService) {
        menuCRUD();

        String command = scanner.nextLine().toLowerCase();
        switch (command){
            case "create":
                reservationService.create(scanner);
                break;
            case "list":
                reservationService.view();
                break;
            case "read":
                reservationService.read(scanner);
                break;
            case "delete":
                reservationService.delete(scanner);
                break;
            case "update":
                reservationService.update(scanner);
                break;
            case "quit":
                System.out.println("Exiting");
                return;
            default:
                System.out.println("The chosen command couldn't be recognized");
        }
    }

    public static void manageTransactions(Scanner scanner, TransactionService transactionService) {
        menuCRUD();

        String command = scanner.nextLine().toLowerCase();
        switch (command){
            case "create":
                transactionService.create(scanner);
                break;
            case "read":
                transactionService.read(scanner);
                break;
            case "delete":
                transactionService.delete(scanner);
                break;
            case "update":
                transactionService.update(scanner);
                break;
            case "quit":
                System.out.println("Exiting");
                return;
            default:
                System.out.println("The chosen command couldn't be recognized");
        }
    }
}

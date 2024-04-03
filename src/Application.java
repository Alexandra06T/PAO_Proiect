import service.*;

import java.util.Scanner;

public class Application {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        BookService bookService = new BookService();
        BranchLibraryService branchLibraryService = new BranchLibraryService();
        CategoryService categoryService = new CategoryService();
        BookCopyService bookCopyService = new BookCopyService();
        LibraryMemberService libraryMemberService = new LibraryMemberService();
        LocationService locationService = new LocationService();
        ReservationService reservationService = new ReservationService();
        TransactionService transactionService = new TransactionService();

        while (true){

            menuTarget();
            String target = scanner.nextLine().toLowerCase();
            switch (target) {
                case "category":
                    menuCRUD();

                    String command = scanner.nextLine().toLowerCase();
                    switch (command){
                        case "create":
                            categoryService.create(scanner);
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
                    break;
                case "book":
                    menuCRUD();

                    command = scanner.nextLine().toLowerCase();
                    switch (command){
                        case "create":
                            bookService.create(scanner);
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
                    break;
                case "copy":
                    menuCRUD();

                    command = scanner.nextLine().toLowerCase();
                    switch (command){
                        case "create":
                            bookCopyService.create(scanner);
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
                    break;
                case "library member":
                    menuCRUD();

                    command = scanner.nextLine().toLowerCase();
                    switch (command){
                        case "create":
                            libraryMemberService.create(scanner);
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
                    break;
                case "branch library":
                    menuCRUD();

                    command = scanner.nextLine().toLowerCase();
                    switch (command){
                        case "create":
                            branchLibraryService.create(scanner);
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
                    break;
                case "location":
                    menuCRUD();

                    command = scanner.nextLine().toLowerCase();
                    switch (command){
                        case "create":
                            locationService.create(scanner);
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
                    break;
                case "reservation":

                    menuCRUD();

                    command = scanner.nextLine().toLowerCase();
                    switch (command){
                        case "create":
                            reservationService.create(scanner);
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
                    break;
                case "transaction":
                    menuCRUD();

                    command = scanner.nextLine().toLowerCase();
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
                    break;
                case "quit":
                    System.out.println("Exiting");
                    return;
                default:
                    System.out.println("The chosen target couldn't be recognized.");
            }
        }
    }

    private static void menuCRUD() {
        System.out.println("Choose the operation you want to perform:");
        System.out.println("create");
        System.out.println("read");
        System.out.println("update");
        System.out.println("delete");
        System.out.println("quit");
        System.out.println("Enter command:");
    }

    private static void menuTarget() {
        System.out.println("Choose the target of your operation:");
        System.out.println("Category");
        System.out.println("Book");
        System.out.println("BookCopy");
        System.out.println("Library Member");
        System.out.println("Location");
        System.out.println("Branch Library");
        System.out.println("Reservation");
        System.out.println("Transaction");
        System.out.println("quit");
        System.out.println("Enter target:");
    }


}

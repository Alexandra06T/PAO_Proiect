import dao.*;
import model.*;
import service.*;

import java.util.ArrayList;
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

        //mini baza de date pentru testare
        BranchLibraryDao branchLibraryDao = new BranchLibraryDao();
        BranchLibrary MatematicaInformatica = new BranchLibrary("Facultatea de Matematica si Informatica", "Strada Academiei, nr 14");
        BranchLibrary Litere = new BranchLibrary("Facultatea de Litere", "Str. Edgar Quinet, nr 5-7");
        branchLibraryDao.create(MatematicaInformatica);
        branchLibraryDao.create(Litere);

        LocationDao locationDao = new LocationDao();
        Location Depozit1 = new Location("Depozit I", MatematicaInformatica);
        Location SalaM = new Location("Sala de lectura", MatematicaInformatica);
        Location SalaL = new Location("Sala de lectura", Litere);
        locationDao.create(Depozit1);
        locationDao.create(SalaL);
        locationDao.create(SalaM);

        LibraryMemberDao libraryMemberDao = new LibraryMemberDao();
        LibraryMember AnaP = new LibraryMember("Ana Popescu", "anap@gmail.com", "0723456789", "Str. Mare, nr. 10");
        LibraryMember GeorgeV = new LibraryMember("George Vasile", "georgev@yahoo.com", "0764791052", "Str. Florilor, nr. 23");
        LibraryMember AndreeaI = new LibraryMember("Andreea Ionescu", "andreea.ionescu@gmail.com", "0749284053", "Str. Zorilor, nr. 15");
        libraryMemberDao.create(AnaP);
        libraryMemberDao.create(GeorgeV);
        libraryMemberDao.create(AndreeaI);

        CategoryDao categoryDao = new CategoryDao();
        Category LiteraturaUniversala = new Category("Literatura Universala", 636);
        Category LiteraturaRomana = new Category("Literatura Romana", 600);
        Category AlgebraL = new Category("Algebra Liniara", 278);
        categoryDao.create(LiteraturaRomana);
        categoryDao.create(LiteraturaUniversala);
        categoryDao.create(AlgebraL);

        BookDao bookDao = new BookDao();
        Book VanatorulZmeie = new Book("Vanatorul de zmeie", new ArrayList<>(), "973-748-092-7", "Niculescu", 2007, 404);
        VanatorulZmeie.getAuthors().add("Hosseini, Khaled");
        VanatorulZmeie.setCategory(LiteraturaUniversala);
        Book Inocentii = new Book("Inocentii", new ArrayList<>(), "978-973-505-469-4", "Humanitas", 2016, 340);
        Inocentii.getAuthors().add("Parvulescu, Ioana");
        Inocentii.setCategory(LiteraturaRomana);
        Book Algebra = new Book("Algebra Liniara", new ArrayList<>(), "973-879-567-1", "Editura de Stat Didactica si Pedagogica", 1962, 308);
        Algebra.getAuthors().add("Creanga, Ion");
        Algebra.getAuthors().add("Haimovici, Corina");
        Algebra.setCategory(AlgebraL);
        Book FratiiKaramazov = new Book("Fratii Karamazov", new ArrayList<>(), "973-950-559-7", "Editura Victoria", 1993, 1300);
        FratiiKaramazov.getAuthors().add("Dostoevskij, Feodor Mihajlovich");
        FratiiKaramazov.setCategory(LiteraturaUniversala);
        bookDao.create(VanatorulZmeie);
        bookDao.create(Inocentii);
        bookDao.create(Algebra);
        bookDao.create(FratiiKaramazov);



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
                case "book copy":
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

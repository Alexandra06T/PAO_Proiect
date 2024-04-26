import dao.*;
import daoservices.*;
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

        ObjectsExamples();

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

    private static void ObjectsExamples() {
        //mini baza de date pentru testare
        BranchLibraryRepositoryService branchLibraryRepositoryService = new BranchLibraryRepositoryService();
        BranchLibrary MatematicaInformatica = new BranchLibrary("Facultatea de Matematica si Informatica", "Strada Academiei, nr 14");
        BranchLibrary Litere = new BranchLibrary("Facultatea de Litere", "Str. Edgar Quinet, nr 5-7");
        branchLibraryRepositoryService.addBranchLibrary(MatematicaInformatica);
        branchLibraryRepositoryService.addBranchLibrary(Litere);

        LocationRepositoryService locationRepositoryService = new LocationRepositoryService();
        Location Depozit1 = new Location("Depozit I", MatematicaInformatica);
        Location SalaM = new Location("Sala de lectura", MatematicaInformatica);
        Location SalaL = new Location("Sala de lectura", Litere);
        locationRepositoryService.addLocation(Depozit1);
        locationRepositoryService.addLocation(SalaL);
        locationRepositoryService.addLocation(SalaM);

        LibraryMemberRepositoryService libraryMemberRepositoryService = new LibraryMemberRepositoryService();
        LibraryMember AnaP = new LibraryMember("Ana Popescu", "anap@gmail.com", "0723456789", "Str. Mare, nr. 10");
        LibraryMember GeorgeV = new LibraryMember("George Vasile", "georgev@yahoo.com", "0764791052", "Str. Florilor, nr. 23");
        LibraryMember AndreeaI = new LibraryMember("Andreea Ionescu", "andreea.ionescu@gmail.com", "0749284053", "Str. Zorilor, nr. 15");
        libraryMemberRepositoryService.addLibraryMember(AnaP);
        libraryMemberRepositoryService.addLibraryMember(GeorgeV);
        libraryMemberRepositoryService.addLibraryMember(AndreeaI);

        CategoryRepositoryService categoryRepositoryService = new CategoryRepositoryService();
        Category LiteraturaUniversala = new Category("Literatura Universala", 636);
        Category LiteraturaRomana = new Category("Literatura Romana", 600);
        Category AlgebraL = new Category("Algebra Liniara", 278);
        categoryRepositoryService.addCategory(LiteraturaRomana);
        categoryRepositoryService.addCategory(LiteraturaUniversala);
        categoryRepositoryService.addCategory(AlgebraL);

        BookRepositoryService bookRepositoryService = new BookRepositoryService();
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
        bookRepositoryService.addBook(VanatorulZmeie);
        bookRepositoryService.addBook(Inocentii);
        bookRepositoryService.addBook(Algebra);
        bookRepositoryService.addBook(FratiiKaramazov);

        BookCopyRepositoryService bookCopyRepositoryService = new BookCopyRepositoryService();
        BookCopy Inocentii1 = new BookCopy(1, Inocentii, "9787485054694", "A.45889", SalaL, true);
        BookCopy Inocentii2 = new BookCopy(2, Inocentii, "9787485054694", "A.45889", SalaL, true);
        BookCopy VanatorulZmeie1 = new BookCopy(3, VanatorulZmeie, "9737480927", "A.87236", SalaL, false);
        BookCopy FratiiKaramazov1 = new BookCopy(4, FratiiKaramazov, "9739505597", "A.64589", SalaL, true);
        BookCopy Algebra1 = new BookCopy(5, Algebra, "9738795671", "II35498", Depozit1, true);
        bookCopyRepositoryService.addCopy(Inocentii1);
        bookCopyRepositoryService.addCopy(Inocentii2);
        bookCopyRepositoryService.addCopy(VanatorulZmeie1);
        bookCopyRepositoryService.addCopy(FratiiKaramazov1);
        bookCopyRepositoryService.addCopy(Algebra1);

        ReservationRepositoryService reservationRepositoryService = new ReservationRepositoryService();
        Reservation reservation1 = new Reservation(AnaP, VanatorulZmeie, java.time.LocalDate.now().plusDays(7), SalaL);
        Reservation reservation2 = new Reservation(AndreeaI, VanatorulZmeie, java.time.LocalDate.now().plusDays(14), SalaL);
        reservationRepositoryService.addReservation(reservation1);
        reservationRepositoryService.addReservation(reservation2);

        TransactionRepositoryService transactionRepositoryService= new TransactionRepositoryService();
        CheckIn checkIn1 = new CheckIn(new Transaction(AnaP, Inocentii1, java.time.LocalDate.now()));
        checkIn1.setNumberDays(14);
        checkIn1.setType("imprumut normal");
        checkIn1.setCheckedOut(false);
        CheckIn checkIn2 = new CheckIn(new Transaction(AnaP, Algebra1, java.time.LocalDate.now()));
        checkIn2.setNumberDays(1);
        checkIn2.setType("imprumut in sala de lectura");
        checkIn2.setCheckedOut(false);
        //imprumut intarziat la predare
        CheckIn checkIn3 = new CheckIn(new Transaction(GeorgeV, FratiiKaramazov1, java.time.LocalDate.now().minusDays(16)));
        checkIn3.setNumberDays(14);
        checkIn3.setType("imprumut normal");
        checkIn3.setCheckedOut(false);
        transactionRepositoryService.addTransaction(checkIn1);
        transactionRepositoryService.addTransaction(checkIn2);
        transactionRepositoryService.addTransaction(checkIn3);
    }


}

//TODO: create audit system
//TODO: throw and handle exceptions
package service;

import daoservices.BookRepositoryService;
import daoservices.CategoryRepositoryService;
import model.Book;
import model.Category;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BookService {

    private BookRepositoryService databaseService;
    private CategoryRepositoryService categoryRepositoryService;

    public BookService() throws SQLException {
        this.databaseService = new BookRepositoryService();
        this.categoryRepositoryService = new CategoryRepositoryService();
    }

    public void create(Scanner scanner) {
        Book book = new Book();
        setGeneralInfo(scanner, book);
        setCategory(scanner, book);
        if(book.getCategory() == null) {
            System.out.println("Couldn't set the category.\nThe book was discarded.");
            return;
        }
        databaseService.addBook(book);
    }

    private void setGeneralInfo(Scanner scanner, Book book) {
        System.out.println("Enter the title of the book:");
        String title = scanner.nextLine();
        System.out.println("Enter the number of the authors:");
        int nr = scanner.nextInt();
        scanner.nextLine();
        List<String> authors = new ArrayList<>();
        for(int i = 0; i < nr; i++) {
            System.out.println("Enter author's name:");
            String name = scanner.nextLine();
            authors.add(name);
        }
        System.out.println("Enter the ISBN:");
        String isbn = scanner.nextLine();
        System.out.println("Enter the name of the publishing house:");
        String publishingHouse = scanner.nextLine();
        System.out.println("Enter the publishing year:");
        int year = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Enter the number of pages:");
        int nrPages = scanner.nextInt();
        scanner.nextLine();
        book.setTitle(title);
        book.setAuthors(authors);
        book.setISBN(isbn);
        book.setPublishingHouse(publishingHouse);
        book.setPublishedDate(year);
        book.setNumberOfPages(nrPages);
    }

    private void setCategory(Scanner scanner, Book book) {
        System.out.println("Available categories:");
        List<Category> categories = categoryRepositoryService.getAll();
        if(categories == null) return;
        System.out.println("Enter the index of the chosen category:");
        int index = scanner.nextInt();
        scanner.nextLine();
        Category category = categoryRepositoryService.getCategoryByIndex(index);
        book.setCategory(category);
    }

    private Book chooseBook(Scanner scanner) {
        System.out.println("How do you want to search the book? [title/author]");
        String option = scanner.nextLine().toLowerCase();
        System.out.println("Enter:");
        String search = scanner.nextLine();
        switch (option) {
            case "title":
                System.out.println("Books having the title '" + search + "':");
                List<Book> titleSearchedBooks = databaseService.getBooksByTitle(search);
                if(titleSearchedBooks == null) return null;
                //daca avem o singura carte, o returnam
                if(titleSearchedBooks.size() == 1) return titleSearchedBooks.getFirst();
                //daca avem mai multe carti, cerem isbn-ul
                break;
            case "author":
                System.out.println("Books of " + search + ":");
                List<Book> authorSearchedBooks = databaseService.getBooksByAuthor(search);
                if(authorSearchedBooks == null) return null;
                if(authorSearchedBooks.size() == 1) return authorSearchedBooks.getFirst();
                break;
            default:
                System.out.println("wrong option");
                return null;
        }
        System.out.println("Enter the ISBN of the book:");
        String isbn = scanner.nextLine();
        Book book = databaseService.getBookByISBN(isbn);
        if(book == null) {
            System.out.println("wrong ISBN");
        }
        return book;
    }

    public void read(Scanner scanner) {
        Book book = chooseBook(scanner);
        if(book == null) {
            System.out.println("Couldn't find the book");
        }
        else {
            System.out.println("The book you are looking for:");
            System.out.println(book);
            System.out.println();
        }
    }

    public void delete(Scanner scanner) {
        Book book = chooseBook(scanner);
        if(book.getCategory() != null)  {
            book.getCategory().removeBook(book);
        }
        databaseService.removeBook(book.getISBN());
    }

    public void update(Scanner scanner) {
        Book book = chooseBook(scanner);
        if (book == null) { return;}
        Book newBook = new Book();
        System.out.println("Enter the updated information: ");
        setGeneralInfo(scanner, newBook);
        setCategory(scanner, newBook);
        book.setTitle(newBook.getTitle());
        book.setAuthors(newBook.getAuthors());
        book.setPublishedDate(newBook.getPublishedDate());
        book.setNumberOfPages(newBook.getNumberOfPages());
        book.setPublishingHouse(newBook.getPublishingHouse());
        book.setCategory(newBook.getCategory());
    }


}

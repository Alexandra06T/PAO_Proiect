package service;

import daoservices.BookRepositoryService;
import daoservices.CategoryRepositoryService;
import model.Book;
import model.Category;
import utils.InvalidDataException;

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
        setISBN(scanner, book);
        setGeneralInfo(scanner, book);
        try {
            setCategory(scanner, book);
            databaseService.addBook(book);
        }
        catch (InvalidDataException e) {
            System.out.println("Creation failed: " + e.getMessage());
        }
    }

    private void setISBN(Scanner scanner, Book book) {
        System.out.println("Enter the ISBN:");
        String isbn = scanner.nextLine();
        book.setISBN(isbn);
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
        book.setPublishingHouse(publishingHouse);
        book.setPublishedDate(year);
        book.setNumberOfPages(nrPages);
    }

    private void setCategory(Scanner scanner, Book book) throws InvalidDataException {
        System.out.println("Available categories:");
        if(categoryRepositoryService.getAll() == null) return;
        categoryRepositoryService.printAll();
        System.out.println("Enter the index of the chosen category:");
        int index = scanner.nextInt();
        scanner.nextLine();
        categoryRepositoryService.getCategoryByIndex(index);
        book.setCategoryID(index);

    }

    private Book chooseBook(Scanner scanner) throws InvalidDataException {
        System.out.println("How do you want to search the book? [title/author]");
        String option = scanner.nextLine().toLowerCase();
        System.out.println("Enter:");
        String search = scanner.nextLine();
        List<Book> searchedBooks;

        switch (option) {
            case "title":
                searchedBooks = databaseService.getBooksByTitle(search);
                //daca avem o singura carte, o returnam
                if(searchedBooks.size() == 1) return searchedBooks.getFirst();
                //daca avem mai multe carti, cerem isbn-ul
                break;
            case "author":
                searchedBooks = databaseService.getBooksByAuthor(search);
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
        Book book = databaseService.getBookByISBN(isbn);
        return book;

    }

    public void view() {
        try {
            System.out.println("BOOKS:");
            databaseService.printAll();
            System.out.println();
        } catch (InvalidDataException e) {
            System.out.println(e.getMessage());
        }
    }

    public void read(Scanner scanner) {
        try {
            Book book = chooseBook(scanner);
            System.out.println("The book you are looking for:");
            System.out.println(book);
            System.out.println();
        } catch (InvalidDataException e) {
            System.out.println(e.getMessage());
        }
    }

    public void delete(Scanner scanner) {
        try {
            Book book = chooseBook(scanner);
            databaseService.removeBook(book);
            System.out.println("Book removed successfully!");
        } catch (InvalidDataException e) {
            System.out.println(e.getMessage());
        }
    }

    public void update(Scanner scanner) {
        try {
            Book book = chooseBook(scanner);
            Book newBook = new Book();
            System.out.println("Enter the updated information: ");
            newBook.setISBN(book.getISBN());
            setGeneralInfo(scanner, newBook);
            setCategory(scanner, newBook);
            databaseService.updateBook(newBook);
            System.out.println("Book updated successfully!");
        } catch (InvalidDataException e) {
            System.out.println("Update failed: " + e.getMessage());
        }

    }

}

package service;

import dao.BookDAOService;
import dao.CategoryDAOService;
import model.Book;
import model.Category;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BookService {

    private BookDAOService databaseService;
    private CategoryDAOService categoryDAOService;

    public BookService(){
        this.databaseService = new BookDAOService();
        this.categoryDAOService = new CategoryDAOService();
    }

    public void create(Scanner scanner) {
        Book book = new Book();
        setGeneralInfo(scanner, book);
        setCategory(scanner, book);
        databaseService.addBook(book);
        System.out.println("The book was added to the catalogue");
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
    }

    private void setCategory(Scanner scanner, Book book) {
        System.out.println(categoryDAOService.getAll());
        System.out.println("Enter the index of the chosen category:");
        int index = scanner.nextInt();
        scanner.nextLine();
        Category category = categoryDAOService.getCategoryByIndex(index);
        book.setCategory(category);
        if(book.getCategory() != null)  {
            book.getCategory().removeBook(book);
        }
        category.addBook(book);
    }

    private String choose(Scanner scanner) {
        System.out.println("How do you want to search the book? [title/author]");
        String option = scanner.nextLine().toLowerCase();
        System.out.println("Enter:");
        String search = scanner.nextLine();
        switch (option) {
            case "title":
                databaseService.getBooksByTitle(search);
            case "author":
                databaseService.getBooksByAuthor(search);
            default:
                System.out.println("wrong option");
        }
        System.out.println("Enter the ISBN of the book:");
        String isbn = scanner.nextLine();
        if(databaseService.getBookByISBN(isbn) == null) {
            System.out.println("wrong ISBN");
        }
        return isbn;
    }

    public void read(Scanner scanner) {
        String isbn = choose(scanner);
        Book book = databaseService.getBookByISBN(isbn);
    }

    public void delete(Scanner scanner) {
        String isbn = choose(scanner);
        databaseService.removeBook(isbn);
    }

    public void update(Scanner scanner) {
        String isbn = choose(scanner);
        Book book = databaseService.getBookByISBN(isbn);
        if (book == null) { return;}
        Book newBook = new Book();
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

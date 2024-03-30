package service;

import dao.BookDAOService;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BookService {

    private BookDAOService databaseService;

    public BookService(){
        this.databaseService = new BookDAOService();
    }

    public void create(Scanner scanner) {
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
        //category
        //add

    }
}

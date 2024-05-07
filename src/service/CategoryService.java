package service;

import daoservices.CategoryRepositoryService;
import model.Category;
import utils.InvalidDataException;

import java.sql.SQLException;
import java.util.Scanner;

public class CategoryService {
    private CategoryRepositoryService databaseService;

    public CategoryService() throws SQLException {
        this.databaseService = new CategoryRepositoryService();
    }

    public void create(Scanner scanner) {
        System.out.println("Enter the name of the category:");
        String name = scanner.nextLine();
        System.out.println("Enter the index of the category:");
        int index = scanner.nextInt();
        scanner.nextLine();
        Category category = new Category(name, index);

        try {
            databaseService.addCategory(category);
        }
        catch (InvalidDataException e) {
            System.out.println("Creation failed: " + e.getMessage());
        }
    }

    private Category searchCategory(Scanner scanner) throws InvalidDataException {
        System.out.println("How do you want to search the category? [name/index]");
        String option = scanner.nextLine().toLowerCase();
        System.out.println("Enter:");
        switch (option) {
            case "name":
                String name = scanner.nextLine();
                return databaseService.getCategoryByName(name);
            case "index":
                int index = scanner.nextInt();
                scanner.nextLine();
                return databaseService.getCategoryByIndex(index);
            default:
                System.out.println("wrong option");
                return null;
        }
    }

    public void view() {
        try {
            System.out.println("CATEGORIES:");
            databaseService.printAll();
            System.out.println();
        } catch (InvalidDataException e) {
            System.out.println(e.getMessage());
        }
    }

    public void read(Scanner scanner) {
        try {
            Category category = searchCategory(scanner);
            System.out.println(category);
        } catch (InvalidDataException e) {
            System.out.println(e.getMessage());
        }
    }

    public void delete(Scanner scanner) {
        try {
            Category category = searchCategory(scanner);
            databaseService.removeCategory(category);
            System.out.println("Category removed successfully!");
        } catch (InvalidDataException e) {
            System.out.println("Removal failed: " + e.getMessage());
        }
    }

    public void update(Scanner scanner) {
        try {
            Category category = searchCategory(scanner);
            System.out.println("Enter the new name of the category:");
            String name = scanner.nextLine();
            category.setName(name);
            databaseService.updateCategory(category);
        }
        catch (InvalidDataException e) {
            System.out.println("Update failed: " + e.getMessage());
        }

    }

}

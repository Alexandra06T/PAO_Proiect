package service;

import daoservices.CategoryRepositoryService;
import model.Category;

import java.util.Scanner;

public class CategoryService {
    private CategoryRepositoryService databaseService;

    public CategoryService(){
        this.databaseService = new CategoryRepositoryService();
    }

    public void create(Scanner scanner) {
        System.out.println("Enter the name of the category:");
        String name = scanner.nextLine();
        System.out.println("Enter the index of the category:");
        int index = scanner.nextInt();
        scanner.nextLine();
        Category category = new Category(name, index);
        databaseService.addCategory(category);
        System.out.println("Category added to the catalogue");
    }

    private Category searchCategory(Scanner scanner) {
        Category category = new Category();
        System.out.println("How do you want to search the category? [name/index]");
        String option = scanner.nextLine().toLowerCase();
        System.out.println("Enter:");
        switch (option) {
            case "name":
                String name = scanner.nextLine();
                category = databaseService.getCategoryByName(name);
            case "index":
                int index = scanner.nextInt();
                scanner.nextLine();
                category = databaseService.getCategoryByIndex(index);
            default:
                System.out.println("wrong option");
        }
        return category;
    }

    public void read(Scanner scanner) {
        searchCategory(scanner);
    }

    public void delete(Scanner scanner) {
        Category category = searchCategory(scanner);
        if(category == null) {
            System.out.println("Couldn't find the category");
            return;
        }
        databaseService.removeCategory(category.getName());
    }

    public void update(Scanner scanner) {
        Category category = searchCategory(scanner);

        System.out.println("Enter the name of the category:");
        String name = scanner.nextLine();
        System.out.println("Enter the index of the category:");
        int index = scanner.nextInt();
        scanner.nextLine();
        category.setName(name);
        category.setIndex(index);
    }

}

package dao;

import model.Category;
import repository.CategoryRepository;

public class CategoryDAOService {

    private CategoryRepository categoryRepository;

    public CategoryDAOService() {
        this.categoryRepository = new CategoryRepository();
    }

    public Category getCategoryByName(String name){
        Category category = categoryRepository.read(name);
        if(category != null){
            System.out.println(category);
        }else {
            System.out.println("No category having this name");
        }

        return category;
    }

    public void removeCategory(String name) {
        Category category = getCategoryByName(name);
        if (category == null) return;

        categoryRepository.delete(category);

        System.out.println("Removed " + category);
    }

    public void addCategory(Category category) {
        if(category != null){
            categoryRepository.create(category);
        }
    }
}

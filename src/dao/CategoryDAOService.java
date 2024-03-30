package dao;

import model.Category;
import repository.CategoryRepository;

import java.util.List;

public class CategoryDAOService {

    private CategoryRepository categoryRepository;

    public CategoryDAOService() {
        this.categoryRepository = new CategoryRepository();
    }

    public List<Category> getAll(){
        List<Category> categories = categoryRepository.getAll();
        if(categories != null){
            System.out.println(categories);
        }else {
            System.out.println("There is no category.");
        }

        return categories;
    }

    public Category getCategoryByName(String name){
        Category category = categoryRepository.readName(name);
        if(category != null){
            System.out.println(category);
        }else {
            System.out.println("No category having this name");
        }

        return category;
    }

    public Category getCategoryByIndex(int index){
        Category category = categoryRepository.readIndex(index);
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

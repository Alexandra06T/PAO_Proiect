package daoservices;

import model.Category;
import dao.CategoryDao;

import java.util.List;

public class CategoryRepositoryService {

    private CategoryDao categoryDao;

    public CategoryRepositoryService() {
        this.categoryDao = new CategoryDao();
    }

    public List<Category> getAll(){
        List<Category> categories = categoryDao.getAll();
        if(categories != null){
            System.out.println(categories);
        }else {
            System.out.println("There is no category.");
        }

        return categories;
    }

    public Category getCategoryByName(String name){
        Category category = categoryDao.readName(name);
        if(category != null){
            System.out.println(category);
        }else {
            System.out.println("No category having this name");
        }

        return category;
    }

    public Category getCategoryByIndex(int index){
        Category category = categoryDao.readIndex(index);
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

        categoryDao.delete(category);

        System.out.println("Removed " + category);
    }

    public void addCategory(Category category) {
        if(category != null){
            categoryDao.create(category);
        }
    }
}

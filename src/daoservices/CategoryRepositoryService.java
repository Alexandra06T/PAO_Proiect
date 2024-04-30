package daoservices;

import model.Book;
import model.Category;
import dao.CategoryDao;
import utils.InvalidDataException;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class CategoryRepositoryService {

    private CategoryDao categoryDao = CategoryDao.getInstance();

    public CategoryRepositoryService() throws SQLException {}

    public void printAll() {
        try {
            List<Category> categories = categoryDao.getAll();
            if(categories != null){
                categories.forEach(System.out:: println);
            }else {
                System.out.println("There is no category.");
            }

        } catch (SQLException e) {
            System.out.println("SQLException " + e.getSQLState() + " " + e.getMessage());
        }
    }

    public List<Category> getAll() {
        List<Category> categories = null;

        try {
            categories = categoryDao.getAll();
            if(categories == null){
                System.out.println("There is no category.");
            }

        } catch (SQLException e) {
            System.out.println("SQLException " + e.getSQLState() + " " + e.getMessage());
        }

        return categories;
    }

    public Category getCategoryByName(String name){
        Category category = null;

        try {
            category = categoryDao.readByName(name);
            if(category == null)
                System.out.println("No category having this name");
        } catch (SQLException e) {
            System.out.println("SQLException " + e.getSQLState() + " " + e.getMessage());
        }

        return category;
    }

    public Category getCategoryByIndex(int index){
        Category category = null;
        try {

            category = categoryDao.read(String.valueOf(index));
            if(category == null)
                System.out.println("No category having this index");
        } catch (SQLException e) {
            System.out.println("SQLException " + e.getSQLState() + " " + e.getMessage());
        }

        return category;
    }

    public void removeCategory(String name) {
        Category category = null;

        try {
            category = getCategoryByName(name);
            if (category == null) return;

            List<Book> bookList = category.getBooks();
            if(bookList != null) {
                for(Book b : bookList) {
                    b.setCategory(null);
                }
            }

            categoryDao.delete(category);

        } catch (SQLException e) {
            System.out.println("SQLException " + e.getSQLState() + " " + e.getMessage());
        }

    }

    public void addCategory(Category category) throws InvalidDataException {
        try {
            if(category != null){
                //verificam sa nu mai existe o alta categorie avand acelasi nume sau index
                if(categoryDao.read(String.valueOf(category.getCategoryIndex())) != null)
                    throw new InvalidDataException("There is already a category having this index!");
                if(categoryDao.readByName(category.getName()) != null)
                    throw new InvalidDataException("There is already a category having this name!");

                categoryDao.add(category);
                System.out.println("Category added to the catalogue");
            }
        } catch (SQLException e) {
            System.out.println("SQLException " + e.getSQLState() + " " + e.getMessage());
        }
    }

    public void updateCategory(Category category) throws InvalidDataException {
        try {
            if(category != null){
                //verificam sa nu mai existe o alta categorie avand acelasi nume
                if(!categoryDao.checkUniqueName(category.getName()))
                    throw new InvalidDataException("There is already a category having this name!");

                categoryDao.update(category);
                System.out.println("Category updated successfully!");
            }
        } catch (SQLException e) {
            System.out.println("SQLException " + e.getSQLState() + " " + e.getMessage());
        }
    }
}

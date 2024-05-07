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

    public void printAll() throws InvalidDataException {
        try {
            List<Category> categories = categoryDao.getAll();
            if(categories == null)
                throw new InvalidDataException("There is no category.");
            categories.forEach(System.out:: println);

        } catch (SQLException e) {
            System.out.println("SQLException " + e.getSQLState() + " " + e.getMessage());
        }
    }

    public List<Category> getAll() throws InvalidDataException {
        List<Category> categories = null;

        try {
            categories = categoryDao.getAll();
            if(categories == null){
                throw new InvalidDataException("There is no category.");
            }

        } catch (SQLException e) {
            System.out.println("SQLException " + e.getSQLState() + " " + e.getMessage());
        }

        return categories;
    }

    public Category getCategoryByName(String name) throws InvalidDataException {
        Category category = null;

        try {
            category = categoryDao.readByName(name);
            if(category == null)
                throw new InvalidDataException("No category having this name");
        } catch (SQLException e) {
            System.out.println("SQLException " + e.getSQLState() + " " + e.getMessage());
        }

        return category;
    }

    public Category getCategoryByIndex(int index) throws InvalidDataException {
        Category category = null;
        try {

            category = categoryDao.read(String.valueOf(index));
            if(category == null)
                throw new InvalidDataException("No category having this index");
        } catch (SQLException e) {
            System.out.println("SQLException " + e.getSQLState() + " " + e.getMessage());
        }

        return category;
    }

    public void removeCategory(Category category) throws InvalidDataException {
        if (category == null)
            throw new InvalidDataException("Invalid category");
        try {
            categoryDao.delete(category);
        } catch (SQLException e) {
            System.out.println("SQLException " + e.getSQLState() + " " + e.getMessage());
        }

    }

    public void addCategory(Category category) throws InvalidDataException {
        try {
            if(category == null)
                throw new InvalidDataException("Invalid category");
            //verificam sa nu mai existe o alta categorie avand acelasi nume sau index
            if(categoryDao.read(String.valueOf(category.getCategoryIndex())) != null)
                throw new InvalidDataException("There is already a category having this index!");
            if(categoryDao.readByName(category.getName()) != null)
                throw new InvalidDataException("There is already a category having this name!");

            categoryDao.add(category);
            System.out.println("Category added to the catalogue");
        } catch (SQLException e) {
            System.out.println("SQLException " + e.getSQLState() + " " + e.getMessage());
        }
    }

    public void updateCategory(Category category) throws InvalidDataException {
        try {
            if(category == null)
                throw new InvalidDataException("Invalid category");
            //verificam sa nu mai existe o alta categorie avand acelasi nume
            Category dupl = categoryDao.readByName(category.getName());
            if(dupl != null && dupl.getCategoryIndex() != category.getCategoryIndex())
                throw new InvalidDataException("There is already a category having this name!");

            categoryDao.update(category);
            System.out.println("Category updated successfully!");
        } catch (SQLException e) {
            System.out.println("SQLException " + e.getSQLState() + " " + e.getMessage());
        }
    }
}

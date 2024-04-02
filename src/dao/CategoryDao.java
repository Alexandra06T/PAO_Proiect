package dao;

import model.Category;

import java.util.ArrayList;
import java.util.List;

public class CategoryDao {
    private static List<Category> categories = new ArrayList<>();

    public List<Category> getAll() {
        if(!categories.isEmpty()){
            return categories;
        }
        return null;
    }

    public Category readName(String name) {
        if(!categories.isEmpty()){
            for(Category c : categories){
                if(c.getName().equals(name)){
                    return c;
                }
            }
        }
        return null;
    }

    public Category readIndex(int index) {
        if(!categories.isEmpty()){
            for(Category c : categories){
                if(c.getIndex() == index){
                    return c;
                }
            }
        }
        return null;
    }

    public void delete(Category branchLibrary) {
        categories.remove(branchLibrary);
    }

    public void create(Category branchLibrary) {
        categories.add(branchLibrary);
    }
}

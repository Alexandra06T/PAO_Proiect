package model;

import java.util.ArrayList;
import java.util.List;

public class Category {
    private String name;
    private int categoryIndex;

    public Category() {}

    public Category(String name, int categoryIndex) {
        this.name = name;
        this.categoryIndex = categoryIndex;
    }

    public Category(Category category) {
        this.name = category.getName();
        this.categoryIndex = category.getCategoryIndex();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCategoryIndex() {
        return categoryIndex;
    }

    public void setCategoryIndex(int categoryIndex) {
        this.categoryIndex = categoryIndex;
    }

    @Override
    public String toString() {
        return name +
                " (" + categoryIndex + ")";
    }
}

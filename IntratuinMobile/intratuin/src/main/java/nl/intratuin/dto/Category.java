package nl.intratuin.dto;

import java.io.Serializable;

/**
 * Created by Иван on 04.04.2016.
 */
public class Category implements Serializable{

    private int categoryId;

    private String name;

    private int parentId;

    public Category(){}

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }
}
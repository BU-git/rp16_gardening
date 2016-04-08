package nl.intratuin.dto;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Иван on 04.04.2016.
 */
public class Category implements Parcelable {

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(categoryId);
        out.writeString(name);
        out.writeInt(parentId);
    }

    public static final Parcelable.Creator<Category> CREATOR = new Parcelable.Creator<Category>(){
        public Category createFromParcel(Parcel in){
            return new Category(in);
        }
        public Category[] newArray(int size){
            return new Category[size];
        }
    };

    private Category(Parcel in){
        categoryId = in.readInt();
        name = in.readString();
        parentId = in.readInt();
    }
}
package nl.intratuin.dto;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Class {@code Category} is creating DTO named "Category".
 * This DTO able to be written to and restored from a {@code Parcel}.
 *
 * @author Ivan
 * @see Parcel
 * @see Parcelable
 * @since 04.04.2016.
 */
public class Category implements Parcelable {

    private int categoryId;

    private String name;

    private int parentId;

    /**
     * Instantiates a new Category.
     */
    //do we need an empty constructor?
    public Category() {
    }

    //why private?
    private Category(Parcel in) {
        categoryId = in.readInt();
        name = in.readString();
        parentId = in.readInt();
    }

    /**
     * Describe the kinds of special objects contained in this Parcelable's marshalled representation
     *
     * @return some bitmask
     * @see Parcelable
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Flatten {@code Category} object in to a {@code Parcel}
     *
     * @param out
     * @param flags
     * @see Parcel
     * @see Parcelable
     */
    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(categoryId);
        out.writeString(name);
        out.writeInt(parentId);
    }

    /**
     * The constant CREATOR generates instances of {@code Category} class from a Parcel
     *
     * @see Parcel
     * @see Parcelable
     */
    public static final Parcelable.Creator<Category> CREATOR = new Parcelable.Creator<Category>() {
        public Category createFromParcel(Parcel in) {
            return new Category(in);
        }

        public Category[] newArray(int size) {
            return new Category[size];
        }
    };

    /**
     * Gets category id.
     *
     * @return the category id
     */
    public int getCategoryId() {
        return categoryId;
    }

    /**
     * Sets category id.
     *
     * @param categoryId the category id
     */
    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets parent id.
     *
     * @return the parent id
     */
    public int getParentId() {
        return parentId;
    }

    /**
     * Sets parent id.
     *
     * @param parentId the parent id
     */
    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

}
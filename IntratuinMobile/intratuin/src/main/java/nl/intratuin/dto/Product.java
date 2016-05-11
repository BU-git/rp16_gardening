package nl.intratuin.dto;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Class {@code Product} is creating DTO named "Product".
 * This DTO able to be written to and restored from a {@code Parcel}.
 *
 * @author Ivan
 * @see Parcel
 * @see Parcelable
 * @since 04.04.2016.
 */
public class Product implements Parcelable {
    private int productId;
    private String productName;
    private double productPrice;
    private String productImage;
    private int categoryId;
    private long barcode;

    private Product(Parcel in) {
        productId = in.readInt();
        categoryId = in.readInt();
        productName = in.readString();
        productImage = in.readString();
        productPrice = in.readDouble();
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
     * Flatten {@code Product} object in to a {@code Parcel}
     *
     * @param out
     * @param flags
     * @see Parcel
     * @see Parcelable
     */
    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(productId);
        out.writeInt(categoryId);
        out.writeString(productName);
        out.writeString(productImage);
        out.writeDouble(productPrice);
    }

    /**
     * The constant CREATOR generates instances of {@code Product} class from a Parcel
     *
     * @see Parcel
     * @see Parcelable
     */
    public static final Parcelable.Creator<Product> CREATOR = new Parcelable.Creator<Product>() {
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        public Product[] newArray(int size) {
            return new Product[size];
        }
    };

    /**
     * Gets barcode.
     *
     * @return the barcode
     */
    public long getBarcode() {
        return barcode;
    }

    /**
     * Sets barcode.
     *
     * @param barcode the barcode
     */
    public void setBarcode(long barcode) {
        this.barcode = barcode;
    }

    /**
     * Instantiates a new Product.
     */
    public Product() {
    }

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
     * Gets product id.
     *
     * @return the product id
     */
    public int getProductId() {
        return productId;
    }

    /**
     * Sets product id.
     *
     * @param productId the product id
     */
    public void setProductId(int productId) {
        this.productId = productId;
    }

    /**
     * Gets product price.
     *
     * @return the product price
     */
    public double getProductPrice() {
        return productPrice;
    }

    /**
     * Sets product price.
     *
     * @param productPrice the product price
     */
    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    /**
     * Gets product image.
     *
     * @return the product image
     */
    public String getProductImage() {
        return productImage;
    }

    /**
     * Sets product image.
     *
     * @param productImage the product image
     */
    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    /**
     * Gets product name.
     *
     * @return the product name
     */
    public String getProductName() {
        return productName;
    }

    /**
     * Sets product name.
     *
     * @param productName the product name
     */
    public void setProductName(String productName) {
        this.productName = productName;
    }

    @Override
    public String toString() {
        return productName + " - " + getProductPrice() + " €";
    }
}

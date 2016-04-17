package nl.intratuin.dto;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Product implements Parcelable {
    private int productId;
    private String productName;
    private double productPrice;
    private String productImage;
    private int categoryId;

    public Product(){}

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    @Override
    public String toString() {
        return productName + " - " + getProductPrice() + " €";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(productId);
        out.writeInt(categoryId);
        out.writeString(productName);
        out.writeString(productImage);
        out.writeDouble(productPrice);
    }

    public static final Parcelable.Creator<Product> CREATOR = new Parcelable.Creator<Product>(){
        public Product createFromParcel(Parcel in){
            return new Product(in);
        }
        public Product[] newArray(int size){
            return new Product[size];
        }
    };

    private Product(Parcel in){
        productId = in.readInt();
        categoryId = in.readInt();
        productName = in.readString();
        productImage = in.readString();
        productPrice = in.readDouble();
    }
}

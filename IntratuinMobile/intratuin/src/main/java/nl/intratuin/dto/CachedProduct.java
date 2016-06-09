package nl.intratuin.dto;

import android.os.Parcel;
import android.os.Parcelable;

public class CachedProduct implements Parcelable {
    private int productId;
    private String productName;
    private double productPrice;
    private String productImage;
    private String cachedDate;
    private long barcode;

    private CachedProduct(Parcel in) {
        productId = in.readInt();
        cachedDate = in.readString();
        productName = in.readString();
        productImage = in.readString();
        productPrice = in.readDouble();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(productId);
        out.writeString(cachedDate);
        out.writeString(productName);
        out.writeString(productImage);
        out.writeDouble(productPrice);
    }

    public static final Parcelable.Creator<CachedProduct> CREATOR = new Parcelable.Creator<CachedProduct>() {
        public CachedProduct createFromParcel(Parcel in) {
            return new CachedProduct(in);
        }

        public CachedProduct[] newArray(int size) {
            return new CachedProduct[size];
        }
    };

    public long getBarcode() {
        return barcode;
    }

    public void setBarcode(long barcode) {
        this.barcode = barcode;
    }

    public CachedProduct() {
    }

    public String getCachedDate() {
        return cachedDate;
    }

    public void setCachedDate(String cachedDate) {
        this.cachedDate = cachedDate;
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

}

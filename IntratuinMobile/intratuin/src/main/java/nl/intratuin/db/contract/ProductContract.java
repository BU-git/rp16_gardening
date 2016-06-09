package nl.intratuin.db.contract;

import android.provider.BaseColumns;

public class ProductContract {

    public ProductContract(){}

    public static final String CREATE =
            "CREATE TABLE IF NOT EXISTS " + ProductEntity.TABLE_NAME + " ("
                    + ProductEntity._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + ProductEntity.CUSTOMER_NAME + " TEXT NOT NULL,"
                    + ProductEntity.PRODUCT_ID + " INTEGER NOT NULL,"
                    + ProductEntity.NAME + " TEXT NOT NULL,"
                    + ProductEntity.PRICE + " DOUBLE NOT NULL,"
                    + ProductEntity.IMAGE + " TEXT NOT NULL,"
                    + ProductEntity.BARCODE + " LONG NOT NULL,"
                    + ProductEntity.CATEGORY_ID + " INTEGER NOT NULL,"
                    + ProductEntity.DATE + " TEXT NOT NULL,"
                    + ProductEntity.STATUS + " TEXT NOT NULL"
                    + ")";

    public static final String DELETE =
            "DROP TABLE " + ProductEntity.TABLE_NAME;

    /* Inner class that defines the table contents */
    public static class ProductEntity implements BaseColumns {
        public static final String TABLE_NAME = "product";

        public static final String CUSTOMER_NAME = "customerName";
        public static final String PRODUCT_ID = "productId";
        public static final String NAME = "productName";
        public static final String PRICE = "productPrice";
        public static final String IMAGE = "productImage";
        public static final String CATEGORY_ID = "categoryId";
        public static final String BARCODE = "barcode";
        public static final String DATE = "dateSave";
        public static final String STATUS = "status";

    }
}

package nl.intratuin.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import nl.intratuin.db.contract.ProductContract;

public class DBHelper extends SQLiteOpenHelper {

    public static final int VERSION = 2;

    public static final String DB_NAME = "database.db";

    public DBHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(ProductContract.CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(ProductContract.DELETE);
        onCreate(db);
    }
}

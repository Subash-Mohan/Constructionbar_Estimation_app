package com.billing.oppiliappanagency;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String CUSTOMER_TABLE = "CUSTOMER_TABLE";
    public static final String COLUMN_CUSTOMER_NAME = "CUSTOMER_NAME";
    public static final String COLUMN_CUSTOMER_QUANTITY = "CUSTOMER_QUANTITY";
    public static final String COLUMN_TOTAL_AMOUNT = "TOTAL_AMOUNT";
    public static final String COLUMN_ID = "ID";
    Context contex;
    public DatabaseHelper(@Nullable Context context) {
        super(context,"customer.db",null,1);
        contex=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {//creating columns of db..
        String createTableStatement= "CREATE TABLE " + CUSTOMER_TABLE + "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_CUSTOMER_NAME + " TEXT," + COLUMN_CUSTOMER_QUANTITY + " TEXT," + COLUMN_TOTAL_AMOUNT + " TEXT)";
        db.execSQL(createTableStatement);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public boolean addOne(CustomerModel customerModel){//adding the data to the db..
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put(COLUMN_CUSTOMER_NAME,customerModel.getCustomer_name());
        cv.put(COLUMN_CUSTOMER_QUANTITY,customerModel.getNumber_of_products());
        cv.put(COLUMN_TOTAL_AMOUNT,customerModel.getTotal_amount());
        long insert = db.insert(CUSTOMER_TABLE, null, cv);
        if(insert==-1){
            return false;
        }else {
           // Toast.makeText(contex,String.valueOf(insert), Toast.LENGTH_SHORT).show();
            return true;
        }

    }
    public List<CustomerModel> getEveryone(){//reading the data from db..
        List<CustomerModel> returnList= new ArrayList<>();
        String queryString="SELECT * FROM " + CUSTOMER_TABLE;
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString,null);
        if(cursor.moveToFirst()){
            do{
                int customerID=cursor.getInt(0);
                String customerName = cursor.getString(1);
                String Quantityofproducts=cursor.getString(2);
                String Totalamountofproducts=cursor.getString(3);
                CustomerModel newCustomer=new CustomerModel(customerID,customerName,Quantityofproducts,Totalamountofproducts);
                returnList.add(newCustomer);
            }while(cursor.moveToNext());
        }else{
            Toast toast=Toast.makeText(contex, "Nothing in History..", Toast.LENGTH_SHORT);
        }


        cursor.close();
        db.close();
        return returnList;
    }
    public void deleteCourse() {//deleting the db..

        // on below line we are creating
        // a variable to write our database.
        SQLiteDatabase db = this.getWritableDatabase();

        // on below line we are calling a method to delete our
        // course and we are comparing it with our course name.
        db. execSQL("DELETE FROM CUSTOMER_TABLE");
        db. close();//delete all rows in a table
    }
}

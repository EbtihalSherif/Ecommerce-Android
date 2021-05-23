package com.example.newversion;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;

public class EcommerceDBHelper extends SQLiteOpenHelper {

    private static String databaseName="ecommerce_database";
    SQLiteDatabase ecommerce_database;
    Context mContext;
    public EcommerceDBHelper(Context c)
    {

        super(c,databaseName,null,2);
        mContext=c;

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table Customer(CustID integer primary key autoincrement,CustName text ,Username text Unique,Password text,Gender text ,BirthDate text,Job text)");
        db.execSQL("create table Category(CatID integer primary key autoincrement,CatName text unique)");

        db.execSQL("create table Product(ProID integer primary key autoincrement," +
                "ProName text not null unique , price integer not null,Quantity integer not null," +
                "cat_id integer, img blob," +
                "FOREIGN KEY (cat_id) REFERENCES Category (CatID));");

        db.execSQL("create table Orders(OrdID integer primary key autoincrement," +
                "OrdDate text not null , Address text not null," +
                "cust_id integer," +
                "FOREIGN KEY (cust_id) REFERENCES Customer (CustID));");

        db.execSQL("create table OrderDetails(OrdID integer not null ," +
                "ProID integer not null  , Quantity integer not null,PRIMARY KEY(OrdID,ProID)," +
                "FOREIGN KEY (OrdID) REFERENCES Orders (OrdID),FOREIGN KEY (ProID) REFERENCES Product (ProID));");



    }

    public Cursor get_customer_orders(int custid)
    {
        ecommerce_database =getReadableDatabase();
        Cursor cursor= ecommerce_database.rawQuery("select *  from Orders where cust_id="+custid+" ",null);
        // if(cursor !=null)
        cursor.moveToFirst();
        ecommerce_database.close();
        String id=cursor.getString(0);
        return cursor;
    }

    public String get_order_ID()
    {
        ecommerce_database =getReadableDatabase();
        Cursor cursor= ecommerce_database.rawQuery("select max(OrdID)  from Orders ",null);
        // if(cursor !=null)
        cursor.moveToFirst();
        ecommerce_database.close();
        String id=cursor.getString(0);
        return id;
    }
    public void add_Order(String Date, String customerID, String Address)
    {
        ContentValues row =new ContentValues();
        row.put("OrdDate",Date);
        row.put("Address",Address);
        row.put("cust_id",customerID);

        ecommerce_database =getWritableDatabase();
        ecommerce_database.insert("Orders",null,row);
        ecommerce_database.close();
    }
    public void update_prod_qty(String Name,String Newqty)
    {
        ContentValues row =new ContentValues();
        row.put("Quantity",Newqty);

        ecommerce_database =getWritableDatabase();
        ecommerce_database.update("Product",row,"ProName='"+Name+"'",null);
        ecommerce_database.close();
    }
    public void add_OrderDetails(String order_id,String product_id,String quantiy)
    {
        ContentValues row =new ContentValues();
        row.put("OrdID",order_id);
        row.put("ProID",product_id);
        row.put("Quantity",quantiy);

        ecommerce_database =getWritableDatabase();
        ecommerce_database.insert("OrderDetails",null,row);
        ecommerce_database.close();
    }
    public String searchProducts_specific(String name)
    {

        ecommerce_database=getReadableDatabase();
        String[] arg={name};
        Cursor cursor=ecommerce_database.rawQuery("select * from Product where ProName like '"+name+"' ",null);
        //if(cursor !=null)
        cursor.moveToFirst();
        ecommerce_database.close();
        return cursor.getString(0);
    }
    public Cursor searchProducts(String name)
    {

            ecommerce_database=getReadableDatabase();
            String[] arg={name};
            Cursor cursor=ecommerce_database.rawQuery("select * from Product where ProName like '%"+name+"%' ",null);
            //if(cursor !=null)
            cursor.moveToFirst();
            ecommerce_database.close();
            return cursor;
        }
    public Cursor searchCategory(String name)
    {

        ecommerce_database=getReadableDatabase();
        String[] arg={name};
        Cursor cursor=ecommerce_database.rawQuery("select * from Category where CatName like '%"+name+"%' ",null);
        //if(cursor !=null)
        cursor.moveToFirst();
        ecommerce_database.close();
        return cursor;
    }
    public void add_product(String name,int price,int quantity,int cat_ID)
    {
        ContentValues row =new ContentValues();
        row.put("ProName",name);
        row.put("price",price);
        row.put("Quantity",quantity);
        row.put("cat_id",cat_ID);

        ecommerce_database =getWritableDatabase();
        ecommerce_database.insert("Product",null,row);
        ecommerce_database.close();
    }

    public void add_category(String name)
    {
        ContentValues row =new ContentValues();
        row.put("CatName",name);


        ecommerce_database =getWritableDatabase();
        ecommerce_database.insert("Category",null,row);
        ecommerce_database.close();
    }
    public void delete_All()
    {
      ecommerce_database =getWritableDatabase();
      ecommerce_database.delete("Category","CatName='Fashion'",null);
        ecommerce_database.delete("Category","CatName='Food and Beverage'",null);
        ecommerce_database.delete("Category","CatName='Makeup'",null);
        ecommerce_database.delete("Category","CatName='Home'",null);


    }

    public void createNewCustomer(String name,String username,String password,String gender,String Birthdate,String job)
    {
        ContentValues row =new ContentValues();
        row.put("CustName",name);
        row.put("Username",username);
        row.put("Password",password);
        row.put("Gender",gender);
        row.put("BirthDate",Birthdate);
        row.put("Job",job);


        ecommerce_database =getWritableDatabase();
        ecommerce_database.insert("Customer",null,row);
        ecommerce_database.close();
    }
    public Cursor getcustomerData(String userName,String password)
    {
        ecommerce_database =getReadableDatabase();
        Cursor cursor= ecommerce_database.rawQuery("select * from Customer where Username = '"+userName+
                "' and Password='"+password+"'",null);
       // if(cursor !=null)
           cursor.moveToFirst();
        ecommerce_database.close();
        return cursor;
    }
    public String find_password(String userName,String Name )
    {
        ecommerce_database =getReadableDatabase();
        Cursor cursor= ecommerce_database.rawQuery("select *  from Customer where Username = '"+userName+
                "' and CustName='"+Name+"'",null);
        // if(cursor !=null)
        cursor.moveToFirst();
        ecommerce_database.close();
        String pass=cursor.getString(3);
        return pass;
    }
    public Cursor Fetchcategories()
    {
        ecommerce_database =getReadableDatabase();
        Cursor cursor= ecommerce_database.rawQuery("select * from Category",null);
        cursor.moveToFirst();
        ecommerce_database.close();
        return cursor;
    }

    public Cursor FetchAllProductsincategory(String cat_ID)
    {
        ecommerce_database =getReadableDatabase();
        Cursor cursor= ecommerce_database.rawQuery("select * from Product where cat_id='"+ cat_ID+"' ",null);
        cursor.moveToFirst();
        ecommerce_database.close();
        return cursor;
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists Customer");
        db.execSQL("drop table if exists Category");
        db.execSQL("drop table if exists product");
        db.execSQL("drop table if exists Orders");
        db.execSQL("drop table if exists OrderDetails");
        onCreate(db);

    }

}
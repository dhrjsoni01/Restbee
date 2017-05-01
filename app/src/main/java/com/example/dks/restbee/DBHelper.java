package com.example.dks.restbee;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by DKS on 4/30/2017.
 */

public class DBHelper extends SQLiteOpenHelper {

    private static final String dbname= "mydb";
    private static final String table = "record";
    private static final String name = "name";
    private static final String pass = "pass";
    private static final String type = "type";
    private static final String email = "email_or_roll";


    public DBHelper(Context context) {
        super(context, dbname, null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+table+" (`id` INTEGER PRIMARY KEY AUTOINCREMENT, `"+email+"` TEXT, `"+name+"` TEXT, `"+pass+"` TEXT, `"+type+"` TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }


    public Cursor getdata(String email){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res= db.rawQuery("select `"+name+"`, `"+pass+"`, `"+type+"`  from `"+table+"` where `"+this.email+"` like '"+email+"' ",null);
        return res;
    }

    public Boolean addAdmin(String email,String name,String pass,String type){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(this.email,email);
        contentValues.put(this.name,name);
        contentValues.put(this.pass,pass);
        contentValues.put(this.type,type);
        long result = db.insert(table,null,contentValues);
        if (result==-1){
            return false;
        }else{
            return true;
        }

    }

}

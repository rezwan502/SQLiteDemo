package com.example.sqlitedatabasedemo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class MyDataBaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "student.db";
    private static final String TABLE_NAME = "student_details";
    private static final int VERSION_NUMBER = 1;

    private static final String ID = "_id";
    private static final String NAME = "Name";
    private static final String AGE = "Age";
    private static final String GENDER = "Gender";

    private Context context;

    private static final String Create_table = "CREATE TABLE "+TABLE_NAME+"("+ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+NAME+" VARCHAR(255), "+AGE+" INTEGER, "+GENDER+" VARCHAR(255));";
    private static final String Drop_table = "DROP TABLE IF EXISTS " + TABLE_NAME;
    private static final String SELECT_ALL = "SELECT * FROM " + TABLE_NAME;


    public MyDataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION_NUMBER);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        try{

            Toast.makeText(context,"db created",Toast.LENGTH_LONG).show();
            db.execSQL(Create_table);

        }catch (Exception e){
            Toast.makeText(context,"Error : " + e,Toast.LENGTH_LONG).show();
        }


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try{
            Toast.makeText(context,"db upgraded",Toast.LENGTH_LONG).show();
            db.execSQL(Drop_table);
            onCreate(db);
        }catch (Exception e){
            Toast.makeText(context,"Error : " + e,Toast.LENGTH_LONG).show();
        }
    }

    public long InsertData(String Name, String Age, String Gender){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(NAME,Name);
        contentValues.put(AGE,Age);
        contentValues.put(GENDER, Gender);

        long rowId = sqLiteDatabase.insert(TABLE_NAME,null,contentValues);

       // sqLiteDatabase.close();

        return rowId;

    }

    public Cursor displayAllData(){

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(SELECT_ALL, null);

        //cursor.close();

        return cursor;

    }

    public void updateData(String mId, String mName, String mAge, String mGender){

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ID,mId);
        contentValues.put(NAME,mName);
        contentValues.put(GENDER,mGender);
        contentValues.put(AGE,mAge);

        sqLiteDatabase.update(TABLE_NAME,contentValues,ID+" = ?", new String[]{mId});

    }

    public void deleteData(String mId){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.delete(TABLE_NAME,ID + " = ?", new String[]{mId});
    }


}

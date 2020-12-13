package com.example.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.model.Person;

public class DatabaseHandler extends SQLiteOpenHelper
{
    private static final String DATABASE_NAME = "TakeCare";
    private static final int VERSION = 1;
    private static final String TABLE_PERSON = "Person";
    private static final String TABLE_COURSE = "Course";
    private static final String TABLE_MEDICINES = "Medicines";
    private static final String TABLE_REPORTS = "Reports";

    public DatabaseHandler(Context context)
    {
        super(context,"TakeCare",null,VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        String CREATE_PERSON = "CREATE TABLE " + TABLE_PERSON + " ( KEY1 INTEGER PRIMARY KEY, NAME TEXT NOT NULL, DOB TEXT NOT NULL, SEX TEXT NOT NULL, HEIGHT REAL NOT NULL, WEIGHT REAL NOT NULL)";
        db.execSQL(CREATE_PERSON);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PERSON);
        onCreate(db);
    }

    public Person gePerson()
    {
        Person p1 = new Person();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(false,TABLE_PERSON,new String[]{"KEY1","NAME","DOB","SEX","HEIGHT","WEIGHT"},"KEY1" + "=?",new String[]{"1"},null,null,null,null,null);
        if(cursor==null)
        {
            p1.setName("NotSet");
            p1.setDob("NotSet");
            p1.setKey(1);
            p1.setHeight(0.0);
            p1.setSex("Male");
            p1.setWeight(0.0);
        }
    }

}

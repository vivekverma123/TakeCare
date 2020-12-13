package com.example.database;

import android.content.ContentValues;
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

    public Person getPerson()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(false,TABLE_PERSON,new String[]{"KEY1","NAME","DOB","SEX","HEIGHT","WEIGHT"},"KEY1" + "=?",new String[]{"1"},null,null,null,null,null);

        if(cursor==null)
        {
            return null;
        }
        else if(cursor.getCount()>0)
        {
            cursor.moveToFirst();
            Person person = new Person(Integer.parseInt(cursor.getString(0)),cursor.getString(1),cursor.getString(2),cursor.getString(3),Double.parseDouble(cursor.getString(4)),Double.parseDouble(cursor.getString(5)));
            return person;
        }
        else
        {
            return null;
        }

    }

    public void updatePerson(Person person)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("KEY1",person.getKey());
        values.put("NAME",person.getName());
        values.put("DOB",person.getDob());
        values.put("SEX",person.getSex());
        values.put("HEIGHT",person.getHeight());
        values.put("WEIGHT",person.getWeight());
        if(db.update(TABLE_PERSON,values,"KEY1=?",new String[]{"1"})==0)
        {
            db.insert(TABLE_PERSON,null,values);
        }
        db.close();
    }

}

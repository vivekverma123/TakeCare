package com.example.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.model.Appointment;
import com.example.model.Person;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;

public class DatabaseHandler extends SQLiteOpenHelper
{
    private static final String DATABASE_NAME = "UserDatabase";
    private static final int VERSION = 1;
    private static final String TABLE_PERSON = "Person";
    private static final String TABLE_APPOINTMENT = "Appointments";
    private static final String TABLE_COURSE = "Courses";
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
        String CREATE_APPOINTMENT = "CREATE TABLE " + TABLE_APPOINTMENT + " ( APPOINTMENT_KEY INTEGER PRIMARY KEY AUTOINCREMENT, DOCTOR TEXT NOT NULL, DATE TEXT NOT NULL, TIME TEXT NOT NULL, URL TEXT NOT NULL)";
        String CREATE_COURSE = "CREATE TABLE " + TABLE_COURSE + " ( COURSE_KEY INTEGER PRIMARY KEY AUTOINCREMENT, FROM1 TEXT NOT NULL, TO1 TEXT NOT NULL, INSTRUCTIONS TEXT NOT NULL, CONSTRAINT FK_APPOINTMENT_KEY FOREIGN KEY (APPOINTMENT_KEY) REFERENCES Appointments(APPOINTMENT_KEY))";
        String CREATE_MEDICINE = "CREATE TABLE " + TABLE_MEDICINES + " (MEDICINE_KEY INTEGER PRIMARY KEY AUTOINCREMENT, DOSES INTEGER NOT NULL, DOSES_TAKEN INTEGER NOT NULL, NAME TEXT NOT NULL, TIME1 TEXT NOT NULL, TIME2 TEXT NOT NULL, TIME3 TEXT NOT NULL, TIME4 TEXT NOT NULL, TIME5 TEXT NOT NULL, T1 INTEGER NOT NULL, T2 INTEGER NOT NULL, T3 INTEGER NOT NULL, T4 INTEGER NOT NULL, T5 INTEGER NOT NULL, CONSTRAINT FK_COURSE_KEY FOREIGN KEY (COURSE_KEY) REFERENCES Courses(COURSE_KEY))";
        String CREATE_REPORT = "CREATE TABLE " + TABLE_REPORTS + " ( REPORT_KEY INTEGER PRIMARY KEY AUTOINCREMENT, TYPE TEXT NOT NULL, REMARKS TEXT NOT NULL, URL TEXT NOT NULL, CONSTRAINT FK_APPOINTMENT_KEY FOREIGN KEY (APPOINTMENT_KEY) REFERENCES Appointments(APPOINTMENT_KEY))";

        db.execSQL(CREATE_PERSON);
        db.execSQL(CREATE_APPOINTMENT);
        db.execSQL(CREATE_COURSE);
        db.execSQL(CREATE_MEDICINE);
        db.execSQL(CREATE_REPORT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PERSON);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_APPOINTMENT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COURSE);
        onCreate(db);
    }

    public Person getPerson()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(false,TABLE_PERSON,new String[]{"KEY1","NAME","DOB","SEX","HEIGHT","WEIGHT"},"KEY1" + "=?",new String[]{"1"},null,null,null,null,null);

        if(cursor.getCount()>0)
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

    public void insertAppointment(Appointment appointment)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("DOCTOR",appointment.getDoctor());
        values.put("DATE",appointment.getDate());
        values.put("TIME",appointment.getTime());
        values.put("URL",appointment.getUrl().toString());
        db.insert(TABLE_APPOINTMENT,null,values);
        db.close();
    }

    public void deleteAppointment(int id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_APPOINTMENT, "APPOINTMENT_KEY" + " = ?", new String[] { String.valueOf(id) });
        db.close();
    }

    public void getAppointment(int id)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(false,TABLE_APPOINTMENT,new String[]{"APPOINTMENT_KEY","DOCTOR","DATE","TIME","URL"},"APPOINTMENT_KEY" + "=?",new String[]{String.valueOf(id)},null,null,null,null,null);
    }

    public void updateAppointment(Appointment appointment)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("DOCTOR",appointment.getDoctor());
        values.put("DATE",appointment.getDate());
        values.put("TIME",appointment.getTime());
        values.put("URL",appointment.getUrl().toString());
        db.update(TABLE_APPOINTMENT,values,"APPOINTMENT_KEY=?",new String[]{String.valueOf(appointment.getAppointmentKey())});
    }

    public ArrayList<Appointment> getAllAppointments() throws MalformedURLException
    {
        ArrayList <Appointment> arrayList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Appointments", null);
        if (cursor.moveToFirst())
        {
            do
            {
                Appointment appointment = new Appointment(Integer.parseInt(cursor.getString(0)),cursor.getString(1),cursor.getString(2),cursor.getString(3),new URL(cursor.getString(4)));
                arrayList.add(appointment);
            } while (cursor.moveToNext());
        }
        Collections.reverse(arrayList);
        return arrayList;
    }

}

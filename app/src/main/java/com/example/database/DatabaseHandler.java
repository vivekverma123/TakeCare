package com.example.database;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.model.Appointment;
import com.example.model.Course;
import com.example.model.Medicine;
import com.example.model.Person;
import com.example.model.Report;

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
        String CREATE_COURSE = "CREATE TABLE " + TABLE_COURSE + " ( COURSE_KEY INTEGER PRIMARY KEY AUTOINCREMENT, FROM1 TEXT NOT NULL, TO1 TEXT NOT NULL, INSTRUCTIONS TEXT NOT NULL, FK_APPOINTMENT_KEY INTEGER NOT NULL, FOREIGN KEY (FK_APPOINTMENT_KEY) REFERENCES Appointments(APPOINTMENT_KEY))";
        String CREATE_MEDICINE = "CREATE TABLE " + TABLE_MEDICINES + " (MEDICINE_KEY INTEGER PRIMARY KEY AUTOINCREMENT, DOSES INTEGER NOT NULL, DOSES_TAKEN INTEGER NOT NULL, NAME TEXT NOT NULL, TIME1 TEXT NOT NULL, TIME2 TEXT NOT NULL, TIME3 TEXT NOT NULL, TIME4 TEXT NOT NULL, TIME5 TEXT NOT NULL, T1 INTEGER NOT NULL, T2 INTEGER NOT NULL, T3 INTEGER NOT NULL, T4 INTEGER NOT NULL, T5 INTEGER NOT NULL, FK_APPOINTMENT_KEY INTEGER NOT NULL, FK_COURSE_KEY INTEGER NOT NULL ,FOREIGN KEY (FK_COURSE_KEY) REFERENCES Courses(FK_COURSE_KEY), FOREIGN KEY (FK_APPOINTMENT_KEY)  REFERENCES Appointments (APPOINTMENT_KEY))";
        String CREATE_REPORT = "CREATE TABLE " + TABLE_REPORTS + " ( REPORT_KEY INTEGER PRIMARY KEY AUTOINCREMENT, TYPE TEXT NOT NULL, REMARKS TEXT NOT NULL, URL TEXT NOT NULL, FK_APPOINTMENT_KEY INTEGER NOT NULL, FOREIGN KEY (FK_APPOINTMENT_KEY) REFERENCES Appointments(APPOINTMENT_KEY))";

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

    public Appointment getAppointment(int id) throws MalformedURLException
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(false,TABLE_APPOINTMENT,new String[]{"APPOINTMENT_KEY","DOCTOR","DATE","TIME","URL"},"APPOINTMENT_KEY=?",new String[]{String.valueOf(id)},null,null,null,null,null);
        if(cursor.getCount()>0)
        {
            cursor.moveToFirst();
            Appointment appointment = new Appointment(Integer.parseInt(cursor.getString(0)),cursor.getString(1),cursor.getString(2),cursor.getString(3),new URL(cursor.getString(4)));
            return appointment;
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

    public void insertCourse(Course course)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("FROM1",course.getFrom());
        values.put("TO1",course.getTo());
        values.put("INSTRUCTIONS",course.getInstructions());
        values.put("FK_APPOINTMENT_KEY",course.getAppointmentKey());
        db.insert(TABLE_COURSE,null,values);
        db.close();
    }

    public void insertMedicine(Medicine medicine)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("DOSES",medicine.getDoses());
        values.put("DOSES_TAKEN",medicine.getDoses_taken());
        values.put("NAME",medicine.getName());
        values.put("TIME1",medicine.getTime1());
        values.put("TIME2",medicine.getTime2());
        values.put("TIME3",medicine.getTime3());
        values.put("TIME4",medicine.getTime4());
        values.put("TIME5",medicine.getTime5());
        values.put("T1",medicine.getT1());
        values.put("T2",medicine.getT2());
        values.put("T3",medicine.getT3());
        values.put("T4",medicine.getT4());
        values.put("T5",medicine.getT5());
        values.put("FK_COURSE_KEY",medicine.getCourseKey());
        values.put("FK_APPOINTMENT_KEY",medicine.getAppKey());
        db.insert(TABLE_MEDICINES,null,values);
        db.close();
    }

    public void insertReport(Report report)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("TYPE",report.getType());
        values.put("REMARKS",report.getRemarks());
        values.put("URL",report.getUrl().toString());
        values.put("FK_APPOINTMENT_KEY",report.getAppointmentKey());
        db.insert(TABLE_REPORTS,null,values);
        db.close();
    }

    public void deleteAppointment(int id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_APPOINTMENT, "APPOINTMENT_KEY" + " = ?", new String[] { String.valueOf(id) });
        db.delete(TABLE_COURSE, "FK_APPOINTMENT_KEY" + " = ?", new String[] { String.valueOf(id) });
        db.delete(TABLE_MEDICINES, "FK_APPOINTMENT_KEY" + " = ?", new String[] { String.valueOf(id) });
        db.delete(TABLE_REPORTS, "FK_APPOINTMENT_KEY" + " = ?", new String[] { String.valueOf(id) });
        db.close();
    }

    public void deleteCourse(int id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_COURSE, "COURSE_KEY" + " = ?", new String[] { String.valueOf(id) });
        db.delete(TABLE_MEDICINES, "FK_COURSE_KEY" + " = ?", new String[] { String.valueOf(id) });
        db.close();
    }

    public void deleteMedicine(int id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_MEDICINES, "MEDICINE_KEY" + " = ?", new String[] { String.valueOf(id) });
        db.close();
    }

    public void deleteReport(int id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_REPORTS, "REPORT_KEY" + " = ?", new String[] { String.valueOf(id) });
        db.close();
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
        db.close();
    }

    public void updateCourse(Course course)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("FROM1",course.getFrom());
        values.put("TO1",course.getTo());
        values.put("INSTRUCTIONS",course.getInstructions());
        values.put("FK_APPOINTMENT_KEY",course.getAppointmentKey());
        db.update(TABLE_COURSE,values,"COURSE_KEY=?",new String[]{String.valueOf(course.getCourseKey())});
        db.close();
    }

    public void updateMedicine(Medicine medicine)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("DOSES",medicine.getDoses());
        values.put("DOSES_TAKEN",medicine.getDoses_taken());
        values.put("NAME",medicine.getName());
        values.put("TIME1",medicine.getTime1());
        values.put("TIME2",medicine.getTime2());
        values.put("TIME3",medicine.getTime3());
        values.put("TIME4",medicine.getTime4());
        values.put("TIME5",medicine.getTime5());
        values.put("T1",medicine.getT1());
        values.put("T2",medicine.getT2());
        values.put("T3",medicine.getT3());
        values.put("T4",medicine.getT4());
        values.put("T5",medicine.getT5());
        values.put("FK_COURSE_KEY",medicine.getCourseKey());
        values.put("FK_APPOINTMENT_KEY",medicine.getAppKey());
        db.update(TABLE_MEDICINES,values,"MEDICINE_KEY=?",new String[]{String.valueOf(medicine.getMedKey())});
        db.close();
    }

    public void updateReport(Report report)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("TYPE",report.getType());
        values.put("REMARKS",report.getRemarks());
        values.put("URL",report.getUrl().toString());
        values.put("FK_APPOINTMENT_KEY",report.getAppointmentKey());
        db.update(TABLE_REPORTS,values,"REPORT_KEY=?",new String[]{String.valueOf(report.getReportKey())});
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

    public ArrayList <Report> getAllReports() throws MalformedURLException
    {
        ArrayList <Report> arrayList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Reports", null);
        if (cursor.moveToFirst())
        {
            do
            {
                Report report = new Report(Integer.parseInt(cursor.getString(4)),Integer.parseInt(cursor.getString(0)),cursor.getString(1),cursor.getString(2),new URL(cursor.getString(3)));
                arrayList.add(report);
            } while (cursor.moveToNext());
        }
        Collections.reverse(arrayList);
        return arrayList;
    }

    public ArrayList <Report> getAllReports(int id) throws MalformedURLException
    {
        ArrayList <Report> arrayList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Reports WHERE FK_APPOINTMENT_KEY = " + String.valueOf(id), null);
        if (cursor.moveToFirst())
        {
            do
            {
                Report report = new Report(Integer.parseInt(cursor.getString(4)),Integer.parseInt(cursor.getString(0)),cursor.getString(1),cursor.getString(2),new URL(cursor.getString(3)));
                arrayList.add(report);
            } while (cursor.moveToNext());
        }
        Collections.reverse(arrayList);
        return arrayList;
    }

    public ArrayList <Course> getAllCourses() throws MalformedURLException
    {
        ArrayList <Course> arrayList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Courses", null);
        if (cursor.moveToFirst())
        {
            do
            {
                Course course = new Course(Integer.parseInt(cursor.getString(4)),Integer.parseInt(cursor.getString(0)),cursor.getString(1),cursor.getString(2),cursor.getString(3));
                arrayList.add(course);
            } while (cursor.moveToNext());
        }
        Collections.reverse(arrayList);
        return arrayList;
    }

    public ArrayList <Course> getAllCourses(int id) throws MalformedURLException
    {
        ArrayList <Course> arrayList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Courses WHERE FK_APPOINTMENT_KEY = " + String.valueOf(id), null);
        if (cursor.moveToFirst())
        {
            do
            {
                Course course = new Course(Integer.parseInt(cursor.getString(4)),Integer.parseInt(cursor.getString(0)),cursor.getString(1),cursor.getString(2),cursor.getString(3));
                arrayList.add(course);
            } while (cursor.moveToNext());
        }
        Collections.reverse(arrayList);
        return arrayList;
    }

}

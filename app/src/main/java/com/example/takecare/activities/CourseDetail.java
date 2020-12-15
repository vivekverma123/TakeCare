package com.example.takecare.activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.database.DatabaseHandler;
import com.example.model.Appointment;
import com.example.model.Course;
import com.example.takecare.R;

import java.net.MalformedURLException;
import java.util.Calendar;

public class CourseDetail extends AppCompatActivity
{
    private TextView t1,t2,t3,e1,e2;
    private EditText e3;
    private Button b1,b2;
    private DatabaseHandler db;
    private Context context;
    private final Calendar cal = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        setContentView(R.layout.activity_course_detail);

        try
        {
            init();
        } catch (MalformedURLException e)
        {
            e.printStackTrace();
        }

    }

    public void init() throws MalformedURLException
    {
        context = CourseDetail.this;
        db = new DatabaseHandler(context);

        t1 = findViewById(R.id.pres_id1);
        t2 = findViewById(R.id.course_id);
        t3 = findViewById(R.id.pres_by);
        e1 = findViewById(R.id.from);
        e2 = findViewById(R.id.to);
        e3 = findViewById(R.id.instructions);

        b1 = findViewById(R.id.sav_course);
        b2 = findViewById(R.id.del_course);

        Intent intent = getIntent();

        Course course = (Course) intent.getSerializableExtra("Object");

        Appointment appointment = db.getAppointment(course.getAppointmentKey());

        t1.setText(String.valueOf(course.getAppointmentKey()));
        if(course.getCourseKey()==-1)
        {
            t2.setText("Will be allotted once you save");
        }
        else
        {
            t2.setText(String.valueOf(course.getCourseKey()));
        }
        t3.setText(appointment.getDoctor());
        e1.setText(course.getFrom());
        e2.setText(course.getTo());
        e3.setText(course.getInstructions());

        e1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                openDatePickerDialog(v);
            }
        });

        e2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                openDatePickerDialog(v);
            }
        });

        b1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                course.setFrom(e1.getText().toString());
                course.setTo(e2.getText().toString());
                course.setInstructions(e3.getText().toString());
                if(course.getCourseKey()==-1)
                {
                    db.insertCourse(course);
                    onBackPressed();
                    Toast.makeText(context,"Saved Successfully",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    db.updateCourse(course);
                    Toast.makeText(context,"Updated Successfully",Toast.LENGTH_SHORT).show();
                }
            }
        });

        b2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(course.getCourseKey()==-1)
                {
                    onBackPressed();
                }
                else
                {
                    db.deleteCourse(course.getCourseKey());
                }
            }
        });

    }

    public void openDatePickerDialog(final View v)
    {
        DatePickerDialog datePickerDialog = new DatePickerDialog(context,
                (view, year, monthOfYear, dayOfMonth) ->
                {
                    String selectedDate = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                    ((TextView)v).setText(selectedDate);
                }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

}
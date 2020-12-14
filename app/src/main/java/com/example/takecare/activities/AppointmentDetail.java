package com.example.takecare.activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.database.DatabaseHandler;
import com.example.model.Appointment;
import com.example.takecare.R;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;

public class AppointmentDetail extends AppCompatActivity
{
    private EditText e2,e5;
    private TextView e1,e3,e4;
    private Button b1,b2,b3,b4;
    private Appointment appointment;
    private Context context;
    private DatabaseHandler db;
    private final Calendar cal = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        setContentView(R.layout.activity_appointment_detail);

        init();
    }

    private void init()
    {
        e1 = findViewById(R.id.app_id);
        e2 = findViewById(R.id.with_doctor);
        e3 = findViewById(R.id.on_date);
        e4 = findViewById(R.id.at_time);
        e5 = findViewById(R.id.pres_url1);
        b1 = findViewById(R.id.sav_appoint);
        b2 = findViewById(R.id.del_appoint);
        b3 = findViewById(R.id.add_course);
        b4 = findViewById(R.id.add_report);

        context = AppointmentDetail.this;

        db = new DatabaseHandler(context);

        Intent intent = getIntent();
        appointment = (Appointment) intent.getSerializableExtra("Object");

        e1.setText(String.valueOf(appointment.getAppointmentKey()));
        e2.setText(appointment.getDoctor());
        e3.setText(appointment.getDate());
        e4.setText(appointment.getTime());
        e5.setText(appointment.getUrl().toString());

        e3.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                openDatePickerDialog(v);
            }
        });

        e4.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                openTimePickerDialog(v);
            }
        });

        b1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                appointment.setDoctor(e2.getText().toString());
                appointment.setDate(e3.getText().toString());
                appointment.setTime(e4.getText().toString());
                try
                {
                    appointment.setUrl(new URL(e5.getText().toString()));
                } catch (MalformedURLException e)
                {
                    e.printStackTrace();
                }
                db.updateAppointment(appointment);
                Toast.makeText(context,"Saved Successfully",Toast.LENGTH_SHORT).show();
            }
        });

        b2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                db.deleteAppointment(appointment.getAppointmentKey());
                Toast.makeText(context,"Deleted Successfully",Toast.LENGTH_SHORT).show();
                onBackPressed();
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

    public void openTimePickerDialog(final View v)
    {
        TimePickerDialog timePickerDialog = new TimePickerDialog(context,
                (view, hourOfDay, minute) ->
                {
                    String selectedTime = hourOfDay + ":" + minute;
                    if(minute==0)
                    {
                        selectedTime = selectedTime + "0";
                    }
                    ((TextView)v).setText(selectedTime);

                },cal.get(Calendar.HOUR_OF_DAY),cal.get(Calendar.MINUTE),true);
        timePickerDialog.show();
    }

}
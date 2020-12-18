package com.example.takecare.activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.hardware.ConsumerIrManager;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.database.DatabaseHandler;
import com.example.model.Appointment;
import com.example.model.Course;
import com.example.model.Report;
import com.example.takecare.R;
import com.example.takecare.notifications.NotificationPublisher;
import com.google.android.material.tabs.TabLayout;

import java.net.Inet4Address;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class AppointmentDetail extends AppCompatActivity
{
    private EditText e2,e5;
    private TextView e1,e3,e4;
    private Button b1,b2,b3,b4;
    private Appointment appointment;
    private Context context;
    private DatabaseHandler db;
    private final Calendar cal = Calendar.getInstance();
    private Date date;
    private TabLayout tabLayout;
    private ListView listView;

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
                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                String temp = appointment.getDate() + " " + appointment.getTime();
                try
                {
                    Date d1 = formatter.parse(temp);
                    scheduleNotification(getNotification(appointment),d1,appointment.getAppointmentKey());
                } catch (ParseException e)
                {
                    e.printStackTrace();
                }
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

        b3.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Course course = new Course();
                course.setAppointmentKey(appointment.getAppointmentKey());
                Intent intent = new Intent(context,CourseDetail.class);
                intent.putExtra("Object",course);
                startActivity(intent);
            }
        });

        b4.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                try
                {
                    Report report = new Report();
                    report.setAppointmentKey(appointment.getAppointmentKey());
                    Intent intent = new Intent(context,ReportDetail.class);
                    intent.putExtra("Object",report);
                    startActivity(intent);

                } catch (MalformedURLException e)
                {
                    e.printStackTrace();
                }
            }
        });

        listView = findViewById(R.id.listView);
        tabLayout = findViewById(R.id.tabLayout2);


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
                    String hour = String.valueOf(hourOfDay),minute1 = String.valueOf(minute);
                    if(hourOfDay/10==0)
                    {
                        hour = "0" + hourOfDay;
                    }
                    if(minute/10==0)
                    {
                        minute1 = "0" + minute;
                    }
                    String selectedTime = hour + ":" + minute1;
                    ((TextView)v).setText(selectedTime);

                },cal.get(Calendar.HOUR_OF_DAY),cal.get(Calendar.MINUTE),true);
        timePickerDialog.show();
    }

    public void onResume()
    {
        tabLayout.getTabAt(0).select();
        try
        {
            ArrayList <Course> arrayList = db.getAllCourses(appointment.getAppointmentKey());
            ArrayAdapter arrayAdapter = new ArrayAdapter(context,R.layout.support_simple_spinner_dropdown_item,arrayList);
            listView.setAdapter(arrayAdapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
            {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                {
                    Intent intent = new Intent(context,CourseDetail.class);
                    intent.putExtra("Object",arrayList.get(position));
                    startActivity(intent);
                }
            });
        } catch (MalformedURLException e)
        {
            e.printStackTrace();
        }

        super.onResume();
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener()
        {
            @Override
            public void onTabSelected(TabLayout.Tab tab)
            {
                switch(tab.getPosition())
                {
                    case 1:
                        try
                        {
                            ArrayList <Report> arrayList = db.getAllReports(appointment.getAppointmentKey());
                            ArrayAdapter arrayAdapter = new ArrayAdapter(context,R.layout.support_simple_spinner_dropdown_item,arrayList);
                            listView.setAdapter(arrayAdapter);
                            listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
                            {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                                {
                                    Intent intent = new Intent(context,ReportDetail.class);
                                    intent.putExtra("Object",arrayList.get(position));
                                    startActivity(intent);
                                }
                            });
                        } catch (MalformedURLException e)
                        {
                            e.printStackTrace();
                        }
                        break;

                    case 0:
                        try
                        {
                            ArrayList <Course> arrayList = db.getAllCourses(appointment.getAppointmentKey());
                            ArrayAdapter arrayAdapter = new ArrayAdapter(context,R.layout.support_simple_spinner_dropdown_item,arrayList);
                            listView.setAdapter(arrayAdapter);
                            listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
                            {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                                {
                                    Intent intent = new Intent(context,CourseDetail.class);
                                    intent.putExtra("Object",arrayList.get(position));
                                    startActivity(intent);
                                }
                            });
                        } catch (MalformedURLException e)
                        {
                            e.printStackTrace();
                        }
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab)
            {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab)
            {
                switch(tab.getPosition())
                {
                    case 1:
                        try
                        {
                            ArrayList <Report> arrayList = db.getAllReports(appointment.getAppointmentKey());
                            ArrayAdapter arrayAdapter = new ArrayAdapter(context,R.layout.support_simple_spinner_dropdown_item,arrayList);
                            listView.setAdapter(arrayAdapter);
                            listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
                            {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                                {
                                    Intent intent = new Intent(context,ReportDetail.class);
                                    intent.putExtra("Object",arrayList.get(position));
                                    startActivity(intent);
                                }
                            });
                        } catch (MalformedURLException e)
                        {
                            e.printStackTrace();
                        }
                        break;

                    case 0:
                        try
                        {
                            ArrayList <Course> arrayList = db.getAllCourses(appointment.getAppointmentKey());
                            ArrayAdapter arrayAdapter = new ArrayAdapter(context,R.layout.support_simple_spinner_dropdown_item,arrayList);
                            listView.setAdapter(arrayAdapter);
                            listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
                            {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                                {
                                    Intent intent = new Intent(context,CourseDetail.class);
                                    intent.putExtra("Object",arrayList.get(position));
                                    startActivity(intent);
                                }
                            });
                        } catch (MalformedURLException e)
                        {
                            e.printStackTrace();
                        }
                }
            }
        });
    }

    private void scheduleNotification (Notification notification , Date d1, int id)
    {
        Intent notificationIntent = new Intent( context, NotificationPublisher.class ) ;
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION_ID , appointment.getAppointmentKey()) ;
        notificationIntent.putExtra(NotificationPublisher. NOTIFICATION , notification) ;
        PendingIntent pendingIntent = PendingIntent. getBroadcast ( context, appointment.getAppointmentKey() , notificationIntent , PendingIntent.FLAG_UPDATE_CURRENT) ;
        Date d2 = new Date();
        long futureInMillis = SystemClock.elapsedRealtime () + d1.getTime() - d2.getTime();
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context. ALARM_SERVICE ) ;
        assert alarmManager != null;
        alarmManager.set(AlarmManager. ELAPSED_REALTIME_WAKEUP , futureInMillis , pendingIntent) ;
    }

    private Notification getNotification (Appointment appointment)
    {
        NotificationCompat.Builder builder = new NotificationCompat.Builder( context,"Health") ;
        builder.setContentTitle("Appointment Alert!!!" ) ;
        builder.setContentText("Your appointment with " + appointment.getDoctor() + " is scheduled at " + appointment.getTime()) ;
        builder.setSmallIcon(R.drawable. ic_launcher_foreground ) ;
        //builder.setAutoCancel( true ) ;
        builder.setChannelId("Health");
        return builder.build() ;
    }

}
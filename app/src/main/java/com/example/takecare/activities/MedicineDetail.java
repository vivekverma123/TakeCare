package com.example.takecare.activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.database.DatabaseHandler;
import com.example.model.Appointment;
import com.example.model.Course;
import com.example.model.Medicine;
import com.example.takecare.R;
import com.example.takecare.notifications.NotificationPublisher;

import java.net.MalformedURLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import static com.example.takecare.utilities.Utilities.getDate;
import static com.example.takecare.utilities.Utilities.getDatetoString;

public class MedicineDetail extends AppCompatActivity
{
    private TextView t1,t2,t3,t6,t7,t8,t9,t10;
    private EditText e4,e5,e6;
    private Appointment appointment;
    private Medicine medicine;
    private Course course;
    private Context context;
    private DatabaseHandler db;
    private CheckBox c1,c2,c3,c4,c5,c6;
    private Button b1,b2;
    private final Calendar cal = Calendar.getInstance();
    private HashMap<TextView,CheckBox> hashMap;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        setContentView(R.layout.activity_medicine_detail);
        try
        {
            init();
        } catch (MalformedURLException e)
        {
            e.printStackTrace();
        }
    }

    private void init() throws MalformedURLException
    {
        context = MedicineDetail.this;
        db = new DatabaseHandler(context);
        Intent intent = getIntent();
        medicine = (Medicine)intent.getSerializableExtra("Object");
        appointment = db.getAppointment(medicine.getAppKey());
        course = db.getCourse(medicine.getCourseKey());
        appointment = db.getAppointment(medicine.getAppKey());
        t1 = findViewById(R.id.course);
        t2 = findViewById(R.id.med);
        t3 = findViewById(R.id.doc);
        e4 = findViewById(R.id.med_name);
        e5 = findViewById(R.id.doses_no);
        e6 = findViewById(R.id.notif_doses);

        t6 = findViewById(R.id.dose_time1);
        t7 = findViewById(R.id.dose_time2);
        t8 = findViewById(R.id.dose_time3);
        t9 = findViewById(R.id.dose_time4);
        t10 = findViewById(R.id.dose_time5);

        c1 = findViewById(R.id.dose1);
        c2 = findViewById(R.id.dose2);
        c3 = findViewById(R.id.dose3);
        c4 = findViewById(R.id.dose4);
        c5 = findViewById(R.id.dose5);
        c6 = findViewById(R.id.create_notif);

        b1 = findViewById(R.id.del_med);
        b2 = findViewById(R.id.sav_med);

        hashMap = new HashMap<>();
        hashMap.put(t6,c1);
        hashMap.put(t7,c2);
        hashMap.put(t8,c3);
        hashMap.put(t9,c4);
        hashMap.put(t10,c5);

        t1.setText(String.valueOf(medicine.getCourseKey()));
        if(medicine.getMedKey()!=-1)
        {
            t2.setText(String.valueOf(medicine.getMedKey()));
        }
        else
        {
            t2.setText("Will be allotted once your save");
        }
        t3.setText(appointment.getDoctor());
        e4.setText(medicine.getName());
        e5.setText(String.valueOf(medicine.getDoses()));
        t6.setText(String.valueOf(medicine.getTime1()));
        t7.setText(String.valueOf(medicine.getTime2()));
        t8.setText(String.valueOf(medicine.getTime3()));
        t9.setText(String.valueOf(medicine.getTime4()));
        t10.setText(String.valueOf(medicine.getTime5()));

        if(medicine.getT1()==0)
        {
            c1.setChecked(false);
        }
        else
        {
            c1.setChecked(true);
        }

        if(medicine.getT2()==0)
        {
            c2.setChecked(false);
        }
        else
        {
            c2.setChecked(true);
        }

        if(medicine.getT3()==0)
        {
            c3.setChecked(false);
        }
        else
        {
            c3.setChecked(true);
        }

        if(medicine.getT4()==0)
        {
            c4.setChecked(false);
        }
        else
        {
            c4.setChecked(true);
        }

        if(medicine.getT5()==0)
        {
            c5.setChecked(false);
        }
        else
        {
            c5.setChecked(true);
        }

        b1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(medicine.getMedKey()==-1)
                {
                    onBackPressed();
                }
                else
                {
                    db.deleteMedicine(medicine.getMedKey());
                    onBackPressed();
                }
            }
        });

        b2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                medicine.setName(e4.getText().toString());
                medicine.setDoses(Integer.parseInt(e5.getText().toString()));
                medicine.setT1(c1.isChecked() ? 1 : 0);
                medicine.setT2(c2.isChecked() ? 1 : 0);
                medicine.setT3(c3.isChecked() ? 1 : 0);
                medicine.setT4(c4.isChecked() ? 1 : 0);
                medicine.setT5(c5.isChecked() ? 1 : 0);
                if(c1.isChecked())
                {
                    medicine.setTime1(t6.getText().toString());
                }
                else
                {
                    medicine.setTime1("Not Set");
                }
                if(c2.isChecked())
                {
                    medicine.setTime2(t7.getText().toString());
                }
                else
                {
                    medicine.setTime2("Not Set");
                }
                if(c3.isChecked())
                {
                    medicine.setTime3(t8.getText().toString());
                }
                else
                {
                    medicine.setTime3("Not Set");
                }
                if(c4.isChecked())
                {
                    medicine.setTime4(t9.getText().toString());
                }
                else
                {
                    medicine.setTime4("Not Set");
                }
                if(c5.isChecked())
                {
                    medicine.setTime5(t10.getText().toString());
                }
                else
                {
                    medicine.setTime5("Not Set");
                }
                if(medicine.getMedKey()==-1)
                {
                    db.insertMedicine(medicine);
                    Toast.makeText(context,"Saved Successfully",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    db.updateMedicine(medicine);
                    Toast.makeText(context,"Updated Successfully",Toast.LENGTH_SHORT).show();
                }
                try {
                    createNotifications();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        t6.setOnClickListener(listner);
        t7.setOnClickListener(listner);
        t8.setOnClickListener(listner);
        t9.setOnClickListener(listner);
        t10.setOnClickListener(listner);
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

                    int id = ((TextView)v).getId();
                    switch(id)
                    {
                        case R.id.dose_time1:
                            t6.setText(selectedTime);
                            break;
                        case R.id.dose_time2:
                            t7.setText(selectedTime);
                            break;
                        case R.id.dose_time3:
                            t8.setText(selectedTime);
                            break;
                        case R.id.dose_time4:
                            t9.setText(selectedTime);
                            break;
                        case R.id.dose_time5:
                            t10.setText(selectedTime);
                            break;
                    }
                },cal.get(Calendar.HOUR_OF_DAY),cal.get(Calendar.MINUTE),true);
        timePickerDialog.show();
    }

    private final View.OnClickListener listner = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            if(hashMap.get((TextView)v).isChecked())
            {
                openTimePickerDialog(v);
            }
        }
    };

    private void createNotifications() throws Exception
    {
        ArrayList <String>  arrayList = new ArrayList<>();
        if(c1.isChecked())
        {
            arrayList.add(t6.getText().toString());
        }
        if(c2.isChecked())
        {
            arrayList.add(t7.getText().toString());
        }
        if(c3.isChecked())
        {
            arrayList.add(t8.getText().toString());
        }
        if(c4.isChecked())
        {
            arrayList.add(t9.getText().toString());
        }
        if(c5.isChecked())
        {
            arrayList.add(t10.getText().toString());
        }
        int doses = Integer.parseInt(e6.getText().toString());
        int i = 0,j;
        String date = getDatetoString();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy");

        Date date1 = new Date();
        Date date3 = getDate();

        for(i=0;i<arrayList.size();++i)
        {
            String temp = date + " " + arrayList.get(i);
            Date date2 = sdf.parse(temp);
            if(date2.getTime()>date1.getTime())
            {
                break;
            }
        }

        if(i==arrayList.size())
        {
            i = 0;
            date3.setTime(date3.getTime() + 24*60*60*1000);
            date = sdf1.format(date3);
        }


        ArrayList <Date> arrayList1 = new ArrayList<>();
        for(j=0;j<doses;++j)
        {
            Date date2 = sdf.parse(date + " " + arrayList.get(i));

            arrayList1.add(date2);
            i = i + 1;
            if(i==arrayList.size())
            {
                i = 0;
                date3.setTime(date3.getTime() + 24*60*60*1000);
                date = sdf1.format(date3);
            }
        }

        for(i=0;i<arrayList1.size();++i)
        {
            int id = Integer.parseInt("2" + String.valueOf(medicine.getMedKey()*10 +  i));
            scheduleNotification(getNotification(medicine),arrayList1.get(i),id);
        }

    }

    private void scheduleNotification (Notification notification , Date d1, int id)
    {
        Intent notificationIntent = new Intent( context, NotificationPublisher.class ) ;
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION_ID , id) ;
        notificationIntent.putExtra(NotificationPublisher. NOTIFICATION , notification) ;
        PendingIntent pendingIntent = PendingIntent. getBroadcast ( context, id , notificationIntent , PendingIntent.FLAG_UPDATE_CURRENT) ;
        Date d2 = new Date();
        long futureInMillis = SystemClock.elapsedRealtime () + d1.getTime() - d2.getTime();
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context. ALARM_SERVICE ) ;
        assert alarmManager != null;
        alarmManager.set(AlarmManager. ELAPSED_REALTIME_WAKEUP , futureInMillis , pendingIntent) ;
    }

    private Notification getNotification (Medicine medicine)
    {
        NotificationCompat.Builder builder = new NotificationCompat.Builder( context,"Health") ;
        builder.setContentTitle("Take your Medicine" ) ;
        builder.setContentText("It's time for your dose of " + medicine.getName()) ;
        builder.setSmallIcon(R.drawable. ic_launcher_foreground ) ;
        //builder.setAutoCancel( true ) ;
        builder.setChannelId("Health");
        builder.setGroup("Medicine Notifications");
        return builder.build() ;
    }

}
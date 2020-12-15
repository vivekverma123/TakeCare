package com.example.takecare.activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

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
import com.example.model.Report;
import com.example.takecare.R;

import java.net.MalformedURLException;
import java.net.URL;

public class ReportDetail extends AppCompatActivity
{
    private TextView t1,t2,t3;
    private EditText e1,e2,e3;
    private Appointment appointment;
    private Report report;
    private DatabaseHandler db;
    private Context context;
    private Button b1,b2;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        setContentView(R.layout.activity_report);

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
        context = ReportDetail.this;
        db = new DatabaseHandler(context);
        Intent intent = getIntent();
        report = (Report)intent.getSerializableExtra("Object");
        appointment = db.getAppointment(report.getAppointmentKey());
        t1 = findViewById(R.id.pres_id);
        t2 = findViewById(R.id.repo_id);
        t3 = findViewById(R.id.refer_by);
        e1 = findViewById(R.id.test);
        e2 = findViewById(R.id.remarks);
        e3 = findViewById(R.id.repo_url);

        t1.setText(String.valueOf(appointment.getAppointmentKey()));
        if(report.getReportKey()==-1)
        {
            t2.setText("Will be allotted once you save");
        }
        else
        {
            t2.setText(String.valueOf(report.getReportKey()));
        }
        t3.setText(appointment.getDoctor());
        e1.setText(report.getType());
        e2.setText(report.getRemarks());
        e3.setText(report.getUrl().toString());

        b1 = findViewById(R.id.sav_repo);
        b2 = findViewById(R.id.del_repo);

        b1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                report.setType(e1.getText().toString());
                report.setRemarks(e2.getText().toString());
                try
                {
                    report.setUrl(new URL(e3.getText().toString()));
                } catch (MalformedURLException e)
                {
                    e.printStackTrace();
                }
                if(report.getReportKey()==-1)
                {
                    db.insertReport(report);
                    onBackPressed();
                }
                else
                {
                    db.updateReport(report);
                }
                Toast.makeText(context,"Saved Successfully",Toast.LENGTH_SHORT).show();
            }
        });

        b2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(report.getReportKey()!=-1)
                {
                    db.deleteReport(report.getReportKey());
                }
                onBackPressed();
            }
        });

    }

}
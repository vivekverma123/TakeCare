package com.example.takecare;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.database.DatabaseHandler;
import com.example.model.Person;

import java.util.ArrayList;
import java.util.Calendar;

public class Profile extends AppCompatActivity
{
    EditText e1,e3,e4;
    TextView e2;
    Spinner s1;
    Button b1;
    Context context;
    final Calendar cal = Calendar.getInstance();
    ArrayList<String> arrayList;
    DatabaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        setContentView(R.layout.activity_profile);

        init();
    }

    void init()
    {
        e1 = findViewById(R.id.name);
        e2 = findViewById(R.id.dob);
        s1 = findViewById(R.id.sex);
        b1 = findViewById(R.id.save);
        e3 = findViewById(R.id.height);
        e4 = findViewById(R.id.weight);
        b1 = findViewById(R.id.save);

        context = Profile.this;
        arrayList = new ArrayList<>();

        arrayList.add("Male");
        arrayList.add("Female");
        arrayList.add("Other");

        ArrayAdapter arrayAdapter = new ArrayAdapter(context,R.layout.spinner_layout,arrayList);
        s1.setAdapter(arrayAdapter);

        e2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                openDatePickerDialog(v);
            }
        });

        s1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                s1.setSelection(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {
                s1.setSelection(2);
            }
        });

        db = new DatabaseHandler(context);

        Person person = db.getPerson();
        if(person==null)
        {
            e1.setText("Name not Set");
            e2.setText("01/01/1970");
            s1.setSelection(0);
            e3.setText("0");
            e4.setText("0");
        }
        else
        {
            e1.setText(person.getName());
            e2.setText(person.getDob());
            s1.setSelection(arrayAdapter.getPosition(person.getSex()));
            e3.setText(String.valueOf(person.getHeight()));
            e4.setText(String.valueOf(person.getWeight()));
        }

        b1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Person person = new Person(1,e1.getText().toString(),e2.getText().toString(),s1.getSelectedItem().toString(),Double.parseDouble(e3.getText().toString()),Double.parseDouble(e4.getText().toString()));
                db.updatePerson(person);
                Toast.makeText(context,"Profile Updated Successfully",Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void openDatePickerDialog(final View v)
    {
        DatePickerDialog datePickerDialog = new DatePickerDialog(context,
                (view, year, monthOfYear, dayOfMonth) ->
                {
                    String selectedDate = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                    switch (v.getId())
                    {
                        case R.id.dob:
                            ((TextView)v).setText(selectedDate);
                            break;
                    }
                }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.getDatePicker().setMaxDate(cal.getTimeInMillis());
        datePickerDialog.show();
    }
}
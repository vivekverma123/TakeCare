package com.example.takecare.useradapters;

import android.app.admin.FactoryResetProtectionPolicy;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.database.DatabaseHandler;
import com.example.model.Appointment;
import com.example.model.Course;
import com.example.model.Report;
import com.example.takecare.R;
import com.example.takecare.activities.AppointmentDetail;
import com.example.takecare.activities.CourseDetail;
import com.example.takecare.activities.ReportDetail;
import com.example.takecare.fragments.reports.Reports;

import java.net.MalformedURLException;
import java.util.ArrayList;

public class CoursesAdapter extends RecyclerView.Adapter <CoursesAdapter.MyAdapter>
{
    private ArrayList<Course> arrayList;
    private Context context;
    DatabaseHandler db;

    public CoursesAdapter(ArrayList<Course> arrayList, Context context)
    {
        this.arrayList = arrayList;
        this.context = context;
        db = new DatabaseHandler(context);
    }

    @NonNull
    @Override
    public MyAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.recycler_layout, parent, false);
        return new MyAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter holder, int position)
    {
        final Course course = arrayList.get(position);
        try
        {
            holder.SetData(course);
        } catch (MalformedURLException e)
        {
            e.printStackTrace();
        }

        // You can set click listners to indvidual items in the viewholder here
        // make sure you pass down the listner or make the Data members of the viewHolder public
    }

    @Override
    public int getItemCount()
    {
        return arrayList.size();
    }

    public class MyAdapter extends RecyclerView.ViewHolder
    {

        private TextView t1,t2,t3,t4,t5;
        private Button b1,b2;

        public MyAdapter(@NonNull View itemView)
        {
            super(itemView);
            t1 = itemView.findViewById(R.id.id1);
            t2 = itemView.findViewById(R.id.doctor);
            t3 = itemView.findViewById(R.id.date);
            t4 = itemView.findViewById(R.id.time);
            t5 = itemView.findViewById(R.id.spare);
            b1 = itemView.findViewById(R.id.view1);
            b2 = itemView.findViewById(R.id.view2);
        }

        public void SetData(Course course) throws MalformedURLException
        {
            t1.setText("ID: " + course.getCourseKey());
            Appointment appointment = db.getAppointment(course.getAppointmentKey());
            t2.setText("Prescribed by " + appointment.getDoctor());
            t3.setText("From: " + course.getFrom());
            t4.setText("To: \n" + course.getTo());
            t5.setVisibility(View.GONE);

            b1.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    Intent intent = new Intent(context, CourseDetail.class);
                    intent.putExtra("Object",course);
                    context.startActivity(intent);
                }
            });

            b2.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(appointment.getUrl().toString()));
                    context.startActivity(browserIntent);
                }
            });

        }
    }

}

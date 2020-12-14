package com.example.takecare.useradapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.model.Appointment;
import com.example.takecare.R;
import com.example.takecare.activities.AppointmentDetail;

import java.util.ArrayList;

public class AppointmentsAdapter extends RecyclerView.Adapter <AppointmentsAdapter.MyAdapter>
{
    private ArrayList<Appointment> arrayList;
    private Context context;

    public AppointmentsAdapter(ArrayList<Appointment> arrayList, Context context)
    {
        this.arrayList = arrayList;
        this.context = context;
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
        final Appointment appointment = arrayList.get(position);
        holder.SetData(appointment);

        // You can set click listners to indvidual items in the viewholder here
        // make sure you pass down the listner or make the Data members of the viewHolder public
    }

    @Override
    public int getItemCount()
    {
        return arrayList.size();
    }

    public class MyAdapter extends RecyclerView.ViewHolder {

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

        public void SetData(Appointment appointment)
        {
            t1.setText("ID: " + appointment.getAppointmentKey());
            t2.setText("With " + appointment.getDoctor());
            t3.setText("On " + appointment.getDate());
            t4.setText("At " + appointment.getTime());
            t5.setVisibility(View.GONE);

            b1.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    Intent intent = new Intent(context,AppointmentDetail.class);
                    intent.putExtra("Object",appointment);
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

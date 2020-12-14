package com.example.takecare.fragments.appointments;

import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.database.DatabaseHandler;
import com.example.model.Appointment;
import com.example.takecare.R;
import com.example.takecare.activities.AppointmentDetail;
import com.example.takecare.useradapters.AppointmentsAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.net.MalformedURLException;
import java.util.ArrayList;

public class Appointments extends Fragment
{

    private AppointmentsViewModel mViewModel;
    private FloatingActionButton floatingActionButton;
    private RecyclerView recyclerView;
    private ArrayList<Appointment> arrayList;
    private DatabaseHandler db;
    private View view;

    public static Appointments newInstance()
    {
        return new Appointments();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState)
    {

        view =  inflater.inflate(R.layout.appointments_fragment, container, false);
        db = new DatabaseHandler(getActivity());
        arrayList = new ArrayList<>();
        try
        {
            arrayList = db.getAllAppointments();
        } catch (MalformedURLException e)
        {
            e.printStackTrace();
        }
        floatingActionButton = view.findViewById(R.id.floatingActionButton);
        recyclerView = view.findViewById(R.id.recyclerView);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        AppointmentsAdapter appointmentsAdapter = new AppointmentsAdapter(arrayList,getActivity());
        recyclerView.setAdapter(appointmentsAdapter);
        appointmentsAdapter.notifyDataSetChanged();

        floatingActionButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Appointment appointment = null;
                try
                {
                    appointment = new Appointment();
                    db.insertAppointment(appointment);
                } catch (MalformedURLException e)
                {
                    e.printStackTrace();
                }

                try
                {
                    arrayList = db.getAllAppointments();
                } catch (MalformedURLException e)
                {
                    e.printStackTrace();
                }
                AppointmentsAdapter appointmentsAdapter = new AppointmentsAdapter(arrayList,getActivity());
                recyclerView.setAdapter(appointmentsAdapter);
            }



        });
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(AppointmentsViewModel.class);
        // TODO: Use the ViewModel
    }

    public void onResume()
    {
        super.onResume();
        try
        {
            arrayList = db.getAllAppointments();
        } catch (MalformedURLException e)
        {
            e.printStackTrace();
        }
        floatingActionButton = view.findViewById(R.id.floatingActionButton);
        recyclerView = view.findViewById(R.id.recyclerView);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        AppointmentsAdapter appointmentsAdapter = new AppointmentsAdapter(arrayList,getActivity());
        recyclerView.setAdapter(appointmentsAdapter);
        appointmentsAdapter.notifyDataSetChanged();
    }

}
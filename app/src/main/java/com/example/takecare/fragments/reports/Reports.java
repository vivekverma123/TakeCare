package com.example.takecare.fragments.reports;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.example.database.DatabaseHandler;
import com.example.model.Report;
import com.example.takecare.R;
import com.example.takecare.useradapters.AppointmentsAdapter;
import com.example.takecare.useradapters.ReportsAdapter;

import java.net.MalformedURLException;
import java.util.ArrayList;

public class Reports extends Fragment
{
    private ReportsViewModel mViewModel;
    private DatabaseHandler db;
    private RecyclerView recyclerView;
    private View view;
    private ArrayList <Report> arrayList;

    public static Reports newInstance()
    {
        return new Reports();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState)
    {
        View view =  inflater.inflate(R.layout.reports_fragment, container, false);
        db = new DatabaseHandler(getActivity());
        arrayList = new ArrayList<>();
        try
        {
            arrayList = db.getAllReports();
        } catch (MalformedURLException e)
        {
            e.printStackTrace();
        }
        recyclerView = view.findViewById(R.id.recycler3);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        ReportsAdapter appointmentsAdapter = new ReportsAdapter(arrayList,getActivity());
        recyclerView.setAdapter(appointmentsAdapter);
        appointmentsAdapter.notifyDataSetChanged();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ReportsViewModel.class);
        // TODO: Use the ViewModel
    }

    @Override
    public void onResume()
    {
        super.onResume();
        try
        {
            arrayList = db.getAllReports();
        } catch (MalformedURLException e)
        {
            e.printStackTrace();
        }
        ReportsAdapter appointmentsAdapter = new ReportsAdapter(arrayList,getActivity());
        recyclerView.setAdapter(appointmentsAdapter);
    }
}
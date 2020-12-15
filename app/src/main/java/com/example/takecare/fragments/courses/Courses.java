package com.example.takecare.fragments.courses;

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

import com.example.database.DatabaseHandler;
import com.example.model.Course;
import com.example.model.Report;
import com.example.takecare.R;
import com.example.takecare.useradapters.CoursesAdapter;
import com.example.takecare.useradapters.ReportsAdapter;

import java.net.MalformedURLException;
import java.util.ArrayList;

public class Courses extends Fragment
{

    private CoursesViewModel mViewModel;
    private DatabaseHandler db;
    private RecyclerView recyclerView;
    private View view;
    private ArrayList<Course> arrayList;

    public static Courses newInstance()
    {
        return new Courses();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState)
    {
        view =  inflater.inflate(R.layout.courses_fragment, container, false);
        db = new DatabaseHandler(getActivity());
        arrayList = new ArrayList<>();
        try
        {
            arrayList = db.getAllCourses();
        } catch (MalformedURLException e)
        {
            e.printStackTrace();
        }
        recyclerView = view.findViewById(R.id.recycler4);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        CoursesAdapter courseAdapter = new CoursesAdapter(arrayList,getActivity());
        recyclerView.setAdapter(courseAdapter);
        courseAdapter.notifyDataSetChanged();

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(CoursesViewModel.class);
        // TODO: Use the ViewModel
    }

}
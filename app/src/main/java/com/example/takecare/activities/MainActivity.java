package com.example.takecare.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.takecare.R;
import com.example.takecare.fragments.appointments.Appointments;
import com.example.takecare.fragments.courses.Courses;
import com.example.takecare.fragments.reports.Reports;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity
{
    private Context context;
    private TabLayout tabLayout;
    private FrameLayout frameLayout;
    private Fragment fragment;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init()
    {
        context = MainActivity.this;

        tabLayout = findViewById(R.id.tabLayout);
        frameLayout = findViewById(R.id.frame);

        fragment = new Appointments();
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.replace(R.id.frame, fragment);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.commit();

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener()
        {
            @Override
            public void onTabSelected(TabLayout.Tab tab)
            {
                // Fragment fragment = null;
                switch (tab.getPosition())
                {
                    case 0:
                        fragment = new Appointments();
                        break;
                    case 1:
                        fragment = new Courses();
                        break;
                    case 2:
                        fragment = new Reports();
                        break;
                }
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.frame, fragment);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft.commit();

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab)
            {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab)
            {
                switch (tab.getPosition())
                {
                    case 0:
                        fragment = new Appointments();
                        break;
                    case 1:
                        fragment = new Courses();
                        break;
                    case 2:
                        fragment = new Reports();
                        break;
                }
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.frame, fragment);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft.commit();
            }
        });
    }

            public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        switch (id)
        {
            case R.id.profile:
                startActivity(new Intent(context, Profile.class));
                break;

            case R.id.preferences:
                Toast.makeText(context, "Services are going to start shortly", Toast.LENGTH_SHORT).show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
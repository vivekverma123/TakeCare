package com.example.model;

import java.net.MalformedURLException;
import java.net.URL;

public class Appointment
{
    private int appointmentKey;
    private String doctor,date,time;
    private URL url;

    public Appointment() throws MalformedURLException
    {
        appointmentKey = 1;
        doctor = "Anonymous";
        date = "Not decided";
        time = "Not decided";
        url = new URL("https://i.kym-cdn.com/entries/icons/original/000/032/279/Screen_Shot_2019-12-30_at_11.26.24_AM.png");
    }

    public Appointment(int appointmentKey, String doctor, String date, String time, URL url)
    {
        this.appointmentKey = appointmentKey;
        this.doctor = doctor;
        this.date = date;
        this.time = time;
        this.url = url;
    }

    public int getAppointmentKey()
    {
        return appointmentKey;
    }

    public void setAppointmentKey(int appointmentKey)
    {
        this.appointmentKey = appointmentKey;
    }

    public String getDoctor()
    {
        return doctor;
    }

    public void setDoctor(String doctor)
    {
        this.doctor = doctor;
    }

    public String getDate()
    {
        return date;
    }

    public void setDate(String date)
    {
        this.date = date;
    }

    public String getTime()
    {
        return time;
    }

    public void setTime(String time)
    {
        this.time = time;
    }

    public URL getUrl()
    {
        return url;
    }

    public void setUrl(URL url)
    {
        this.url = url;
    }
}

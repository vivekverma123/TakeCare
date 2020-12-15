package com.example.model;

import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;

public class Report implements Serializable
{
    private int appointmentKey,reportKey;
    private String type,remarks;
    private URL url;

    public Report() throws MalformedURLException
    {
        reportKey = appointmentKey = -1;
        type = "Not Set";
        remarks = "Not Set";
        url = new URL("https://64.media.tumblr.com/54bc62d2af0f7cbe1be80937105812d4/tumblr_nnu788Mpdf1rkembbo1_500.png");
    }

    public Report(int appointmentKey, int reportKey, String type, String remarks, URL url)
    {
        this.appointmentKey = appointmentKey;
        this.reportKey = reportKey;
        this.type = type;
        this.remarks = remarks;
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

    public int getReportKey()
    {
        return reportKey;
    }

    public void setReportKey(int reportKey)
    {
        this.reportKey = reportKey;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public String getRemarks()
    {
        return remarks;
    }

    public void setRemarks(String remarks)
    {
        this.remarks = remarks;
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

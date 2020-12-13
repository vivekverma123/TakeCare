package com.example.model;

import java.net.URL;

public class Reports
{
    private int appointmentKey,reportKey;
    private String referredBy,type,remarks;
    private URL url;

    public Reports()
    {
    }

    public Reports(int appointmentKey, int reportKey, String referredBy, String type, String remarks, URL url)
    {
        this.appointmentKey = appointmentKey;
        this.reportKey = reportKey;
        this.referredBy = referredBy;
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

    public String getReferredBy()
    {
        return referredBy;
    }

    public void setReferredBy(String referredBy)
    {
        this.referredBy = referredBy;
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

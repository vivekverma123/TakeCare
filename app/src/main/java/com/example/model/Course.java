package com.example.model;

public class Course
{
    private int appointmentKey,courseKey;
    private String prescribedBy,from,to,instructions;

    public Course()
    {
    }

    public Course(int appointmentKey, int courseKey, String prescribedBy, String from, String to, String instructions)
    {
        this.appointmentKey = appointmentKey;
        this.courseKey = courseKey;
        this.prescribedBy = prescribedBy;
        this.from = from;
        this.to = to;
        this.instructions = instructions;
    }

    public int getAppointmentKey()
    {
        return appointmentKey;
    }

    public void setAppointmentKey(int appointmentKey)
    {
        this.appointmentKey = appointmentKey;
    }

    public int getCourseKey()
    {
        return courseKey;
    }

    public void setCourseKey(int courseKey)
    {
        this.courseKey = courseKey;
    }

    public String getPrescribedBy()
    {
        return prescribedBy;
    }

    public void setPrescribedBy(String prescribedBy)
    {
        this.prescribedBy = prescribedBy;
    }

    public String getFrom()
    {
        return from;
    }

    public void setFrom(String from)
    {
        this.from = from;
    }

    public String getTo()
    {
        return to;
    }

    public void setTo(String to)
    {
        this.to = to;
    }

    public String getInstructions()
    {
        return instructions;
    }

    public void setInstructions(String instructions)
    {
        this.instructions = instructions;
    }
}

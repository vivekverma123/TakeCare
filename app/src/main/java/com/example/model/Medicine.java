package com.example.model;

public class Medicine
{
    private int appKey,medKey,courseKey,doses,doses_taken;
    private String Name,time1,time2,time3,time4,time5,instructions;
    private int t1,t2,t3,t4,t5;

    public Medicine()
    {
    }

    public Medicine(int appKey, int medKey, int courseKey, int doses, int doses_taken, String name, String time1, String time2, String time3, String time4, String time5, String instructions, int t1, int t2, int t3, int t4, int t5)
    {
        this.appKey = appKey;
        this.medKey = medKey;
        this.courseKey = courseKey;
        this.doses = doses;
        this.doses_taken = doses_taken;
        Name = name;
        this.time1 = time1;
        this.time2 = time2;
        this.time3 = time3;
        this.time4 = time4;
        this.time5 = time5;
        this.instructions = instructions;
        this.t1 = t1;
        this.t2 = t2;
        this.t3 = t3;
        this.t4 = t4;
        this.t5 = t5;
    }

    public int getAppKey()
    {
        return appKey;
    }

    public void setAppKey(int appKey)
    {
        this.appKey = appKey;
    }

    public int getMedKey()
    {
        return medKey;
    }

    public void setMedKey(int medKey)
    {
        this.medKey = medKey;
    }

    public int getCourseKey()
    {
        return courseKey;
    }

    public void setCourseKey(int courseKey)
    {
        this.courseKey = courseKey;
    }

    public int getDoses()
    {
        return doses;
    }

    public void setDoses(int doses)
    {
        this.doses = doses;
    }

    public int getDoses_taken()
    {
        return doses_taken;
    }

    public void setDoses_taken(int doses_taken)
    {
        this.doses_taken = doses_taken;
    }

    public String getName()
    {
        return Name;
    }

    public void setName(String name)
    {
        Name = name;
    }

    public String getTime1()
    {
        return time1;
    }

    public void setTime1(String time1)
    {
        this.time1 = time1;
    }

    public String getTime2()
    {
        return time2;
    }

    public void setTime2(String time2)
    {
        this.time2 = time2;
    }

    public String getTime3()
    {
        return time3;
    }

    public void setTime3(String time3)
    {
        this.time3 = time3;
    }

    public String getTime4()
    {
        return time4;
    }

    public void setTime4(String time4)
    {
        this.time4 = time4;
    }

    public String getTime5()
    {
        return time5;
    }

    public void setTime5(String time5)
    {
        this.time5 = time5;
    }

    public String getInstructions()
    {
        return instructions;
    }

    public void setInstructions(String instructions)
    {
        this.instructions = instructions;
    }

    public int getT1()
    {
        return t1;
    }

    public void setT1(int t1)
    {
        this.t1 = t1;
    }

    public int getT2()
    {
        return t2;
    }

    public void setT2(int t2)
    {
        this.t2 = t2;
    }

    public int getT3()
    {
        return t3;
    }

    public void setT3(int t3)
    {
        this.t3 = t3;
    }

    public int getT4()
    {
        return t4;
    }

    public void setT4(int t4)
    {
        this.t4 = t4;
    }

    public int getT5()
    {
        return t5;
    }

    public void setT5(int t5)
    {
        this.t5 = t5;
    }
}

package com.example.model;

public class Medicine
{
    private int medKey,courseKey,doses,doses_taken;
    private String Name,time1,time2,time3,time4,time5,instructions;
    private boolean t1,t2,t3,t4,t5;

    public Medicine()
    {
    }

    public Medicine(int medKey, int courseKey, int doses, int doses_taken, String name, String time1, String time2, String time3, String time4, String time5, String instructions, boolean t1, boolean t2, boolean t3, boolean t4, boolean t5)
    {
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

    public boolean isT4()
    {
        return t4;
    }

    public void setT4(boolean t4)
    {
        this.t4 = t4;
    }

    public boolean isT5()
    {
        return t5;
    }

    public void setT5(boolean t5)
    {
        this.t5 = t5;
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

    public String getInstructions()
    {
        return instructions;
    }

    public void setInstructions(String instructions)
    {
        this.instructions = instructions;
    }

    public boolean isT1()
    {
        return t1;
    }

    public void setT1(boolean t1)
    {
        this.t1 = t1;
    }

    public boolean isT2()
    {
        return t2;
    }

    public void setT2(boolean t2)
    {
        this.t2 = t2;
    }

    public boolean isT3()
    {
        return t3;
    }

    public void setT3(boolean t3)
    {
        this.t3 = t3;
    }
}

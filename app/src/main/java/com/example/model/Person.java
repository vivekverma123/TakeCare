package com.example.model;

public class Person
{
    private int Key;
    private String Name,Dob,Sex;
    private double Height,Weight;

    public Person()
    {
    }

    public Person(int key, String name, String dob, String sex, double height, double weight)
    {
        Key = key;
        Name = name;
        Dob = dob;
        Sex = sex;
        Height = height;
        Weight = weight;
    }

    public int getKey()
    {
        return Key;
    }

    public void setKey(int key)
    {
        Key = key;
    }

    public String getName()
    {
        return Name;
    }

    public void setName(String name)
    {
        Name = name;
    }

    public String getDob()
    {
        return Dob;
    }

    public void setDob(String dob)
    {
        Dob = dob;
    }

    public String getSex()
    {
        return Sex;
    }

    public void setSex(String sex)
    {
        Sex = sex;
    }

    public double getHeight()
    {
        return Height;
    }

    public void setHeight(double height)
    {
        Height = height;
    }

    public double getWeight()
    {
        return Weight;
    }

    public void setWeight(double weight)
    {
        Weight = weight;
    }
}

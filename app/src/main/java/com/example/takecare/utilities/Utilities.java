package com.example.takecare.utilities;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Utilities
{
    public static Date getDate() throws Exception
    {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String s1 = sdf.format(date);
        return sdf.parse(s1);
    }

    public static void addDay(Date date)
    {
        date.setTime(date.getTime() + 24*60*60*1000);
    }

    public static String getDatetoString()
    {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String s1 = sdf.format(date);
        return s1;
    }

}

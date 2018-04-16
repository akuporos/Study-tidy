package com.example.stasy.study_tidy;

import android.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;

public class DateStorage {
    //дата, направление дела, список дел
    private HashMap<String, HashMap<String, ArrayList<String>>> dataStorage = new HashMap<String, HashMap<String, ArrayList<String>>>();
    private ArrayList<String> events = new ArrayList<String>();
    private HashMap<String, ArrayList<String>> hashMap = new HashMap<String, ArrayList<String>>();

    public DateStorage()
    {

    }
    public void addEvent(String data, String course, String event)
    {
        if (dataStorage.get(data) == null)
        {
            events = new ArrayList<String>();
        }
        events.add(event);
        hashMap.put(course,events);
        dataStorage.put(data, hashMap);

    }
    public void changeEvent()
    {

    }
    public void deleteEvent()
    {

    }
    public ArrayList<String> todayEvent(String date,String key)
    {
        ArrayList<String> todayEvents = new ArrayList<String>();
        todayEvents = dataStorage.get(date).get(key);
        return todayEvents;
    }

}

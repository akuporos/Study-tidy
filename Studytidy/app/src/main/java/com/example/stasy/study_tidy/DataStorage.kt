package com.example.stasy.study_tidy

import android.util.Pair

import java.util.ArrayList
import java.util.HashMap

class DateStorage {

    companion object {
        //дата, направление дела, список дел
        val educationEvent = "Учебная"
        val funEvent = "Внеучебная"
        var dataStorage = HashMap<String, HashMap<String, ArrayList<String>>>()


    fun addEvent(data: String, course: String, event: String) {
        var events = ArrayList<String>()
        var hashMap = HashMap<String, ArrayList<String>>()
        if (!dataStorage.containsKey(data)) {
            events = ArrayList()
        }
        events.add(event)
        hashMap.set(course, events)
        dataStorage.set(data, hashMap)

    }

    fun deleteEvent(date: String, key: String, position: Int) {
        dataStorage.get(date)?.get(key)?.removeAt(position)
    }

    fun todayEvent(date: String, key: String): ArrayList<String> {
        var todayEvents = ArrayList<String>()
        if (dataStorage.containsKey(date) && dataStorage[date]!!.containsKey(key)) {
            todayEvents = dataStorage[date]!!.get(key)!!
        } else {
            todayEvents.add(0, "")

        }
        return todayEvents

    }

    fun changeEvent(s: String, s1: String, toString: String, position: Int) {
        dataStorage.get(s)?.get(s1)?.add(position, toString)
    }
    }
}

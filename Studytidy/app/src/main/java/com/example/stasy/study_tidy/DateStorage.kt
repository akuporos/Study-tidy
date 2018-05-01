package com.example.stasy.study_tidy

import android.util.Pair
import com.google.gson.internal.LinkedTreeMap
import java.io.FileWriter
import java.io.Writer

import java.util.ArrayList
import java.util.HashMap

class DateStorage {

    companion object {
        //дата, направление дела, список дел
        val educationEvent = "Учебная"
        val funEvent = "Внеучебная"
        var dataStorage = HashMap<String, LinkedTreeMap<String, ArrayList<String>>>()
        val filename = "myfile.json"

    fun deleteEvent(date: String, key: String, position: Int) {
        if(position < dataStorage.get(date)?.get(key)?.size!!)
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
    fun addEvent(s: String, s1: String, toString: String) {
        if (!dataStorage.containsKey(s))
            dataStorage[s] = LinkedTreeMap()
        if (!dataStorage[s]!!.containsKey(s1))
            dataStorage[s]!![s1] = ArrayList()
        dataStorage[s]!![s1]!!.add(toString)
    }

    fun setEvent(s: String, s1: String, toString: String, position: Int) {
        if(position < dataStorage.get(s)?.get(s1)?.size!!)
            dataStorage.get(s)?.get(s1)?.set(position, toString)
    }
    }
}

package com.example.mynotebook.data.data_source

import androidx.room.TypeConverter
import com.example.mynotebook.domain.task.utils.AlarmState
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Convertes {

    private val gson = Gson()


    @TypeConverter
    fun fromAlarmState(state: AlarmState): String{
        return gson.toJson(state)
    }

    @TypeConverter
    fun toAlarmState(json: String): AlarmState{
        val type = object: TypeToken<AlarmState>() {}.type
        return gson.fromJson(json, type)
    }
}
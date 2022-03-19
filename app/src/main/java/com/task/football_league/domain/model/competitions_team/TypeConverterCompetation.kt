package com.task.football_league.domain.model.competitions_team

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type


class TypeConverterCompetation {
    val gson : Gson = Gson()
    @TypeConverter
    fun stringToSomeObjectList(data: String?) :Competition? {
        if(data == null)return null
        val listType: Type = object :TypeToken<Competition?>() {}.type
        return gson.fromJson<Competition?>(data, listType)
    }

    @TypeConverter
    fun someObjectListToString(someobject: Competition?): String?
    {
        return gson.toJson(someobject)
    }
}

package com.task.football_league.domain.model.competitions_team

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type


class TypeConverterTeam {
    val gson : Gson = Gson()
    @TypeConverter
    fun stringToSomeObjectList(data: String?) :List<Team>? {
        if(data == null)return null
        val listType: Type = object :TypeToken<List<Team>?>() {}.type
        return gson.fromJson<List<Team>?>(data, listType)
    }

    @TypeConverter
    fun someObjectListToString(someobject: List<Team>?): String?
    {
        return gson.toJson(someobject)
    }
}

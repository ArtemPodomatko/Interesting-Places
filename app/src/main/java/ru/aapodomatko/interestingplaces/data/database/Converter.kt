package ru.aapodomatko.interestingplaces.data.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ru.aapodomatko.interestingplaces.models.places.Coords
import ru.aapodomatko.interestingplaces.models.places.Image


class Converter {
    @TypeConverter
    fun fromString(value: String): ArrayList<Image> {
        val listType = object : TypeToken<ArrayList<Image>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromArrayList(list: ArrayList<Image>): String {
        val gson = Gson()
        return gson.toJson(list)
    }

    @TypeConverter
    fun fromString2(value: String): Coords {
        val type = object : TypeToken<Coords>() {}.type
        return Gson().fromJson(value, type)
    }

    @TypeConverter
    fun fromCoords(type: Coords): String {
        val gson = Gson()
        return gson.toJson(type)
    }
}
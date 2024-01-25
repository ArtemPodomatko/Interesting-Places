package ru.aapodomatko.interestingplaces.data.database


import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ru.aapodomatko.interestingplaces.models.places.Result

@Database(entities = [Result::class], version = 1, exportSchema = true)
@TypeConverters(Converter::class)
abstract class PlacesDataBase : RoomDatabase() {
    abstract fun getPlacesDao(): PlacesDao


}
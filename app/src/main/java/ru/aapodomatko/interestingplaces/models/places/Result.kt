package ru.aapodomatko.interestingplaces.models.places

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(tableName = "places")
data class Result(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val images: ArrayList<Image>,
    val slug: String,
    val title: String,
    @SerializedName("short_title")
    val shortTitle: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("favorites_count")
    val favoriteCount: Int,
    val address: String?,
    val timetable: String?,
    @SerializedName("foreign_url")
    val foreignUrl: String?,
    val subway: String?,
    val coords: Coords?,
    var isLiked: Boolean = false
) : Serializable
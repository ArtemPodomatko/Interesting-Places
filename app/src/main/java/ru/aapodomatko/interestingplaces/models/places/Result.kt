package ru.aapodomatko.interestingplaces.models.places

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Result(
    val id: Int,
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
    val subway: String?
) : Serializable
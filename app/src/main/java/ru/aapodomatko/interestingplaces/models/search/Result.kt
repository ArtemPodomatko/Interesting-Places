package ru.aapodomatko.interestingplaces.models.search

data class Result(
    val age_restriction: Int,
    val comments_count: Int,
    val ctype: String,
    val daterange: Daterange,
    val description: String,
    val disable_comments: Boolean,
    val favorites_count: Int,
    val first_image: FirstImage,
    val id: Int,
    val item_url: String,
    val place: Place,
    val title: String
)
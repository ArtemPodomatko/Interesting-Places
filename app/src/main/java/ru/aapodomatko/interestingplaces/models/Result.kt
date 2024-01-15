package ru.aapodomatko.interestingplaces.models

data class Result(
    val id: Int,
    val images: List<Image>,
    val slug: String,
    val title: String
)
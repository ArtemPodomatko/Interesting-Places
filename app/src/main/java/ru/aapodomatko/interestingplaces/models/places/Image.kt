package ru.aapodomatko.interestingplaces.models.places

import java.io.Serializable

data class Image(
    val image: String?,
    val source: Source?
) : Serializable
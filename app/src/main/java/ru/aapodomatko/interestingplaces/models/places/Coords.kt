package ru.aapodomatko.interestingplaces.models.places

import java.io.Serializable

data class Coords(
    val lat: Double?,
    val lon: Double?
) : Serializable

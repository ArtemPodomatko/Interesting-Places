package ru.aapodomatko.interestingplaces.models.search

data class Daterange(
    val end: Long,
    val end_date: Int,
    val end_time: Any,
    val is_continuous: Boolean,
    val is_endless: Boolean,
    val is_startless: Boolean,
    val schedules: List<Any>,
    val start: String,
    val start_date: Int,
    val start_time: Any,
    val use_place_schedule: Boolean
)


package ru.aapodomatko.interestingplaces.models.places

data class ApiResponse(
    val count: Int,
    val next: String,
    val previous: Any,
    val results: List<Result>
)
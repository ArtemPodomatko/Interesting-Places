package ru.aapodomatko.interestingplaces.models.search

data class SearchResponse(
    val count: Int,
    val next: String,
    val previous: Any,
    val results: List<Result>
)
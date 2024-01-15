package ru.aapodomatko.interestingplaces.data.api

import javax.inject.Inject

class RetrofitRepository @Inject constructor(private val kudaGoService: KudaGoService) {
    suspend fun getPlacesList(fields: String, location: String, categories: String) =
        kudaGoService.getPlacesList(
            fields = fields,
            location = location,
            categories = categories
        )
}
package ru.aapodomatko.interestingplaces.data.api

import javax.inject.Inject

class RetrofitRepository @Inject constructor(private val kudaGoService: KudaGoService) {
    suspend fun getPlacesList(fields: String, location: String, pageSize: Int, categories: String) =
        kudaGoService.getPlacesList(
            fields = fields,
            location = location,
            pageSize = pageSize,
            categories = categories,
        )

    suspend fun searchAll(q: String, location: String, pageSize: Int, ctype: String) =
        kudaGoService.searchAll(
            q = q,
            location = location,
            pageSize = pageSize,
            ctype = ctype
        )
}
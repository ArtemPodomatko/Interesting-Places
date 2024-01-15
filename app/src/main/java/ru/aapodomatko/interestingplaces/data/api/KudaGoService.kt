package ru.aapodomatko.interestingplaces.data.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ru.aapodomatko.interestingplaces.models.ApiResponse

interface KudaGoService {

    @GET("public-api/v1.3/places")
    suspend fun getPlacesList(
        @Query("fields") fields: String,
        @Query("location") location: String,
        @Query("categories") categories: String
    ) : Response<ApiResponse>
}
package ru.aapodomatko.interestingplaces.data.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import ru.aapodomatko.interestingplaces.models.places.ApiResponse
import ru.aapodomatko.interestingplaces.models.search.SearchResponse

interface KudaGoService {

    @GET("public-api/v1.4/places")
    suspend fun getPlacesList(
        @Query("fields") fields: String,
        @Query("location") location: String,
        @Query("page_size") pageSize: Int,
        @Query("categories") categories: String,
    ) : Response<ApiResponse>

    @GET("public-api/v1.4/search")
    suspend fun searchAll(
        @Query("q") q: String,
        @Query("location") location: String,
        @Query("page_size") pageSize: Int,
        @Query("ctype") ctype: String,

    ): Response<SearchResponse>
}
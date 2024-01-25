package ru.aapodomatko.interestingplaces.data.api

import ru.aapodomatko.interestingplaces.data.database.PlacesDao
import ru.aapodomatko.interestingplaces.models.places.Result
import javax.inject.Inject

class RetrofitRepository @Inject constructor(
    private val kudaGoService: KudaGoService,
    private val placesDao: PlacesDao
) {
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

    suspend fun addToFavorite(place: Result) = placesDao.insert(place)

    suspend fun deleteFavorite(place: Result) = placesDao.delete(place)

    suspend fun getAllPlaces(): List<Result> {
        return placesDao.getAllPlaces()
    }

    suspend fun updatePlace(place: Result) = placesDao.update(place)
}
package ru.aapodomatko.interestingplaces.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.aapodomatko.interestingplaces.data.api.RetrofitRepository
import ru.aapodomatko.interestingplaces.models.places.Result
import javax.inject.Inject

@HiltViewModel
class DescriptionViewModel @Inject constructor(private val repository: RetrofitRepository) : ViewModel() {
    val favoritePlaces = MutableLiveData<List<Result>>()

    init {
        getAllPlaces()
    }

    fun addPlaceToFavorite(place: Result) {
        viewModelScope.launch(Dispatchers.IO) {
            place.isLiked = true
            repository.updatePlace(place)
            repository.addToFavorite(place)
        }
    }

   private fun getAllPlaces() {
        viewModelScope.launch(Dispatchers.IO) {
            favoritePlaces.postValue(repository.getAllPlaces())
        }
    }
}
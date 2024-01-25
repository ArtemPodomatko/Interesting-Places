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
class FavoriteViewModel @Inject constructor(private val repository: RetrofitRepository) : ViewModel() {
    val placesFavorite = MutableLiveData<List<Result>>()

    init {
        getAllPlaces()
    }

    private fun getAllPlaces() {
        viewModelScope.launch(Dispatchers.IO) {
            placesFavorite.postValue(repository.getAllPlaces())
        }
    }

    fun deleteFavoriteItem(item: Result) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteFavorite(item)
            placesFavorite.postValue(repository.getAllPlaces())
        }
    }
}
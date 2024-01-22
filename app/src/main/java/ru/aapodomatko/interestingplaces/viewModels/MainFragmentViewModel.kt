package ru.aapodomatko.interestingplaces.viewModels

import android.util.Log
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
class MainFragmentViewModel @Inject constructor(private val repository: RetrofitRepository) : ViewModel() {

    val interestingPlacesLiveData = MutableLiveData<List<Result>>()

    init {
        getPlacesList(
            fields = "id,title,address,timetable,phone,short_title,slug,images,description,foreign_url,subway,favorites_count",
            location = "spb",
            pageSize = 40,
            categories = "sights",
        )
        Log.d("ViewModel", "Send Request")
    }

    fun getPlacesList(fields: String, location: String, pageSize: Int, categories: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.getPlacesList(fields, location, pageSize, categories)
            if (response.isSuccessful) {
                interestingPlacesLiveData.postValue(response.body()?.results)
            } else {
                Log.d("MainViewModel", "Response was not found")
            }
        }
    }


}
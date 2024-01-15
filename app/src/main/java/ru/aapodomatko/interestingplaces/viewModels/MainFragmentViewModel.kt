package ru.aapodomatko.interestingplaces.viewModels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.aapodomatko.interestingplaces.data.api.RetrofitRepository
import ru.aapodomatko.interestingplaces.models.ApiResponse
import ru.aapodomatko.interestingplaces.models.Result
import javax.inject.Inject

@HiltViewModel
class MainFragmentViewModel @Inject constructor(private val repository: RetrofitRepository) : ViewModel() {


    fun getPlacesList(fields: String, location: String, categories: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getPlacesList(fields, location, categories)
        }
    }

}
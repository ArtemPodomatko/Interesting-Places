package ru.aapodomatko.interestingplaces.viewModels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.aapodomatko.interestingplaces.data.api.RetrofitRepository
import ru.aapodomatko.interestingplaces.models.search.Result
import javax.inject.Inject

@HiltViewModel
class SearchFragmentViewModel @Inject constructor(private val repository: RetrofitRepository) : ViewModel() {
    val searchResponse = MutableLiveData<List<Result>>()

    fun searchAll(
        q: String,
        location: String = "spb",
        pageSize: Int = 40,
        ctype: String = "event"
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.searchAll(q, location, pageSize, ctype)
            if (response.isSuccessful) {
                searchResponse.postValue(response.body()?.results)
            }
            else {
                Log.d("SearchViewModel", "Error")
            }
        }
    }
}


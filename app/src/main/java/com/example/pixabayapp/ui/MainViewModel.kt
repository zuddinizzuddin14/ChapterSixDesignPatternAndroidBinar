package com.example.pixabayapp.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pixabayapp.model.SearchResponse
import com.example.pixabayapp.repository.MainRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(private val mainRepository: MainRepository): ViewModel() {

    val searchResult = MutableLiveData<SearchResponse>()
    val loadingState = MutableLiveData<Boolean>()
    val errorState = MutableLiveData<Pair<Boolean, Exception?>>()

    fun searchPost(query: String) {
        loadingState.postValue(true)
        viewModelScope.launch(Dispatchers.IO) {
            errorState.postValue(Pair(false, null))
            try {
                val data = mainRepository.searchPhoto(query = query)
                viewModelScope.launch(Dispatchers.Main) {
                    searchResult.postValue(data)
                    loadingState.postValue(false)
                    errorState.postValue(Pair(false, null))
                }
            } catch (e: Exception) {
                viewModelScope.launch(Dispatchers.Main) {
                    loadingState.postValue(false)
                    errorState.postValue(Pair(true, e))
                }
            }
        }
    }
}
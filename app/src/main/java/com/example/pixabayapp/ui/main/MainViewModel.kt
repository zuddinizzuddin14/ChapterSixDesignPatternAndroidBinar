package com.example.pixabayapp.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pixabayapp.Resource
import com.example.pixabayapp.data.model.SearchResponse
import com.example.pixabayapp.data.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val mainRepository: MainRepository
    ): ViewModel() {

    val searchResult = MutableLiveData<Resource<SearchResponse>>()
    val loadingState = MutableLiveData<Boolean>()
    val errorState = MutableLiveData<Pair<Boolean, Exception?>>()

    fun searchPost(query: String) {
        loadingState.postValue(true)
        viewModelScope.launch(Dispatchers.IO) {
            errorState.postValue(Pair(false, null))
            try {
                val data = mainRepository.searchPhoto(query)
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
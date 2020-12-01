package com.example.cats.presentation.details

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cats.repository.CatsRepository
import com.example.cats.model.Cat
import kotlinx.coroutines.launch

class CatDetailsViewModel(private val repository: CatsRepository) : ViewModel() {
    val details = MutableLiveData<Cat>()
    val loading = MutableLiveData<Boolean>()
    val noSuchCatError = MutableLiveData<String>()

    fun start(name: String?) = viewModelScope.launch {
        if (name.isNullOrEmpty()) {
            noSuchCatError.postValue("There are no such cat :(")
            return@launch
        }

        loading.postValue(true)
        try {
            val cat = repository.getCatByName(name)
            details.postValue(cat)
        } catch (e: NoSuchElementException) {
            noSuchCatError.postValue("There are no such cat :(")
        } finally {
            loading.postValue(false)
        }
    }
}
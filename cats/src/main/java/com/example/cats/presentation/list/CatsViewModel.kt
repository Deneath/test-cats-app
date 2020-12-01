package com.example.cats.presentation.list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cats.repository.CatsRepository
import com.example.cats.model.Cat
import com.example.cats.presentation.filter.FilterItem
import kotlinx.coroutines.launch

class CatsViewModel(private val catsRepository: CatsRepository) : ViewModel() {

    val cats = MutableLiveData<List<Cat>>()
    val loading = MutableLiveData<Boolean>()
    val error = MutableLiveData<String>()
    val filters = MutableLiveData<List<FilterItem>>()
    val selectedCat = MutableLiveData<String>()

    private var filterItems: List<FilterItem> = emptyList()
    private var catsSource: MutableList<Cat> = mutableListOf()

    fun start() = viewModelScope.launch {
        loading.postValue(true)
        try {
            catsSource = catsRepository.getCats().toMutableList()
            this@CatsViewModel.cats.postValue(catsSource)
            filterItems = catsSource.distinctBy { it.name }.map { FilterItem(it.name, false) }
        } catch (e: Exception) {
            error.postValue(e.localizedMessage)
        } finally {
            loading.postValue(false)
        }
    }

    fun catSelected(cat: Cat) {
        selectedCat.postValue(cat.name)
    }

    fun onFilter() {
        filters.postValue(filterItems)
    }

    fun onFiltered() {
        val hasNoSelectedFilter = filterItems.all { it.checked.not() }
        if (hasNoSelectedFilter) {
            cats.postValue(catsSource)
        } else {
            val filteredCats =
                catsSource.filter { cat -> filterItems.any { cat.name == it.name && it.checked } }
            cats.postValue(filteredCats)
        }
    }
}
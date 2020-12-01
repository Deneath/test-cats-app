package com.example.cats.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.cats.repository.CatsRepository

class CatsViewModelFactory(private val catsRepository: CatsRepository) : ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(CatsRepository::class.java).newInstance(catsRepository)
    }
}
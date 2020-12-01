package com.example.cats.di

import com.example.cats.repository.CatsRepository

class AppContainer {
    val catsRepository by lazy { CatsRepository() }

    val catsViewModelFactory
        get() = CatsViewModelFactory(catsRepository)

    val catDetailsViewModelFactory
        get() = CatsViewModelFactory(catsRepository)

}
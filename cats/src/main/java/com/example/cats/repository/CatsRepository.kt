package com.example.cats.repository

import com.example.cats.model.Cat
import kotlinx.coroutines.delay

class CatsRepository {

    private var cats = listOf<Cat>()

    fun setCats(cats: List<Cat>) {
        this.cats = cats
    }

    suspend fun getCats(): List<Cat> {
        //emitting request to the server
        delay(2000L)
        return cats
    }

    suspend fun getCatByName(name: String): Cat? {
        //emitting request to the server
        delay(2000L)
        return cats.first { it.name == name }
    }
}
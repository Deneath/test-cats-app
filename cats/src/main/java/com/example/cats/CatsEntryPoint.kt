package com.example.cats

import com.example.cats.model.Cat

interface CatsEntryPoint {
    fun start(cats: List<Cat>)
}
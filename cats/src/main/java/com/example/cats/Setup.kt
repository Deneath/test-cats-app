package com.example.cats

import android.content.Context
import com.example.cats.model.Cat

object CatsLib {
    fun setup(appContext: Context, cats: List<Cat>) {
        (appContext as CatsApp).appContainer.catsRepository.setCats(cats)
    }
}
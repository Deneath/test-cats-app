package com.example.cats.model

import androidx.recyclerview.widget.DiffUtil

data class Cat(
    val picture: String,
    val name: String,
    val breed: String,
    val description: String
) {
    companion object
}

val Cat.Companion.diffUtil: DiffUtil.ItemCallback<Cat>
    get() = object : DiffUtil.ItemCallback<Cat>() {
        override fun areItemsTheSame(oldItem: Cat, newItem: Cat) =
            oldItem.name == newItem.name && oldItem.breed == newItem.breed

        override fun areContentsTheSame(oldItem: Cat, newItem: Cat) = oldItem == newItem
    }




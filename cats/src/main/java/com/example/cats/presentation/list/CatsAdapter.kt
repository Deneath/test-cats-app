package com.example.cats.presentation.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.example.cats.R
import com.example.cats.databinding.ListItemCatBinding
import com.example.cats.model.Cat
import com.example.cats.model.diffUtil

class CatViewHolder(itemView: View, onClick: (Int) -> Unit) : RecyclerView.ViewHolder(itemView) {
    val binding = ListItemCatBinding.bind(itemView)

    init {
        binding.root.setOnClickListener {
            onClick(adapterPosition)
        }
    }
}

class CatsAdapter(private val onClick: (Cat) -> Unit) :
    ListAdapter<Cat, CatViewHolder>(Cat.diffUtil) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_cat, parent, false)
        return CatViewHolder(view) {
            val item = getItem(it)
            onClick(item)
        }
    }

    override fun onBindViewHolder(holder: CatViewHolder, position: Int) {
        val item = getItem(position)
        holder.binding.apply {
            name.text = item.name
            breed.text = item.breed
            Glide.with(holder.itemView)
                .load(item.picture)
                .transform(CenterCrop(), CircleCrop())
                .into(catPicture)
        }
    }
}
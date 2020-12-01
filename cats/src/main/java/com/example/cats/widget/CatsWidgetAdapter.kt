package com.example.cats.widget

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.cats.R
import com.example.cats.databinding.ListItemCatWidgetBinding
import com.example.cats.model.Cat
import com.example.cats.model.diffUtil

class CatWidgetViewHolder(itemView: View, onClick: (Int) -> Unit) :
    RecyclerView.ViewHolder(itemView) {
    val binding = ListItemCatWidgetBinding.bind(itemView)

    init {
        binding.root.setOnClickListener {
            onClick(adapterPosition)
        }
    }
}

class CatsWidgetAdapter(private val onClick: (Cat) -> Unit) :
    ListAdapter<Cat, CatWidgetViewHolder>(Cat.diffUtil) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatWidgetViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_cat_widget, parent, false)
        return CatWidgetViewHolder(view) {
            val item = getItem(it)
            onClick(item)
        }
    }

    override fun onBindViewHolder(holder: CatWidgetViewHolder, position: Int) {
        val item = getItem(position)
        holder.binding.apply {
            name.text = item.name
            breed.text = item.breed
            description.text = item.description
            val cornerRadius =
                holder.itemView.resources.getDimension(R.dimen.cat_item_corner_radius).toInt()
            Glide.with(holder.itemView)
                .load(item.picture)
                .transform(CenterCrop(), RoundedCorners(cornerRadius))
                .into(catPicture)
        }
    }
}
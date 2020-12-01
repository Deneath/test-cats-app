package com.example.cats.presentation.filter

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cats.R
import com.example.cats.databinding.DialogFiltersBinding
import com.example.cats.databinding.ListItemFilterBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class FiltersDialog : BottomSheetDialogFragment() {

    private lateinit var binding: DialogFiltersBinding
    private lateinit var filterItems: List<FilterItem>
    private lateinit var onApply: () -> Unit
    private val adapter by lazy { FiltersAdapter(filterItems, ::onItemSelected) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogFiltersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.filters.adapter = adapter
        binding.resetButton.setOnClickListener {
            filterItems.forEach { it.checked = false }
            adapter.notifyDataSetChanged()
        }
        binding.applyButton.setOnClickListener {
            onApply()
            dismiss()
        }
    }

    fun show(fm: FragmentManager, filterItems: List<FilterItem>, onApply: () -> Unit) {
        this.filterItems = filterItems
        this.onApply = onApply
        show(fm, FiltersDialog::class.java.canonicalName)
    }

    private fun onItemSelected(item: FilterItem) {
        item.checked = item.checked.not()
        val position = filterItems.indexOf(item)
        adapter.notifyItemChanged(position)
    }
}

class FilterViewHolder(itemView: View, onClick: (Int) -> Unit) : RecyclerView.ViewHolder(itemView) {
    val binding = ListItemFilterBinding.bind(itemView)

    init {
        binding.filterItem.setOnClickListener {
            onClick(adapterPosition)
        }
    }
}

class FiltersAdapter(
    private val items: List<FilterItem>,
    private val onClick: (FilterItem) -> Unit
) :
    RecyclerView.Adapter<FilterViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilterViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.list_item_filter, parent, false)
        return FilterViewHolder(view) {
            val item = items[it]
            onClick(item)
        }
    }

    override fun onBindViewHolder(holder: FilterViewHolder, position: Int) {
        val item = items[position]
        holder.binding.apply {
            filterItem.text = item.name
            val color = if (item.checked) ContextCompat.getColor(
                holder.itemView.context,
                R.color.item_selected
            ) else ContextCompat.getColor(holder.itemView.context, android.R.color.transparent)
            filterItem.background = ColorDrawable(color)
        }
    }

    override fun getItemCount() = items.size
}

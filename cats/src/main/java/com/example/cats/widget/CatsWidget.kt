package com.example.cats.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cats.CatsEntryPoint
import com.example.cats.CatsLib
import com.example.cats.R
import com.example.cats.databinding.ViewWidgetBinding
import com.example.cats.model.Cat
import com.example.cats.presentation.details.CatDetailsActivity
import com.example.cats.presentation.list.CatsActivity
import com.example.cats.utils.HorizontalListMarginsItemDecoration

class CatsWidget(context: Context, attrs: AttributeSet) : FrameLayout(context, attrs),
    CatsEntryPoint {
    private val binding: ViewWidgetBinding
    private val adapter by lazy {
        CatsWidgetAdapter(this::showCatScreen)
    }

    init {
        val content = LayoutInflater.from(context).inflate(R.layout.view_widget, this, false)
        binding = ViewWidgetBinding.bind(content)
        addView(content)
        binding.catsList.layoutManager =
            LinearLayoutManager(context).apply { orientation = LinearLayoutManager.HORIZONTAL }

        binding.catsList.adapter = adapter
        val itemMargin = resources.getDimension(R.dimen.cat_item_margin).toInt()
        binding.catsList.addItemDecoration(HorizontalListMarginsItemDecoration(itemMargin))
        binding.openAppButton.setOnClickListener {
            showCatsScreen()
        }
    }

    override fun start(cats: List<Cat>) {
        CatsLib.setup(context.applicationContext, cats)
        adapter.submitList(cats.take(5))
    }

    private fun showCatsScreen() {
        val intent = CatsActivity.buildIntent(context)
        context.startActivity(intent)
    }

    private fun showCatScreen(cat: Cat) {
        val intent = CatDetailsActivity.buildIntent(context, cat.name)
        context.startActivity(intent)
    }
}


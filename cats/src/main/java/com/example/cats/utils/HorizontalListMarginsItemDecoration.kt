package com.example.cats.utils

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class HorizontalListMarginsItemDecoration(private val margin: Int) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = parent.getChildAdapterPosition(view)
        val lastPosition = parent.adapter!!.itemCount - 1

        when (position) {
            lastPosition -> outRect.set(margin, 0, margin, 0)
            else -> outRect.set(margin, 0, 0, 0)
        }
    }
}
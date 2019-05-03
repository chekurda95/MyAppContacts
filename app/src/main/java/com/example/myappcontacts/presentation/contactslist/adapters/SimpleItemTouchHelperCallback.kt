package com.example.myappcontacts.presentation.contactslist.adapters

import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper

class SimpleItemTouchHelperCallback(private val adapter: ItemTouchHelperAdapter): ItemTouchHelper.Callback(){

    override fun isLongPressDragEnabled() = false

    override fun isItemViewSwipeEnabled() = true

    override fun getMovementFlags(p0: RecyclerView, p1: RecyclerView.ViewHolder): Int {
        val dragFlags = ItemTouchHelper.UP or ItemTouchHelper.DOWN
        val swipeFlags = ItemTouchHelper.START or ItemTouchHelper.END
        return makeMovementFlags(dragFlags, swipeFlags)
    }

    override fun onMove(p0: RecyclerView, p1: RecyclerView.ViewHolder, p2: RecyclerView.ViewHolder) = false

    override fun onSwiped(p0: RecyclerView.ViewHolder, p1: Int) =
        adapter.onItemDismiss(p0.adapterPosition)

}
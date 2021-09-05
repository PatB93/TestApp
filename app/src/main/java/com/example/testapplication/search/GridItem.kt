package com.example.testapplication.search

interface GridItem<T> {
    val item: T
    fun bind(viewHolder: GridAdapter.ViewHolder, onClick: (T) -> Unit)
}
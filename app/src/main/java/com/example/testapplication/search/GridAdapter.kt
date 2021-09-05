package com.example.testapplication.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.model.PhotoResult
import com.example.testapplication.databinding.ImageItemBinding

class GridAdapter(val onItemClicked: (PhotoResult) -> Unit) :
    RecyclerView.Adapter<GridAdapter.ViewHolder>() {

    class ViewHolder(val view: ImageItemBinding) : RecyclerView.ViewHolder(view.root)

    private var items = listOf<ImageItems>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ImageItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        items[position].bind(holder) { onItemClicked(it) }
    }

    override fun getItemCount(): Int = items.size

    fun setItems(items: List<ImageItems>) {
        this.items = items
        notifyDataSetChanged()
    }
}
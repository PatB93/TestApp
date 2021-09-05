package com.example.testapplication.search

import com.bumptech.glide.Glide
import com.example.domain.model.ImageSize
import com.example.domain.model.PhotoResult
import com.example.testapplication.R

data class ImageItems(override val item: PhotoResult) : GridItem<PhotoResult> {

    override fun bind(viewHolder: GridAdapter.ViewHolder, onClick: (PhotoResult) -> Unit) {
        val binding = viewHolder.view

        Glide
            .with(viewHolder.itemView)
            .load(item.getUrl(ImageSize.THUMBNAIL))
            .placeholder(R.drawable.ic_broken_image)
            .into(binding.thumbnail)

        binding.label.text = item.title
        binding.imageListItem.setOnClickListener { onClick(item) }
    }
}
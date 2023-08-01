package com.example.codereviewtask_jc51.presentation.breeds

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.codereviewtask_jc51.R
import com.example.codereviewtask_jc51.databinding.CatBreedItemBinding
import com.example.codereviewtask_jc51.domain.model.CatPreview

class CatBreedsAdapter(
    private val onClickListener: (CatPreview) -> Unit,
    private val onAddToFavoriteListener: (CatPreview) -> Unit
): ListAdapter<CatPreview, CatBreedsAdapter.ViewHolder>(CatDiff()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatBreedsAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = CatBreedItemBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int)  = holder.bind(getItem(position), position)

    inner class ViewHolder(private val binding: CatBreedItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: CatPreview, position: Int) {
            binding.run {
                Glide.with(root).load(item.picture).into(binding.imageThumb)
                breedName.text = item.breedName
                catContainer.setOnClickListener {
                    onClickListener.invoke(item)
                }
                if (item.favorite) {
                    imageFavorite.setImageResource(R.drawable.ic_far_heart_selected)
                } else {
                    imageFavorite.setImageResource(R.drawable.ic_far_heart)
                }
                imageFavorite.setOnClickListener {
                    onAddToFavoriteListener.invoke(item)
                    notifyItemChanged(position)
                }
            }
        }
    }

    private class CatDiff : DiffUtil.ItemCallback<CatPreview>() {
        override fun areItemsTheSame(
            oldItem: CatPreview,
            newItem: CatPreview
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: CatPreview,
            newItem: CatPreview
        ): Boolean {
            return oldItem == newItem
        }
    }
}
package com.example.codereviewtask_jc51.presentation.utils

import android.view.View
import android.widget.ImageView
import androidx.annotation.IdRes
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.codereviewtask_jc51.R


/***************************************
 * Binding Adapter for loading URI Image
 ***************************************/
@BindingAdapter("imageUrl")
fun displayImageFromUrl(imageView: ImageView, imageUrl: String?) {
    imageUrl?.let {
        Glide.with(imageView.context)
            .load(imageUrl)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .override(imageView.width, imageView.height)
            .placeholder(R.drawable.ic_cat_placeholder)
            .error(R.drawable.ic_cat_placeholder)
            .into(imageView)
    }
}

@BindingAdapter("visible")
fun setViewVisibility(view: View, isVisible: Boolean) {
    view.isVisible = isVisible
}

@BindingAdapter("imageRes")
fun loadImage(view: ImageView?, @IdRes imageId: Int?) {
    imageId?.let {
        view?.setImageResource(it)
    }
}

/***************************************
 * Setting Observers
 ***************************************/
@BindingAdapter("items")
fun updateListAdapter(view: RecyclerView, newList: List<Any>?) {
    view.adapter?.let {
        when (it) {
            is ListAdapter<*, *> -> (it as ListAdapter<Any, *>).submitList(newList)
            else -> throw RuntimeException("'items' binding adapter cannot be used with a non ListAdapter")
        }
    }
}
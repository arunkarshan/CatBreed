package com.example.codereviewtask_jc51.presentation.utils

import com.example.codereviewtask_jc51.BR
import com.example.codereviewtask_jc51.R
import com.example.codereviewtask_jc51.databinding.CatBreedItemBinding
import com.example.codereviewtask_jc51.presentation.entity.CatPreview

class CatBreedsAdapter(
    private val onClickListener: (CatPreview) -> Unit,
    private val onAddToFavoriteListener: (CatPreview) -> Unit
): BaseListAdapter<CatPreview, CatBreedItemBinding>(
    itemLayoutId = R.layout.cat_breed_item,
    bindingItemId = BR.item,
    bindingMap = hashMapOf(BR.onClick to  object :
        AdapterClickItemListener<CatPreview> {
        override fun onItemClicked(item: CatPreview) {
            onClickListener.invoke(item)
        }
    }),
    areItemsSameLambda = { new, old -> new.id == old.id},
    areContentsTheSame = { new, old -> new == old},
){
    override fun onBind(binding: CatBreedItemBinding, item: CatPreview, position: Int) {
        super.onBind(binding, item, position)
        binding.imageFavorite.setOnClickListener{
            onAddToFavoriteListener.invoke(item)
            notifyItemChanged(position)
        }
    }
}
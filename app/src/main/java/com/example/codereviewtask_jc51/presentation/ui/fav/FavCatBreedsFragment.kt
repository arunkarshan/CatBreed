package com.example.codereviewtask_jc51.presentation.ui.fav

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.codereviewtask_jc51.databinding.FavCatBreedsFragmentBinding
import com.example.codereviewtask_jc51.presentation.ui.breeds.CatBreedsFragmentDirections
import com.example.codereviewtask_jc51.presentation.utils.CatBreedsAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavCatBreedsFragment : Fragment() {

    private val viewModel: FavCatBreedsViewModel by viewModels()

    private val adapter by lazy {
        CatBreedsAdapter(
            onClickListener = {
                findNavController().navigate(
                    FavCatBreedsFragmentDirections.actionFromFavCatsToDetails(it.id),
                )
            },
            onAddToFavoriteListener = {
                viewModel.removeCatBreedFromFavourites(it)
            })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FavCatBreedsFragmentBinding.inflate(layoutInflater).apply {
            breedsList.adapter = this@FavCatBreedsFragment.adapter
            viewModel = this@FavCatBreedsFragment.viewModel
            lifecycleOwner = viewLifecycleOwner
        }.root
    }

    override fun onResume() {
        super.onResume()
        viewModel.refreshFavouriteStatus()
    }
}
package com.example.codereviewtask_jc51.presentation.fav

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.codereviewtask_jc51.CatApp
import com.example.codereviewtask_jc51.databinding.FavCatBreedsFragmentBinding

class FavCatBreedsFragment : Fragment() {

    private var _viewBinding: FavCatBreedsFragmentBinding? = null
    private val viewBinding
        get() = requireNotNull(_viewBinding)

    private val viewModel: FavCatBreedsViewModel by viewModels {
        (requireActivity().application as CatApp).let {
            FavCatBreedsViewModel.provideFactory(
                it.catRepository,
                it.favoriteCatSaver,
                this
            )
        }
    }

    private val adapter: FavCatBreedsAdapter by lazy {
        FavCatBreedsAdapter(
            onClickListener = {
                findNavController().navigate(
                    FavCatBreedsFragmentDirections.actionFromFavCatsToDetails(it.id)
                )
            },
            {viewModel.removeCatBreedFromFavourites(it)}
        )
    }

    private var swipeToRefreshLayout: SwipeRefreshLayout? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _viewBinding = FavCatBreedsFragmentBinding.inflate(layoutInflater).apply {
            breedsList.adapter = this@FavCatBreedsFragment.adapter
            swipeToRefreshLayout = swipeToRefresh.apply {
                setOnRefreshListener {
                    viewModel.loadFavorites()
                }
            }
        }
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.run {
            favoriteCats.observe(viewLifecycleOwner) {
                viewBinding.noFavBreedPlaceholder.visibility =
                    if (it.isEmpty()) View.VISIBLE else View.INVISIBLE

                adapter.submitList(it)
            }
            isLoading.observe(viewLifecycleOwner) {
                swipeToRefreshLayout?.isRefreshing = it
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _viewBinding = null
    }
}
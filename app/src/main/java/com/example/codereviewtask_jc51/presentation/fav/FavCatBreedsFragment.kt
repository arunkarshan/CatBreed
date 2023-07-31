package com.example.codereviewtask_jc51.presentation.fav

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.codereviewtask_jc51.ApplicationDependencies
import com.example.codereviewtask_jc51.R
import com.example.codereviewtask_jc51.databinding.FavCatBreedsFragmentBinding

class FavCatBreedsFragment : Fragment() {

    private var _viewBinding: FavCatBreedsFragmentBinding? = null
    private val viewBinding
        get() = _viewBinding!!

    private lateinit var viewModel: FavCatBreedsViewModel
    private var adapter: FavCatBreedsAdapter? = null
    private var swipeToRefresh: SwipeRefreshLayout? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModel = FavCatBreedsViewModel(
            ApplicationDependencies.catRepository,
            ApplicationDependencies.favoriteCatSaver
        ).apply {
            favoriteCats.observe(viewLifecycleOwner) {
                viewBinding.noFavBreedPlaceholder.visibility = if (it.isEmpty()) View.VISIBLE else View.INVISIBLE

                adapter?.setItems(it)
                swipeToRefresh?.isRefreshing = it.isEmpty()
            }
        }

        _viewBinding = FavCatBreedsFragmentBinding.inflate(layoutInflater)
        return _viewBinding!!.root
    }

    override fun onResume() {
        super.onResume()
        adapter = FavCatBreedsAdapter (
            onClickListener = {
                findNavController().navigate(
                    R.id.navigation_cat_info,
                    Bundle().apply { putLong("catId", it.id.toLong()) }
                )
            }
        )
        viewBinding.breeds.let {
            it.adapter = this@FavCatBreedsFragment.adapter
            it.layoutManager = LinearLayoutManager(context)
        }

        viewModel.loadFavorites()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _viewBinding = null
    }
}
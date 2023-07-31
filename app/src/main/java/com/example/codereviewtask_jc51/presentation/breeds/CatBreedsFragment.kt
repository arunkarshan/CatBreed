package com.example.codereviewtask_jc51.presentation.breeds

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
import com.example.codereviewtask_jc51.databinding.CatBreedsFragmentBinding

class CatBreedsFragment : Fragment() {

    private var _viewBinding: CatBreedsFragmentBinding? = null
    private val viewBinding
        get() = _viewBinding!!

    private lateinit var view_model: CatBreedsViewModel
    private var adapter: CatBreedsAdapter? = null
    private var swipeToRefresh: SwipeRefreshLayout? = null

    private fun setupSwipeToRefresh() {
        swipeToRefresh = viewBinding.swipeToRefresh
        swipeToRefresh?.setOnRefreshListener {
            swipeToRefresh?.isRefreshing = true
            view_model.loadBreeds()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        view_model = CatBreedsViewModel(
            ApplicationDependencies.catRepository,
            ApplicationDependencies.favoriteCatSaver
        ).apply {
            breedsList.observe(viewLifecycleOwner) {
                adapter?.setItems(it)
                swipeToRefresh?.isRefreshing = it.isEmpty()
            }
            catFact.observe(viewLifecycleOwner) {
                viewBinding.catFact.text = it
            }
        }

        _viewBinding = CatBreedsFragmentBinding.inflate(layoutInflater)
        return _viewBinding!!.root
    }

    override fun onResume() {
        super.onResume()
        adapter = CatBreedsAdapter(
            onClickListener = {
                findNavController().navigate(
                    R.id.navigation_cat_info,
                    Bundle().apply { putLong("catId", it.id.toLong()) }
                )
            },
            { view_model.setFavoriteCatBreed(it.id) }
        )
        viewBinding.breeds.let {
            it.adapter = this@CatBreedsFragment.adapter
            it.layoutManager = LinearLayoutManager(context)
        }

        setupSwipeToRefresh()

        view_model.loadCatFacts()
        view_model.loadBreeds()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _viewBinding = null
    }
}
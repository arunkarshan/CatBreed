package com.example.codereviewtask_jc51.presentation.breeds

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
import com.example.codereviewtask_jc51.databinding.CatBreedsFragmentBinding

class CatBreedsFragment : Fragment() {

    private var _viewBinding: CatBreedsFragmentBinding? = null
    private val viewBinding
        get() = _viewBinding!!

    private val viewModel: CatBreedsViewModel by viewModels {
        (requireActivity().application as CatApp).let {
            CatBreedsViewModel.provideFactory(
                it.catRepository,
                it.favoriteCatSaver,
            this
            )
        }
    }
    private val adapter: CatBreedsAdapter by lazy {
        CatBreedsAdapter(
            onClickListener = {
                findNavController().navigate(
                    CatBreedsFragmentDirections.actionFromCatsToDetails(it.id),
                )
            },
            {
                viewModel.changeCatBreedFavouriteStatus(it)
            }
        )
    }

    private var swipeToRefresh: SwipeRefreshLayout? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _viewBinding = CatBreedsFragmentBinding.inflate(layoutInflater).apply {
            breedsList.adapter = this@CatBreedsFragment.adapter
        }
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.apply {
            breedsList.observe(viewLifecycleOwner) {
                adapter.submitList(it)
            }
            catFact.observe(viewLifecycleOwner) {
                viewBinding.catFact.text = it
            }
            isLoading.observe(viewLifecycleOwner) {
                swipeToRefresh?.isRefreshing = it
            }
        }

        swipeToRefresh = viewBinding.swipeToRefresh.apply {
            setOnRefreshListener {
                viewModel.loadBreeds()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.refreshFavouriteStatus()
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _viewBinding = null
    }
}
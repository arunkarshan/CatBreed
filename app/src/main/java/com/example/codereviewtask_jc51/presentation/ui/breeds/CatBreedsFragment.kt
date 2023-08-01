package com.example.codereviewtask_jc51.presentation.ui.breeds

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.codereviewtask_jc51.R
import com.example.codereviewtask_jc51.databinding.CatBreedsFragmentBinding
import com.example.codereviewtask_jc51.presentation.utils.CatBreedsAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CatBreedsFragment : Fragment() {
    private val viewModel: CatBreedsViewModel by viewModels()

    private val adapter by lazy {
       CatBreedsAdapter(onClickListener = {
           findNavController().navigate(
               CatBreedsFragmentDirections.actionFromCatsToDetails(it.id),
           )
       }, onAddToFavoriteListener = {
           viewModel.changeCatBreedFavouriteStatus(it)
       })
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return CatBreedsFragmentBinding.inflate(layoutInflater).apply {
            breedsList.adapter = this@CatBreedsFragment.adapter
            viewModel = this@CatBreedsFragment.viewModel
            lifecycleOwner = viewLifecycleOwner
        }.root
    }

    override fun onResume() {
        super.onResume()
        viewModel.refreshFavouriteStatus()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.error.observe(viewLifecycleOwner){
            //TODO: Implement a proper sad path journey
            Toast.makeText(requireContext(), getString(R.string.error_message), Toast.LENGTH_SHORT).show()
        }
    }

}
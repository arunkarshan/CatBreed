package com.example.codereviewtask_jc51.presentation.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.codereviewtask_jc51.ApplicationDependencies
import com.example.codereviewtask_jc51.databinding.FragmentCatDetailsBinding

class CatDetailsFragment: Fragment() {

    private var viewBinding: FragmentCatDetailsBinding? = null
    private val viewModel: CatDetailsViewModel by viewModels { object : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return CatDetailsViewModel(ApplicationDependencies.catRepository) as T
        }
    } }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentCatDetailsBinding.inflate(inflater)



        return viewBinding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.fetchCatDetails((arguments?.getLong("catId", 0L) ?: 0L))
        viewModel.details.observe(viewLifecycleOwner) {
            with(viewBinding!!) {
                breedName.text = it.breedName
                breedLifespan.text = it.lifespan
                breedOrigin.text = it.origin
                breedRate.text = "${it.rate}/5.0"
                funFact.text = it.fun_fact

                Glide.with(catImage).load(it.picture).into(catImage)
            }
        }
    }
}
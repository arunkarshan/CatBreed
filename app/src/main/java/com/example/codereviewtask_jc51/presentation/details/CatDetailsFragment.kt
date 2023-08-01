package com.example.codereviewtask_jc51.presentation.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.codereviewtask_jc51.CatApp
import com.example.codereviewtask_jc51.R
import com.example.codereviewtask_jc51.databinding.CatDetailsFragmentBinding
import com.example.codereviewtask_jc51.domain.model.CatDetails

class CatDetailsFragment : Fragment() {

    private val args by navArgs<CatDetailsFragmentArgs>()

    private var _viewBinding: CatDetailsFragmentBinding? = null
    private val viewBinding: CatDetailsFragmentBinding
        get() = requireNotNull(_viewBinding)

    private val viewModel: CatDetailsViewModel by viewModels {
        CatDetailsViewModel.provideFactory(
            catRepository = (requireActivity().application as CatApp).catRepository,
            favoriteCatSaver = (requireActivity().application as CatApp).favoriteCatSaver,
            catId = args.catId,
            owner = this
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _viewBinding = CatDetailsFragmentBinding.inflate(inflater)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.details.observe(viewLifecycleOwner) {
            /*
                To improve the user experience, we can adopt various approaches for handling
                error scenarios, such as API failures or invalid catId. Suggested solutions
                include showing an error screen, an alert dialog, or a toast to notify the
                user that an issue has occurred, and they won't be able to view the details.
                For simplicity, the provided solution demonstrates a toast and navigating back,
                but it is highly recommended to use an alert dialog or error screen
                that aligns with the app's theme, providing a more cohesive user experience.
            */

            if (it != CatDetails.EMPTY) {
                setCatDetailsData(it)
            } else {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.error_message),
                    Toast.LENGTH_SHORT
                ).show()
                findNavController().navigateUp()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _viewBinding = null
    }
    private fun setCatDetailsData(catDetails: CatDetails) {
        with(viewBinding) {
            breedName.text = catDetails.breedName
            breedLifespan.text = catDetails.lifespan
            breedOrigin.text = catDetails.origin
            breedRateLabel.text = getString(R.string.cat_rating, catDetails.rate)
            breedRating.rating = catDetails.rate.toFloat()
            breedFunFactLabel.text = getString(R.string.breed_funfact_label, catDetails.breedName)
            funFact.text = catDetails.funFact
            btnCatDetailFavourite.setOnClickListener{
                viewModel.changeCatBreedFavouriteStatus(catDetails)
            }
            imgCatDetailsFavourite.setImageResource(
                if(catDetails.favorite) R.drawable.ic_far_heart_selected else R.drawable.ic_far_heart
            )

            Glide.with(catImage).load(catDetails.picture).into(catImage)
        }
    }
}
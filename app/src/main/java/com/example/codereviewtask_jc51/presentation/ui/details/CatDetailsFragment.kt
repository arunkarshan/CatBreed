package com.example.codereviewtask_jc51.presentation.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.codereviewtask_jc51.R
import com.example.codereviewtask_jc51.databinding.CatDetailsFragmentBinding
import com.example.codereviewtask_jc51.domain.model.CatDetailsDomain
import com.example.codereviewtask_jc51.presentation.entity.CatDetails
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CatDetailsFragment : Fragment() {

    private val viewModel: CatDetailsViewModel by viewModels ()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return CatDetailsFragmentBinding.inflate(inflater).apply {
            viewModel = this@CatDetailsFragment.viewModel
            lifecycleOwner = viewLifecycleOwner
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /*
            To improve the user experience, we can adopt various approaches for handling
            error scenarios, such as API failures or invalid catId. Suggested solutions
            include showing an error screen, an alert dialog, or a toast to notify the
            user that an issue has occurred, and they won't be able to view the details.
            For simplicity, the provided solution demonstrates a toast and navigating back,
            but it is highly recommended to use an alert dialog or error screen
            that aligns with the app's theme, providing a more cohesive user experience.
        */

        viewModel.error.observe(viewLifecycleOwner) {
            Toast.makeText(
                requireContext(),
                getString(R.string.error_message),
                Toast.LENGTH_SHORT
            ).show()
            findNavController().navigateUp()
        }
    }
}
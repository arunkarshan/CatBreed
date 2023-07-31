package com.example.codereviewtask_jc51.presentation.ui.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.codereviewtask_jc51.domain.repository.CatFavouriteRepository
import com.example.codereviewtask_jc51.domain.repository.CatRepository
import com.example.codereviewtask_jc51.domain.utils.collect
import com.example.codereviewtask_jc51.presentation.entity.CatDetails
import com.example.codereviewtask_jc51.presentation.usecases.GetCatDetailsUseCase
import com.example.codereviewtask_jc51.presentation.usecases.SetCatFavouriteStatusUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable
import javax.inject.Inject

@HiltViewModel
class CatDetailsViewModel @Inject constructor(
    private val getCatDetailsUseCase: GetCatDetailsUseCase,
    private val setCatFavouriteStatusUseCase: SetCatFavouriteStatusUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val catId: Int = requireNotNull(savedStateHandle.get("catId"))

    private val _details = MutableLiveData<CatDetails>()
    val details: LiveData<CatDetails> = _details

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    private val _isLoading: MutableLiveData<Boolean> = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading


    init {
        fetchCatDetails()
    }

    private fun fetchCatDetails() {
        if (catId == 0) {
            _error.value = "Invalid cat breed!"
        }
       getCatDetailsUseCase.collect(
           scope = viewModelScope,
           param = GetCatDetailsUseCase.Params(
               catId = catId
           ),
           data = _details,
           error = _error,
           loading = _isLoading,
       )
    }


    fun changeCatBreedFavouriteStatus() {
        details.value?.let {
            setCatFavouriteStatusUseCase.invoke(
                SetCatFavouriteStatusUseCase.Params(catId = it.id, favourite = !it.favorite)
            )
            _details.value = it.apply {
                favorite = !favorite
            }
        }
    }
}
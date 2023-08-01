package com.example.codereviewtask_jc51.presentation.ui.breeds

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.codereviewtask_jc51.domain.repository.CatFavouriteRepository
import com.example.codereviewtask_jc51.domain.repository.CatRepository
import com.example.codereviewtask_jc51.domain.utils.collect
import com.example.codereviewtask_jc51.presentation.entity.CatPreview
import com.example.codereviewtask_jc51.presentation.usecases.GetCatBreedsUseCase
import com.example.codereviewtask_jc51.presentation.usecases.GetCatFactsUseCase
import com.example.codereviewtask_jc51.presentation.usecases.RefreshFavouriteStatusUseCase
import com.example.codereviewtask_jc51.presentation.usecases.SetCatFavouriteStatusUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class CatBreedsViewModel @Inject constructor(
    private val getCatBreedsUseCase: GetCatBreedsUseCase,
    private val setCatFavouriteStatusUseCase: SetCatFavouriteStatusUseCase,
    private val refreshFavouriteStatusUseCase: RefreshFavouriteStatusUseCase,
    private val getCatFactsUseCase : GetCatFactsUseCase,
) : ViewModel() {

    private val disposables: CompositeDisposable = CompositeDisposable()

    private val _isLoading: MutableLiveData<Boolean> = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _breedsList = MutableLiveData<List<CatPreview>>(emptyList())
    val breedsList: LiveData<List<CatPreview>> = _breedsList

    private val _catFact = MutableLiveData<String>()
    val catFact: LiveData<String> = _catFact

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error


    init {
        loadBreeds()
        loadCatFacts()
    }

    fun loadBreeds() {
        getCatBreedsUseCase.collect(
            scope = viewModelScope,
            param = Unit,
            data = _breedsList,
            error = _error,
            loading = _isLoading,
        )
    }

    fun changeCatBreedFavouriteStatus(cat: CatPreview) {
        cat.favorite = !cat.favorite
        setCatFavouriteStatusUseCase(
            SetCatFavouriteStatusUseCase.Params(
                catId = cat.id,
                favourite = cat.favorite
            )
        )
    }


    fun refreshFavouriteStatus(){
        breedsList.value?.let{
            refreshFavouriteStatusUseCase.collect(
                scope = viewModelScope,
                param = RefreshFavouriteStatusUseCase.Params(
                    breeds = it
                ),
                data = _breedsList
            )
        }
    }

    internal fun loadCatFacts() {
        getCatFactsUseCase.collect(
            scope = viewModelScope,
            param = GetCatFactsUseCase.Params(
                start = 1,
                count = 8,
                initialDelay = 0,
                period = 25
            ),
            data = _catFact,
        )
    }

    override fun onCleared() {
        super.onCleared()
        disposables.dispose()
    }
}
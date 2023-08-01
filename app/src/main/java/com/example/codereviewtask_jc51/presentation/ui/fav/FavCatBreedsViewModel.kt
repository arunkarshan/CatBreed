package com.example.codereviewtask_jc51.presentation.ui.fav

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.codereviewtask_jc51.domain.repository.CatFavouriteRepository
import com.example.codereviewtask_jc51.domain.repository.CatRepository
import com.example.codereviewtask_jc51.domain.utils.collect
import com.example.codereviewtask_jc51.presentation.entity.CatPreview
import com.example.codereviewtask_jc51.presentation.entity.toUi
import com.example.codereviewtask_jc51.presentation.usecases.GetCatBreedsUseCase
import com.example.codereviewtask_jc51.presentation.usecases.GetFavouriteCatsUseCase
import com.example.codereviewtask_jc51.presentation.usecases.RefreshFavouriteStatusUseCase
import com.example.codereviewtask_jc51.presentation.usecases.SetCatFavouriteStatusUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.Singles
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class FavCatBreedsViewModel @Inject constructor(
    private val getFavouriteCatsUseCase: GetFavouriteCatsUseCase,
    private val setCatFavouriteStatusUseCase: SetCatFavouriteStatusUseCase,
    private val refreshFavouriteStatusUseCase: RefreshFavouriteStatusUseCase,
) : ViewModel() {

    private val _favoriteCats = MutableLiveData<List<CatPreview>>(emptyList())
    val favoriteCats: LiveData<List<CatPreview>> = _favoriteCats

    private val _isLoading: MutableLiveData<Boolean> = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    init {
        loadFavorites()
    }

    fun loadFavorites() {
        getFavouriteCatsUseCase.collect(
            scope = viewModelScope,
            param = Unit,
            data = _favoriteCats,
            loading = _isLoading,
        )
    }

    fun removeCatBreedFromFavourites(cat: CatPreview) {
        _favoriteCats.value = favoriteCats.value?.toMutableList()?.apply {
            remove(cat)
        }
        setCatFavouriteStatusUseCase(
            SetCatFavouriteStatusUseCase.Params(
                catId = cat.id,
                favourite = false
            )
        )
    }

    fun refreshFavouriteStatus(){
        favoriteCats.value?.let{
            refreshFavouriteStatusUseCase.collect(
                scope = viewModelScope,
                param = RefreshFavouriteStatusUseCase.Params(
                    breeds = it
                ),
                successListener = {
                    _favoriteCats.postValue(it.filter { cat -> cat.favorite })
                }
            )
        }
    }
}
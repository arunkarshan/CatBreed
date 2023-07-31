package com.example.codereviewtask_jc51.presentation.fav

import android.os.Bundle
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.savedstate.SavedStateRegistryOwner
import com.example.codereviewtask_jc51.data.CatRepository
import com.example.codereviewtask_jc51.data.FavoriteCatSaver
import com.example.codereviewtask_jc51.domain.model.CatPreview
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.Singles
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.schedulers.Schedulers
import timber.log.Timber

class FavCatBreedsViewModel(
    private val catRepository: CatRepository,
    private val favoriteCatSaver: FavoriteCatSaver
): ViewModel() {

    private val disposables: CompositeDisposable = CompositeDisposable()

    private val _favoriteCats = MutableLiveData<List<CatPreview>>(emptyList())
    val favoriteCats: LiveData<List<CatPreview>> = _favoriteCats

    private val _isLoading: MutableLiveData<Boolean> = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    init {
        loadFavorites()
    }

    fun loadFavorites() {
        _isLoading.value = true
        Singles.zip(
            catRepository.fetchCats(),
            favoriteCatSaver.getFavoriteCats().firstOrError()
        )
            .map { (cats, favoriteIds) ->
                cats.filter { it.id.toString() in favoriteIds }.map { it.apply { favorite = true }}
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    _favoriteCats.value = it
                    _isLoading.value = false
                },
                {
                    Timber.e("failed to download cat breeds", it)
                    it.printStackTrace()
                    _isLoading.value = false
                }
            ).addTo(disposables)
    }

    override fun onCleared() {
        super.onCleared()
        disposables.dispose()
    }
    fun removeCatBreedFromFavourites(cat: CatPreview) {
        favoriteCatSaver.run{
            if(cat.favorite) removeFavoriteCat(cat.id)
            _favoriteCats.value = favoriteCats.value?.toMutableList()?.apply {
                remove(cat)
            }
        }
    }


    companion object {
        fun provideFactory(
            catRepository: CatRepository,
            favoriteCatSaver: FavoriteCatSaver,
            owner: SavedStateRegistryOwner,
            defaultArgs: Bundle? = null,
        ): AbstractSavedStateViewModelFactory =
            object : AbstractSavedStateViewModelFactory(owner, defaultArgs) {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(
                    key: String,
                    modelClass: Class<T>,
                    handle: SavedStateHandle
                ): T {
                    return FavCatBreedsViewModel(catRepository, favoriteCatSaver) as T
                }
            }
    }
}
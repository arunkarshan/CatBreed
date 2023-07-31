package com.example.codereviewtask_jc51.presentation.details

import android.os.Bundle
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.savedstate.SavedStateRegistryOwner
import com.example.codereviewtask_jc51.data.CatRepository
import com.example.codereviewtask_jc51.data.FavoriteCatSaver
import com.example.codereviewtask_jc51.data.remote.dto.CatDetailsResponse
import com.example.codereviewtask_jc51.domain.model.CatDetails
import com.example.codereviewtask_jc51.domain.model.CatPreview
import com.example.codereviewtask_jc51.presentation.breeds.CatBreedsViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.kotlin.Singles
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.schedulers.Schedulers

class CatDetailsViewModel(
    private val repository: CatRepository,
    private val favoriteCatSaver: FavoriteCatSaver,
    private val catId: Int
) : ViewModel() {

    private val disposables: CompositeDisposable = CompositeDisposable()

    private val _details = MutableLiveData<CatDetails>()
    val details: LiveData<CatDetails> = _details

    init {
        fetchCatDetails()
    }

    private fun fetchCatDetails() {
        if (catId == 0) {
            _details.value = CatDetails.EMPTY
        }
        Singles.zip(
            repository.fetchCat(catId),
            favoriteCatSaver.isFavouriteCat(catId)
        ).map { (cat, isFavourite) ->
            cat.apply { favorite = isFavourite }
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { _details.postValue(it) },
                { _details.value = CatDetails.EMPTY }
            ).addTo(disposables)
    }


    fun changeCatBreedFavouriteStatus(cat: CatDetails) {
        favoriteCatSaver.run {
            if (cat.favorite) removeFavoriteCat(cat.id) else addFavoriteCat(cat.id)
            _details.value = cat.apply {
                favorite = !favorite
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        disposables.dispose()
    }

    companion object {
        fun provideFactory(
            catRepository: CatRepository,
            favoriteCatSaver: FavoriteCatSaver,
            catId: Int,
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
                    return CatDetailsViewModel(catRepository, favoriteCatSaver, catId) as T
                }
            }
    }
}
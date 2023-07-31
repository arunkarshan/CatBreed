package com.example.codereviewtask_jc51.presentation.breeds

import android.os.Bundle
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.savedstate.SavedStateRegistryOwner
import com.example.codereviewtask_jc51.data.CatRepository
import com.example.codereviewtask_jc51.data.FavoriteCatSaver
import com.example.codereviewtask_jc51.domain.model.CatPreview
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.kotlin.Singles
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.schedulers.Schedulers
import timber.log.Timber
import java.util.concurrent.TimeUnit

class CatBreedsViewModel(
    private val catRepository: CatRepository,
    private val favoriteCatSaver: FavoriteCatSaver
) : ViewModel() {

    private val disposables: CompositeDisposable = CompositeDisposable()

    private val _isLoading: MutableLiveData<Boolean> = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _breedsList = MutableLiveData<List<CatPreview>>(emptyList())
    val breedsList: LiveData<List<CatPreview>> = _breedsList

    private val _catFact = MutableLiveData<String>()
    val catFact: LiveData<String> = _catFact

    init {
        loadBreeds()
        loadCatFacts()
    }

    fun loadBreeds() {
        Timber.e("Loading list")
        _isLoading.value = true
        Singles.zip(
            catRepository.fetchCats(),
            favoriteCatSaver.getFavoriteCats().firstOrError()
        )
            .map { (cats, favoriteIds) ->
                cats.map { cat ->
                    cat.apply { favorite = cat.id.toString() in favoriteIds }
                }
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    _isLoading.value = false
                    _breedsList.value = it
                },
                {
                    Timber.e("failed to download cat breeds", it)
                    it.printStackTrace()
                    _isLoading.value = false
                }
            ).addTo(disposables)
    }

    fun changeCatBreedFavouriteStatus(cat: CatPreview) {
        favoriteCatSaver.run{
            if(cat.favorite) removeFavoriteCat(cat.id) else addFavoriteCat(cat.id)
            cat.favorite = !cat.favorite
        }
    }


    fun refreshFavouriteStatus(){
        favoriteCatSaver.getFavoriteCats()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ favs ->
                _breedsList.value = _breedsList.value?.apply {
                    map{it.favorite = it.id.toString() in favs}
                }
            }, {it.printStackTrace()}).addTo(disposables)
    }

    private fun loadCatFacts() {
        Observable.intervalRange(
            1,
            8,
            0,
            25,
            TimeUnit.SECONDS,
            Schedulers.computation()
        )
            .repeat()
            .flatMap { catRepository.fetchCatFact(it.toInt()).toObservable() }
            .subscribeOn(Schedulers.single())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { _catFact.postValue(it) },
                { Timber.e("failed to fetch cat fact"); it.printStackTrace() }
            ).addTo(disposables)
    }

    override fun onCleared() {
        super.onCleared()
        disposables.dispose()
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
                    return CatBreedsViewModel(catRepository, favoriteCatSaver) as T
                }
            }
    }
}
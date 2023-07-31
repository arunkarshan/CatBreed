package com.example.codereviewtask_jc51.presentation.breeds

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.codereviewtask_jc51.data.CatRepository
import com.example.codereviewtask_jc51.data.FavoriteCatSaver
import com.example.codereviewtask_jc51.domain.model.CatPreview
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.kotlin.Singles
import io.reactivex.rxjava3.schedulers.Schedulers
import timber.log.Timber
import java.util.concurrent.TimeUnit

class CatBreedsViewModel(
    private val catRepository: CatRepository,
    private val favoriteCatSaver: FavoriteCatSaver
): ViewModel() {

    private var runningSubscription: Disposable? = null

    private val _breedsList = MutableLiveData<List<CatPreview>>()
    val breedsList: LiveData<List<CatPreview>> = _breedsList

    private val _catFact = MutableLiveData<String>()
    val catFact: LiveData<String> = _catFact

    init {
        _breedsList.value = emptyList()
    }

    fun loadBreeds() {
        runningSubscription = Singles.zip(
            catRepository.fetchCats(),
            favoriteCatSaver.getFavoriteCat().firstOrError()
        )
            .map { (cats, favoriteId) ->
                cats.map { cat ->
                    cat.copy().also {
                        it.favorite = cat.id == favoriteId
                    }
                }
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { _breedsList.value = it },
                {
                    Timber.e("failed to download cat breeds", it)
                    it.printStackTrace()
                    _breedsList.value = emptyList()
                }
            )
    }

    fun setFavoriteCatBreed(catId: Int) {
        favoriteCatSaver.addFavoriteCat(catId)
        loadBreeds()
    }

    fun loadCatFacts() {
        runningSubscription = Observable.intervalRange(
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
            )
    }

    override fun onCleared() {
        super.onCleared()
        runningSubscription?.dispose()
    }




}
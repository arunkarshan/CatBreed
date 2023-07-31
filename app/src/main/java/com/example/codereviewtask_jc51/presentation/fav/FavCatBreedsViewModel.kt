package com.example.codereviewtask_jc51.presentation.fav

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.codereviewtask_jc51.data.CatRepository
import com.example.codereviewtask_jc51.data.FavoriteCatSaver
import com.example.codereviewtask_jc51.domain.model.CatPreview
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.kotlin.Singles
import io.reactivex.rxjava3.schedulers.Schedulers
import timber.log.Timber

class FavCatBreedsViewModel(
    private val catRepository: CatRepository,
    private val favoriteCatSaver: FavoriteCatSaver
): ViewModel() {

    private var runningSubscription: Disposable? = null

    private val _favoriteCats = MutableLiveData<List<CatPreview>>()
    val favoriteCats: LiveData<List<CatPreview>> = _favoriteCats

    init {
        _favoriteCats.value = emptyList()
    }

    fun loadFavorites() {
        runningSubscription = Singles.zip(
            catRepository.fetchCats(),
            favoriteCatSaver.getFavoriteCat().firstOrError()
        )
            .map { (cats, favoriteId) ->
                cats.filter { it.id == favoriteId }
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { _favoriteCats.value = it },
                {
                    Timber.e("failed to download cat breeds", it)
                    _favoriteCats.value = emptyList()
                }
            )
    }

    override fun onCleared() {
        super.onCleared()
        runningSubscription?.dispose()
    }
}
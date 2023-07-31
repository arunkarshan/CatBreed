package com.example.codereviewtask_jc51.presentation.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.codereviewtask_jc51.data.CatRepository
import com.example.codereviewtask_jc51.data.remote.dto.CatDetailsResponse
import com.example.codereviewtask_jc51.domain.model.CatDetails
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers

class CatDetailsViewModel(
    private val repository: CatRepository
): ViewModel() {

    private var runningSubscription: Disposable? = null

    private val _details = MutableLiveData<CatDetails>()
    val details: LiveData<CatDetails> = _details

    fun fetchCatDetails(catId: Long) {
        if (catId == 0L) {
            return
        }
        runningSubscription = repository.fetchCat(catId.toInt())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { _details.postValue(it) },
                { _details.value = CatDetails.EMPTY }
            )
    }

    override fun onCleared() {
        super.onCleared()
        runningSubscription?.dispose()
    }

}
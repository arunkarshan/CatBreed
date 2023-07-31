package com.example.codereviewtask_jc51.domain.utils

import androidx.lifecycle.MutableLiveData
import com.example.codereviewtask_jc51.domain.usecase.BaseFlowUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

fun <P, R> BaseFlowUseCase<P, R>.collect(
    scope: CoroutineScope,
    param: P,
    data: MutableLiveData<R>? = null,
    successListener: ((R) -> Unit)? = null,
    error: MutableLiveData<String?>? = null,
    errorListener: ((String?) -> Unit)? = null,
    loading: MutableLiveData<Boolean>? = null,
    loadingListener: ((Boolean) -> Unit)? = null,
) {
    collect(
        scope = scope,
        param = param,
        successListener = {
            data?.postValue(it)
            successListener?.invoke(it)
        },
        errorListener = {
            error?.postValue(it)
            errorListener?.invoke(it)
        },
        loadingListener = {
            loading?.postValue(it)
            loadingListener?.invoke(it)
        }
    )
}


/***************************************
 * Setting Observers
 ***************************************/
private fun <P, R> BaseFlowUseCase<P, R>.collect(
    scope: CoroutineScope,
    param: P,
    successListener: (R) -> Unit,
    errorListener: ((String?) -> Unit)? = null,
    loadingListener: ((Boolean) -> Unit)? = null,
) {
    loadingListener?.invoke(true)
    scope.launch(getDispatcher()) {
        invoke(param).catch {
            it.printStackTrace()
            errorListener?.invoke(it.message)
            loadingListener?.invoke(false)
        }.collect {
            successListener.invoke(it)
            delay(100)
            loadingListener?.invoke(false)
        }
    }
}
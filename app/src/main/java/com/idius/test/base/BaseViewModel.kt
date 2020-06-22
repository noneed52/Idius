package com.idius.test.base

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.idius.test.network.ApiClient
import io.reactivex.disposables.CompositeDisposable

abstract class BaseViewModel(application: Application): AndroidViewModel(application) {
    /**
     * Rx disposable
     **/
    val compositeDisposable by lazy { CompositeDisposable() }

    /**
     * Retrofit client
     **/
    val apiClient by lazy { ApiClient.getClient() }

    val callback by lazy { MutableLiveData<Int>() }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }
}
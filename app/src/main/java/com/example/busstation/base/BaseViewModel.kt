package com.example.busstation.base

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.busstation.network.ApiClient
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

    /**
     * activity callback
     **/
    val activityCallback by lazy { MutableLiveData<Int>() }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }
}
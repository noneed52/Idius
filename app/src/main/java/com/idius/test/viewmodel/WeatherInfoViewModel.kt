package com.idius.test.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.idius.test.base.BaseActivity
import com.idius.test.base.BaseViewModel
import com.idius.test.model.CityInfoResponse
import com.idius.test.model.WeatherInfoResponse
import com.idius.test.network.WeatherInfoService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers

class WeatherInfoViewModel(private val activity: BaseActivity<*>) : BaseViewModel(activity.application) {

    private val apiService = apiClient.create(WeatherInfoService::class.java)

    private lateinit var weatherInfoList: ArrayList<CityInfoResponse>
    private val _weatherInfoList by lazy { MutableLiveData<ArrayList<CityInfoResponse>>(ArrayList()) }
    val weatherInfoListLiveData: LiveData<ArrayList<CityInfoResponse>> = _weatherInfoList

    fun requestWeatherInfo(cityName: String, showLoading: Boolean) {
        apiService.getWeatherCityInfo(cityName)
            .flattenAsFlowable {
                weatherInfoList = it
                weatherInfoList
            }
            .parallel()
            .runOn(Schedulers.io())
            .flatMap { apiService.getWeatherInfo(it.woeid).toFlowable() }
            .sequential()
            .doOnSubscribe { if(showLoading) activity.showLoading() }
            .doOnTerminate { if(showLoading) activity.hideLoading() }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(::parseWeatherInfo, activity::errorHandler, ::parsingWeatherInfoComplete)
            .addTo(compositeDisposable)
    }

    private fun parseWeatherInfo(weatherInfoResponse: WeatherInfoResponse) {
        weatherInfoResponse.consolidatedWeather = weatherInfoResponse.consolidatedWeather.sortedBy { it.applicableDate }
        weatherInfoList.first { it.woeid == weatherInfoResponse.woeid }.weatherInfo = weatherInfoResponse
    }

    private fun parsingWeatherInfoComplete() {
        callback.postValue(1)
        _weatherInfoList.postValue(weatherInfoList)
    }
}
package com.example.busstation.viewmodel

import androidx.databinding.ObservableArrayList
import com.example.busstation.base.BaseActivity
import com.example.busstation.base.BaseViewModel
import com.example.myapplication.model.*
import com.example.myapplication.network.BusStationInfoService
import com.example.myapplication.ui.activity.MapsActivity.Companion.BUS_ARRIVAL_INFO_COMPLETE
import com.example.myapplication.ui.activity.MapsActivity.Companion.BUS_ROUTE_INFO
import com.example.myapplication.ui.activity.MapsActivity.Companion.BUS_STATION_AROUND_COMPLETE
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Action
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers

/**
 * @file BusViewModel.kt
 * @date 06/18/2019
 * @brief A class containing a single appliance's data
 * @copyright GE Appliances, a Haier Company (Confidential). All rights reserved.
 */

class BusViewModel(private val activity: com.example.busstation.base.BaseActivity<*>) : com.example.busstation.base.BaseViewModel(activity.application) {

    private val apiService = apiClient.create(BusStationInfoService::class.java)
    var busStationAroundInfoList: List<BusStationAround>? = null
    var selectedBusStation: BusStationAround? = null
    var busStationArrivalList: List<BusStationArrival>? = null
    val busInfoList by lazy { ObservableArrayList<BusInfo?>() }
    var selectedBusInfo: BusInfo? = null
    val busRouteList by lazy { ObservableArrayList<BusRoute>() }

    /**
     *
     */
    fun requestBusStationList(latitude: Double, longitude: Double) {
        apiService.getBusStationList(longitude.toString(), latitude.toString())
            .doOnSubscribe { activity.showLoading() }
            .doOnTerminate { activity.hideLoading() }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(::parseBusStationList, activity::errorHandler)
            .addTo(compositeDisposable)
    }

    /**
     *
     */
    private fun parseBusStationList(busStationAroundResponse: BusStationAroundResponse) {
        busStationAroundResponse.run {
            if (comMsgHeader.returnCode == "00") {
                busStationAroundInfoList = msgBody?.busStationAroundList
                activityCallback.postValue(BUS_STATION_AROUND_COMPLETE)
            } else {
                activity.hideLoading()
                activity.toast(busStationAroundResponse.comMsgHeader.errMsg ?: "ERROR: parseBusStationList")
            }
        }
    }

    fun getBusStationInfo(markerTitle: String) {
        busStationAroundInfoList?.first {
            it.stationName == markerTitle
        }?.run {
            selectedBusStation = this
            requestBusStationInfo(this, Action {
                activityCallback.postValue(BUS_ARRIVAL_INFO_COMPLETE)
            })
        }
    }

    private fun requestBusStationInfo(busStation: BusStationAround?, requestCompleteCallback: Action? = null) {
        busStation?.let {
            apiService.getBusStationArrivalInfo(it.stationId)
                .flatMap(::parseBusStationInfo)
                .doOnError { activity.hideLoading() }
                .toObservable()
                .flatMap(::requestBusInfo)
                .doOnSubscribe { activity.showLoading() }
                .doOnTerminate { activity.hideLoading() }
                .doOnError { activity.hideLoading() }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(::parseBusInfo, activity::errorHandler) {
                    busInfoList.sortBy { busInfo ->  busInfo?.routeName }
                    requestCompleteCallback?.run()
                }
                .addTo(compositeDisposable)
        }
    }

    private fun parseBusStationInfo(busStationArrivalInfoResponse: BusStationArrivalInfoResponse): Single<List<BusStationArrival>?> {
        return busStationArrivalInfoResponse.run {
            if (comMsgHeader.returnCode == "00") {
                msgBody?.let {
                    Single.just(it.busArrivalList)
                }?: Single.error(Throwable(msgHeader.resultMessage))
            } else {
                Single.error(Throwable(comMsgHeader.errMsg))
            }
        }
    }

    private fun requestBusInfo(busStationArrivalList: List<BusStationArrival>?): Observable<BusInfoResponse> {
        this.busStationArrivalList = busStationArrivalList
        return busStationArrivalList?.let { busStationList ->
            busInfoList.clear()
            Observable.fromIterable(busStationList)
                .map { it.routeId }
                .flatMapSingle { apiService.getBusInfo(it) }
        }?: Observable.error<BusInfoResponse>(Throwable("BUS_INFO_ERROR"))
    }

    private fun parseBusInfo(busInfoResponse: BusInfoResponse) {
        busInfoResponse.run {
            if (comMsgHeader.returnCode == "00") {
                busStationArrivalList?.first {
                    it.routeId == msgBody?.busRouteInfoItem?.routeId
                }?.run {
                    busInfoList.add(msgBody?.busRouteInfoItem)
                }
            } else {
                activity.hideLoading()
                activity.toast(comMsgHeader.errMsg?: "ERROR: parseBusInfo")
            }
        }
    }

    fun refreshBusArrivalInfo(unit: Unit) {
        requestBusStationInfo(selectedBusStation)
    }

    fun requestBusRouteInfo(busInfo: BusInfo) {
        selectedBusInfo = busInfo
        activityCallback.postValue(BUS_ROUTE_INFO)
        apiService.getBusRouteInfo(busInfo.routeId)
            .doOnSubscribe { activity.showLoading() }
            .doOnSuccess { activity.hideLoading() }
            .doOnError { activity.hideLoading() }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(::parseBusRouteInfo, activity::errorHandler)
            .addTo(compositeDisposable)
    }

    private fun parseBusRouteInfo(busRouteInfoResponse: BusRouteInfoResponse) {
        busRouteInfoResponse.run {
            if(comMsgHeader.returnCode == "00") {
                msgBody?.busRouteStationList?.let {
                    busRouteList.addAll(it.toList())
                }
            } else {
                activity.hideLoading()
                activity.toast(comMsgHeader.errMsg?: "ERROR: parseBusRouteInfo")
            }
        }
    }
}
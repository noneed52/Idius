package com.example.busstation.network

import com.example.busstation.constants.ApiKeys
import com.example.busstation.model.BusInfoResponse
import com.example.busstation.model.BusRouteInfoResponse
import com.example.busstation.model.BusStationAroundResponse
import com.example.busstation.model.BusStationArrivalInfoResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface BusStationInfoService {

    /**
     * Get bus station around the location
     * @param x: logitude of the location
     * @param y: latitude of the location
     * @param serviceKey: Api key
     */
    @GET(com.example.busstation.constants.ApiAddress.BUS_STATION_LIST)
    fun getBusStationList(
        @Query("x") x: String,
        @Query("y") y: String,
        @Query("serviceKey", encoded = true) serviceKey: String = ApiKeys.BUS_STATION_API_KEY
    ): Single<BusStationAroundResponse>

    /**
     * Get arriving info of buses at station
     * @param stationId: station identity key
     * @param serviceKey: api key
     */
    @GET(com.example.busstation.constants.ApiAddress.BUS_STATION_ARRIVAL_TIME_ALL)
    fun getBusStationArrivalInfo(
        @Query("stationId") stationId: String,
        @Query("serviceKey", encoded = true) serviceKey: String = ApiKeys.BUS_ARRIVAL_TIME_API_KEY
    ): Single<BusStationArrivalInfoResponse>

    /**
     * Get bus info
     * @param routeId: bus route key
     * @param serviceKey: api key
     */
    @GET(com.example.busstation.constants.ApiAddress.BUS_INFO)
    fun getBusInfo(
        @Query("routeId") routeId: String,
        @Query("serviceKey", encoded = true) serviceKey: String = ApiKeys.BUS_INFO_API_KEY
    ): Single<BusInfoResponse>

    /**
     * Get list of bus stations in bus route
     * @param routeId: bus route key
     * @param serviceKey: api key
     */
    @GET(com.example.busstation.constants.ApiAddress.BUS_ROUTE_STATION_INFO)
    fun getBusRouteInfo(
        @Query("routeId") routeId: String,
        @Query("serviceKey", encoded = true) serviceKey: String = ApiKeys.BUS_INFO_API_KEY
    ): Single<BusRouteInfoResponse>
}
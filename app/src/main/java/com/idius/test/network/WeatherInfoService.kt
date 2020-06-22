package com.idius.test.network

import com.idius.test.constants.ApiAddress
import com.idius.test.model.CityInfoResponse
import com.idius.test.model.WeatherInfoResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface WeatherInfoService {

    @GET(ApiAddress.API_WEATHER_CITY_INFO)
    fun getWeatherCityInfo(
        @Query("query") cityName: String
    ): Single<ArrayList<CityInfoResponse>>

    @GET(ApiAddress.API_WEATHER_INFO)
    fun getWeatherInfo(
        @Path("woeid") woeid: Int
    ): Single<WeatherInfoResponse>
}
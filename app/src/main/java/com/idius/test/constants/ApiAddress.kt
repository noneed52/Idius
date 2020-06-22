package com.idius.test.constants

object ApiAddress {

    const val BASE_URL = "https://www.metaweather.com/"
    private const val API_BASE_URL = BASE_URL + "api/"
    const val API_WEATHER_CITY_INFO = API_BASE_URL + "location/search"
    const val API_WEATHER_INFO = API_BASE_URL + "location/{woeid}/"
}
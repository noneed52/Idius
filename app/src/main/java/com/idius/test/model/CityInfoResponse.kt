package com.idius.test.model
import com.google.gson.annotations.SerializedName


data class CityInfoResponse(
    @SerializedName("latt_long")
    val lattLong: String,
    @SerializedName("location_type")
    val locationType: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("woeid")
    val woeid: Int,
    @Transient
    var weatherInfo: WeatherInfoResponse
)
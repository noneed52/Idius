package com.idius.test.model
import com.google.gson.annotations.SerializedName


data class WeatherInfoResponse(
    @SerializedName("consolidated_weather")
    var consolidatedWeather: List<ConsolidatedWeather>,
    @SerializedName("latt_long")
    val lattLong: String,
    @SerializedName("location_type")
    val locationType: String,
    @SerializedName("parent")
    val parent: Parent,
    @SerializedName("sources")
    val sources: List<Source>,
    @SerializedName("sun_rise")
    val sunRise: String,
    @SerializedName("sun_set")
    val sunSet: String,
    @SerializedName("time")
    val time: String,
    @SerializedName("timezone")
    val timezone: String,
    @SerializedName("timezone_name")
    val timezoneName: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("woeid")
    val woeid: Int
)

data class ConsolidatedWeather(
    @SerializedName("air_pressure")
    val airPressure: Double,
    @SerializedName("applicable_date")
    val applicableDate: String,
    @SerializedName("created")
    val created: String,
    @SerializedName("humidity")
    val humidity: Int,
    @SerializedName("id")
    val id: Long,
    @SerializedName("max_temp")
    val maxTemp: Double,
    @SerializedName("min_temp")
    val minTemp: Double,
    @SerializedName("predictability")
    val predictability: Int,
    @SerializedName("the_temp")
    val theTemp: Double,
    @SerializedName("visibility")
    val visibility: Double,
    @SerializedName("weather_state_abbr")
    val weatherStateAbbr: String,
    @SerializedName("weather_state_name")
    val weatherStateName: String,
    @SerializedName("wind_direction")
    val windDirection: Double,
    @SerializedName("wind_direction_compass")
    val windDirectionCompass: String,
    @SerializedName("wind_speed")
    val windSpeed: Double
)

data class Parent(
    @SerializedName("latt_long")
    val lattLong: String,
    @SerializedName("location_type")
    val locationType: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("woeid")
    val woeid: Int
)

data class Source(
    @SerializedName("crawl_rate")
    val crawlRate: Int,
    @SerializedName("slug")
    val slug: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("url")
    val url: String
)
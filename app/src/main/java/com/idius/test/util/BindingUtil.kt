package com.idius.test.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.idius.test.constants.ImagePath
import com.idius.test.model.CityInfoResponse
import com.idius.test.ui.adapter.WeatherInfoListAdapter

object BindingUtil {
    @JvmStatic @BindingAdapter("bindWeatherInfoList")
    fun bindWeatherInfoList(recyclerView: RecyclerView, weatherInfoList: LiveData<ArrayList<CityInfoResponse>>) {
        val adapter = recyclerView.adapter as WeatherInfoListAdapter?
        adapter?.bindCityInfoList(weatherInfoList.value!!)
    }

    @JvmStatic @BindingAdapter("bindWeatherIcon")
    fun bindWeatherIcon(imageView: ImageView, weatherStateAbr: String) {
        Glide.with(imageView.context)
            .load(ImagePath.WEATHER_ICON_64.format(weatherStateAbr))
            .into(imageView)
    }
}

object Util {
    @JvmStatic
    fun getTempString(temp: Double) = "${temp.toInt()}\u2103"

    @JvmStatic
    fun getHumidityString(humidity: Int) = "${humidity}%"
}

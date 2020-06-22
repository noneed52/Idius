package com.idius.test.ui.viewholder

import com.idius.test.base.BaseViewHolder
import com.idius.test.databinding.LayoutCityWeatherInfoItemBinding
import com.idius.test.model.CityInfoResponse

class WeatherInfoViewHolder(private val binding: LayoutCityWeatherInfoItemBinding,
                            private val cityInfoList: ArrayList<CityInfoResponse>
): BaseViewHolder(binding.root) {

    override fun onBindViewHolder(position: Int) {
        binding.run {
            cityInfo = cityInfoList[position]
            cityInfo?.run {
                cityNameTv.text = title
                todayWeatherInfo = weatherInfo.consolidatedWeather[0]
                tomorrowWeatherInfo = weatherInfo.consolidatedWeather[1]
            }
        }
    }
}
package com.idius.test.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.idius.test.base.BaseViewHolder
import com.idius.test.ui.viewholder.WeatherInfoViewHolder
import com.idius.test.viewmodel.WeatherInfoViewModel
import com.idius.test.databinding.LayoutCityWeatherInfoItemBinding
import com.idius.test.model.CityInfoResponse

/**
 * @file BusInfoListAdapter.kt
 * @date 06/21/2019
 * @brief A class containing a single appliance's data
 * @copyright GE Appliances, a Haier Company (Confidential). All rights reserved.
 */

class WeatherInfoListAdapter(private val weatherInfoViewModel: WeatherInfoViewModel): RecyclerView.Adapter<BaseViewHolder>() {

    private val cityInfoList by lazy { ArrayList<CityInfoResponse>() }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val binding = LayoutCityWeatherInfoItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WeatherInfoViewHolder(
            binding,
            cityInfoList)
    }

    override fun getItemCount(): Int {
        return cityInfoList.size
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.onBindViewHolder(position)
    }

    fun bindCityInfoList(cityInfoList: ArrayList<CityInfoResponse>) {
        if(cityInfoList.isNotEmpty()) {
            this.cityInfoList.clear()
            this.cityInfoList.addAll(cityInfoList)
            notifyDataSetChanged()
        }
    }
}
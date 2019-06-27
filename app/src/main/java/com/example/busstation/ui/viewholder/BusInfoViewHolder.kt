package com.example.busstation.ui.viewholder

import android.view.View.GONE
import com.example.myapplication.R
import com.example.busstation.base.BaseViewHolder
import com.example.busstation.model.BusInfo
import com.example.busstation.viewmodel.BusViewModel
import com.example.myapplication.databinding.LayoutBusInfoItemBinding
import com.jakewharton.rxbinding2.view.clicks
import io.reactivex.rxkotlin.addTo
import java.util.concurrent.TimeUnit

/**
 * @file BusInfoViewHolder.kt
 * @date 06/21/2019
 * @brief A class containing a single appliance's data
 * @copyright GE Appliances, a Haier Company (Confidential). All rights reserved.
 */

class BusInfoViewHolder(private val binding: LayoutBusInfoItemBinding,
                        private val busInfoList: ArrayList<BusInfo>,
                        private val busViewModel: BusViewModel): BaseViewHolder(binding.root) {

    override fun onBindViewHolder(position: Int) {
        binding.run {
            busInfo = busInfoList[position]
            busViewModel.busStationArrivalList!![position].run {
                busStationArrival = this
                executePendingBindings()
                if(locationNo1.isBlank() || predictTime1.isBlank())
                    busArrivalInfo1Tv.text = context.getString(R.string.no_information)
                if(locationNo2.isBlank() || predictTime2.isBlank())
                    busArrivalInfo2Tv.text = context.getString(R.string.no_information)
                if(remainSeatCnt1 == "-1")
                    emptySeats1Tv.visibility = GONE
                if(remainSeatCnt2 == "-1")
                    emptySeats2Tv.visibility = GONE
            }
            root.clicks()
                .throttleFirst(1, TimeUnit.SECONDS)
                .map { busInfoList[position] }
                .subscribe(busViewModel::requestBusRouteInfo)
                .addTo(busViewModel.compositeDisposable)
        }
    }
}
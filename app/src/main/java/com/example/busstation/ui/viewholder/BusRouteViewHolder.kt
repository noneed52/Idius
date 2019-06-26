package com.example.busstation.ui.viewholder

import com.example.busstation.base.BaseViewHolder
import com.example.myapplication.databinding.LayoutBusRouteItemBinding
import com.example.myapplication.model.BusRoute
import com.example.myapplication.viewmodel.BusViewModel

/**
 * @file BusRouteViewHolder * @date 06/21/2019
 * @brief A class containing a single appliance's data
 * @copyright GE Appliances, a Haier Company (Confidential). All rights reserved.
 */

class BusRouteViewHolder(private val binding: LayoutBusRouteItemBinding,
                         private val busRouteInfoList: ArrayList<BusRoute>,
                         private val busViewModel: BusViewModel): com.example.busstation.base.BaseViewHolder(binding.root) {

    override fun onBindViewHolder(position: Int) {
        binding.run {
            busRoute = busRouteInfoList[position]
        }
    }
}
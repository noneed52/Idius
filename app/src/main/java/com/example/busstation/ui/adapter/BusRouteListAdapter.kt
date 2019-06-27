package com.example.busstation.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.busstation.base.BaseViewHolder
import com.example.busstation.model.BusRoute
import com.example.busstation.ui.viewholder.BusRouteViewHolder
import com.example.busstation.viewmodel.BusViewModel
import com.example.myapplication.databinding.LayoutBusRouteItemBinding

/**
 * @file BusInfoListAdapter.kt
 * @date 06/21/2019
 * @brief A class containing a single appliance's data
 * @copyright GE Appliances, a Haier Company (Confidential). All rights reserved.
 */

class BusRouteListAdapter(private val busViewModel: BusViewModel): RecyclerView.Adapter<BaseViewHolder>() {

    private val busRouteList by lazy { ArrayList<BusRoute>() }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val binding = LayoutBusRouteItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BusRouteViewHolder(binding, busRouteList, busViewModel)
    }

    override fun getItemCount(): Int {
        return busRouteList.size
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.onBindViewHolder(position)
    }

    fun bindBusRouteInfoList(busRouteList: ArrayList<BusRoute>?) {
        busRouteList?.let {
            this.busRouteList.run {
                clear()
                addAll(it)
                notifyDataSetChanged()
            }
        }
    }
}
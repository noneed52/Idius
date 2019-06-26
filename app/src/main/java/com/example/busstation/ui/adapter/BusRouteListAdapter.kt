package com.example.busstation.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.busstation.base.BaseViewHolder
import com.example.myapplication.databinding.LayoutBusRouteItemBinding
import com.example.myapplication.model.BusRoute
import com.example.myapplication.ui.viewholder.BusRouteViewHolder
import com.example.myapplication.viewmodel.BusViewModel

/**
 * @file BusInfoListAdapter.kt
 * @date 06/21/2019
 * @brief A class containing a single appliance's data
 * @copyright GE Appliances, a Haier Company (Confidential). All rights reserved.
 */

class BusRouteListAdapter(private val busViewModel: BusViewModel): RecyclerView.Adapter<com.example.busstation.base.BaseViewHolder>() {

    private val busRouteList by lazy { ArrayList<BusRoute>() }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): com.example.busstation.base.BaseViewHolder {
        val binding = LayoutBusRouteItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BusRouteViewHolder(binding, busRouteList, busViewModel)
    }

    override fun getItemCount(): Int {
        return busRouteList.size
    }

    override fun onBindViewHolder(holder: com.example.busstation.base.BaseViewHolder, position: Int) {
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
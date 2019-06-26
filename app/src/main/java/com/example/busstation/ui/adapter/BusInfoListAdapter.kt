package com.example.busstation.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.busstation.base.BaseViewHolder
import com.example.myapplication.databinding.LayoutBusInfoItemBinding
import com.example.myapplication.model.BusInfo
import com.example.myapplication.ui.viewholder.BusInfoViewHolder
import com.example.myapplication.viewmodel.BusViewModel

/**
 * @file BusInfoListAdapter.kt
 * @date 06/21/2019
 * @brief A class containing a single appliance's data
 * @copyright GE Appliances, a Haier Company (Confidential). All rights reserved.
 */

class BusInfoListAdapter(private val busViewModel: BusViewModel): RecyclerView.Adapter<com.example.busstation.base.BaseViewHolder>() {

    private val busInfoList by lazy { ArrayList<BusInfo>() }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): com.example.busstation.base.BaseViewHolder {
        val binding = LayoutBusInfoItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BusInfoViewHolder(binding, busInfoList, busViewModel)
    }

    override fun getItemCount(): Int {
        return busInfoList.size
    }

    override fun onBindViewHolder(holder: com.example.busstation.base.BaseViewHolder, position: Int) {
        holder.onBindViewHolder(position)
    }

    fun bindBusInfoList(busInfoList: ArrayList<BusInfo>?) {
        busInfoList?.let {
            this.busInfoList.run {
                clear()
                addAll(it)
                notifyDataSetChanged()
            }
        }
    }
}
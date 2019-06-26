package com.example.busstation.ui.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.databinding.LayoutBusStationInfoListBinding
import com.example.myapplication.ui.adapter.BusInfoListAdapter
import com.example.myapplication.viewmodel.BusViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.jakewharton.rxbinding2.view.clicks
import io.reactivex.rxkotlin.addTo
import java.util.concurrent.TimeUnit

/**
 * @file BusStationArrivalInfoDialog.kt
 * @date 06/21/2019
 * @brief A class containing a single appliance's data
 * @copyright GE Appliances, a Haier Company (Confidential). All rights reserved.
 */

class BusStationArrivalInfoDialog : BottomSheetDialogFragment() {

    companion object {
        fun newInstance(busViewModel: BusViewModel) = BusStationArrivalInfoDialog().apply {
            this.busViewModel = busViewModel
        }
    }

    lateinit var busViewModel: BusViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val viewDataBinding = LayoutBusStationInfoListBinding.inflate(inflater, container, false)
        viewDataBinding.busInfoRv.layoutManager = LinearLayoutManager(context)
        viewDataBinding.busInfoRv.adapter = BusInfoListAdapter(busViewModel)
        viewDataBinding.busInfoList = busViewModel.busInfoList
        viewDataBinding.titleTv.text = busViewModel.selectedBusStation?.stationName

        viewDataBinding.refreshTv.clicks()
            .throttleFirst(500, TimeUnit.MILLISECONDS)
            .subscribe(busViewModel::refreshBusArrivalInfo)
            .addTo(busViewModel.compositeDisposable)
        return viewDataBinding.root
    }
}
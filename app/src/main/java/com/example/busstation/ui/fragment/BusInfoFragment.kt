package com.example.busstation.ui.fragment


import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.busstation.ui.adapter.BusRouteListAdapter
import com.example.busstation.viewmodel.BusViewModel
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentBusRouteInfoBinding

class BusInfoFragment : com.example.busstation.base.BaseFragment<FragmentBusRouteInfoBinding>() {

    override val layoutId = R.layout.fragment_bus_route_info

    private val busViewModel by lazy {
        getAttachedActivity()?.let {
            it.getViewModel { BusViewModel(it) }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewDataBinding?.run {
            busViewModel?.let {
                busInfo = it.selectedBusInfo
                busRouteList = it.busRouteList
                busRouteInfoRv.layoutManager = LinearLayoutManager(context)
                busRouteInfoRv.adapter = BusRouteListAdapter(it)
            }
        }
    }
}

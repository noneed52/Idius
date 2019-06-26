package com.example.busstation.util

import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.databinding.ObservableArrayList
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.model.BusInfo
import com.example.myapplication.model.BusRoute
import com.example.myapplication.ui.adapter.BusInfoListAdapter
import com.example.myapplication.ui.adapter.BusRouteListAdapter

object BindingUtil {

    /**
     * Binds bus info list to recyclerview adapter
     */
    @JvmStatic @BindingAdapter("bindBusInfoList")
    fun bindBusInfoList(recyclerView: RecyclerView, busInfos: ObservableArrayList<BusInfo>?) {
        val adapter = recyclerView.adapter as BusInfoListAdapter?
        adapter?.bindBusInfoList(busInfos)
    }

    /**
     * Binds bus route list to recyclerview adapter
     */
    @JvmStatic @BindingAdapter("bindBusRouteList")
    fun bindBusRouteList(recyclerView: RecyclerView, busRouteInfos: ObservableArrayList<BusRoute>?) {
        val adapter = recyclerView.adapter as BusRouteListAdapter?
        adapter?.bindBusRouteInfoList(busRouteInfos)
    }

    /**
     * Binds bus number text color according to bus route type
     */
    @JvmStatic @BindingAdapter("bindBusColor")
    fun bindBusColor(textView: TextView, routeTypeCd: String) {
        val context = textView.context
        return when(routeTypeCd) {
            "11" -> textView.setTextColor(ContextCompat.getColor(context, android.R.color.holo_red_light))  //    11:직행좌석형시내버스
            "12" -> textView.setTextColor(ContextCompat.getColor(context, android.R.color.holo_red_light))  //    12:좌석형시내버스
            "13" -> textView.setTextColor(ContextCompat.getColor(context, android.R.color.holo_blue_dark))  //    13:일반형시내버스
            "14" -> textView.setTextColor(ContextCompat.getColor(context, android.R.color.holo_red_dark))    //    14:광역급행형시내버스
            "15" -> textView.setTextColor(ContextCompat.getColor(context, android.R.color.holo_green_light)) //    15:따복형 시내버스
            "16" -> textView.setTextColor(ContextCompat.getColor(context, android.R.color.holo_red_dark))    //    16:경기순환버스
            "21" -> textView.setTextColor(ContextCompat.getColor(context, android.R.color.holo_green_light)) //    21:직행좌석형농어촌버스
            "22" -> textView.setTextColor(ContextCompat.getColor(context, android.R.color.holo_green_light)) //    22:좌석형농어촌버스
            "23" -> textView.setTextColor(ContextCompat.getColor(context, android.R.color.holo_green_light)) //    23:일반형농어촌버스
            "30" -> textView.setTextColor(ContextCompat.getColor(context, android.R.color.holo_green_dark))  //    30:마을버스
            "41" -> textView.setTextColor(ContextCompat.getColor(context, android.R.color.holo_green_dark))  //    41:고속형시외버스
            "42" -> textView.setTextColor(ContextCompat.getColor(context, android.R.color.holo_green_dark))  //    42:좌석형시외버스
            "43" -> textView.setTextColor(ContextCompat.getColor(context, android.R.color.holo_green_dark))  //    43:일반형시외버스
            "51" -> textView.setTextColor(ContextCompat.getColor(context, android.R.color.holo_orange_dark)) //    51:리무진형공항버스
            "52" -> textView.setTextColor(ContextCompat.getColor(context, android.R.color.holo_orange_dark)) //    52:좌석형공항버스
            "53" -> textView.setTextColor(ContextCompat.getColor(context, android.R.color.holo_orange_dark)) //    53:일반형공항버스
            else -> textView.setTextColor(ContextCompat.getColor(context, android.R.color.black))
        }
    }
}

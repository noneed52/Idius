package com.idius.test.ui.fragment


import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.lifecycle.Observer
import com.idius.test.R
import com.idius.test.base.BaseFragment
import com.idius.test.databinding.FragmentCityWeatherInfoBinding
import com.idius.test.ui.adapter.WeatherInfoListAdapter
import com.idius.test.viewmodel.WeatherInfoViewModel

class CityWeatherInfoFragment : BaseFragment<FragmentCityWeatherInfoBinding>() {

    override val layoutId = R.layout.fragment_city_weather_info

    private val weatherInfoViewModel by lazy {
        getAttachedActivity()?.let {
            it.getViewModel { WeatherInfoViewModel(it) }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewDataBinding?.run {
            weatherInfoViewModel?.let {
                weatherInfoList = it.weatherInfoListLiveData
                weatherInfoRv.adapter = WeatherInfoListAdapter(it)
                it.callback.observe(viewLifecycleOwner, Observer {
                    headerGv.visibility = VISIBLE
                    if(pullRefresh.isRefreshing) {
                        weatherInfoRv.visibility = VISIBLE
                        pullRefresh.isRefreshing = false
                    }
                })
                pullRefresh.setOnRefreshListener {
                    if(pullRefresh.isRefreshing) {
                        weatherInfoRv.visibility = GONE
                        headerGv.visibility = GONE
                        it.requestWeatherInfo("se", false)
                    }
                }
                it.requestWeatherInfo("se", true)
            }
        }
    }
}

package com.idius.test.ui.activity

import android.os.Bundle
import com.idius.test.R
import com.idius.test.base.BaseActivity
import com.idius.test.databinding.ActivityWeatherInfoBinding
import com.idius.test.ui.fragment.CityWeatherInfoFragment

class WeatherInfoActivity : BaseActivity<ActivityWeatherInfoBinding>() {

    override val layoutId: Int = R.layout.activity_weather_info

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewDataBinding?.run {
            replaceFragment(fragmentContainerFl.id, CityWeatherInfoFragment(), "")
        }
    }
}
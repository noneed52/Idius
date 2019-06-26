package com.example.busstation.model

import com.tickaroo.tikxml.annotation.Element
import com.tickaroo.tikxml.annotation.PropertyElement
import com.tickaroo.tikxml.annotation.Xml

/**
 * @file BusStationAroundInfo.kt
 * @date 06/19/2019
 * @brief A class containing a single appliance's data
 * @copyright GE Appliances, a Haier Company (Confidential). All rights reserved.
 */

@Xml(name = "msgBody")
data class BusStationAroundInfo(
    @Element
    val busStationAroundList: List<BusStationAround>)

@Xml(name = "busStationAroundList")
data class BusStationAround(
    @PropertyElement
    val centerYn: String,
    @PropertyElement
    val districtCd: String,
    @PropertyElement
    val mobileNo: String,
    @PropertyElement
    val regionName: String,
    @PropertyElement
    val stationId: String,
    @PropertyElement
    val stationName: String,
    @PropertyElement
    val x: Double,
    @PropertyElement
    val y: Double,
    @PropertyElement
    val distance: String)
package com.example.busstation.model

import com.tickaroo.tikxml.annotation.Element
import com.tickaroo.tikxml.annotation.PropertyElement
import com.tickaroo.tikxml.annotation.Xml

/**
 * @file BusInfo.kt
 * @date 06/20/2019
 * @brief A class containing a single appliance's data
 * @copyright GE Appliances, a Haier Company (Confidential). All rights reserved.
 */

@Xml(name = "msgBody")
data class BusRouteInfoItem(
    @Element
    val busRouteInfoItem: BusInfo)

@Xml(name = "busRouteInfoItem")
data class BusInfo (
    @PropertyElement
    val companyId: String,
    @PropertyElement
    val companyName: String,
    @PropertyElement
    val companyTel: String,
    @PropertyElement
    val districtCd: String,
    @PropertyElement
    val downFirstTime: String,
    @PropertyElement
    val downLastTime: String,
    @PropertyElement
    val endMobileNo: String?,
    @PropertyElement
    val endStationId: String,
    @PropertyElement
    val endStationName: String,
    @PropertyElement
    val peekAlloc: String,
    @PropertyElement
    val regionName: String,
    @PropertyElement
    val routeId: String,
    @PropertyElement
    val routeName: String,
    @PropertyElement
    val routeTypeCd: String,
    @PropertyElement
    val routeTypeName: String,
    @PropertyElement
    val startMobileNo: String,
    @PropertyElement
    val startStationId: String,
    @PropertyElement
    val startStationName: String,
    @PropertyElement
    val upFirstTime: String,
    @PropertyElement
    val upLastTime: String,
    @PropertyElement
    val nPeekAlloc: String)
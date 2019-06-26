package com.example.busstation.model

import com.tickaroo.tikxml.annotation.Element
import com.tickaroo.tikxml.annotation.PropertyElement
import com.tickaroo.tikxml.annotation.Xml

/**
 * @file BusStationArrivalInfo.kt
 * @date 06/20/2019
 * @brief A class containing a single appliance's data
 * @copyright GE Appliances, a Haier Company (Confidential). All rights reserved.
 */

@Xml(name = "msgBody")
data class BusStationArrivalInfo(
    @Element
    val busArrivalList: List<BusStationArrival>)

@Xml(name = "busArrivalList")
data class BusStationArrival(
    @PropertyElement
    val flag: String,
    @PropertyElement
    var locationNo1: String,
    @PropertyElement
    var locationNo2: String,
    @PropertyElement
    val lowPlate1: String,
    @PropertyElement
    val lowPlate2: String,
    @PropertyElement
    val plateNo1: String,
    @PropertyElement
    val plateNo2: String,
    @PropertyElement
    var predictTime1: String,
    @PropertyElement
    var predictTime2: String,
    @PropertyElement
    var remainSeatCnt1: String,
    @PropertyElement
    var remainSeatCnt2: String,
    @PropertyElement
    val routeId: String,
    @PropertyElement
    val staOrder: String,
    @PropertyElement
    val stationId: String)
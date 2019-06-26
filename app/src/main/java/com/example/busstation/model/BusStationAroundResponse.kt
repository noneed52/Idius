package com.example.busstation.model

import com.tickaroo.tikxml.annotation.Element
import com.tickaroo.tikxml.annotation.PropertyElement
import com.tickaroo.tikxml.annotation.Xml

/**
 * @file BusStationAroundResponseroundResponse.kt
 * @date 06/19/2019
 * @brief A class containing a single appliance's data
 * @copyright GE Appliances, a Haier Company (Confidential). All rights reserved.
 */

@Xml(name = "response")
data class BusStationAroundResponse(
    @Element
    val comMsgHeader: ComMsgHeader,
    @Element
    val msgHeader: MsgHeader,
    @Element
    val msgBody: BusStationAroundInfo?)
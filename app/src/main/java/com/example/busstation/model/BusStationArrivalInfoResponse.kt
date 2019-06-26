package com.example.busstation.model

import com.tickaroo.tikxml.annotation.Element
import com.tickaroo.tikxml.annotation.Xml

/**
 * @file BusStationArrivalResponsense.kt
 * @date 06/20/2019
 * @brief A class containing a single appliance's data
 * @copyright GE Appliances, a Haier Company (Confidential). All rights reserved.
 */

@Xml(name = "response")
data class BusStationArrivalInfoResponse(
    @Element
    val comMsgHeader: ComMsgHeader,
    @Element
    val msgHeader: MsgHeader,
    @Element
    val msgBody: BusStationArrivalInfo?)
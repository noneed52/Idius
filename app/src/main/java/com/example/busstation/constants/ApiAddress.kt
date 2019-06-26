package com.example.busstation.constants

/**
 * @file ApiAddress.kt
 * @date 06/19/2019
 * @brief A class containing a single appliance's data
 * @copyright GE Appliances, a Haier Company (Confidential). All rights reserved.
 */

object ApiAddress {

    const val BASE_URL = "http://openapi.gbis.go.kr/ws/rest/"

    const val BUS_STATION_LIST = "busstationservice/searcharound"
    const val BUS_STATION_ARRIVAL_TIME_ALL = "busarrivalservice/station"
    const val BUS_INFO = "busrouteservice/info"
    const val BUS_ROUTE_STATION_INFO = "busrouteservice/station"

}
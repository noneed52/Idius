package com.example.busstation.model

import com.tickaroo.tikxml.annotation.PropertyElement
import com.tickaroo.tikxml.annotation.Xml

/**
 * @file CommonResponse.kt
 * @date 06/20/2019
 * @brief A class containing a single appliance's data
 * @copyright GE Appliances, a Haier Company (Confidential). All rights reserved.
 */

@Xml
data class ComMsgHeader(
    @PropertyElement
    val errMsg: String?,
    @PropertyElement
    val returnCode: String?)

@Xml
data class MsgHeader(
    @PropertyElement
    val queryTime: String?,
    @PropertyElement
    val resultCode: String?,
    @PropertyElement
    val resultMessage: String?)
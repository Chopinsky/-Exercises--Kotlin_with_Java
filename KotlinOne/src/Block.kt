package com.kotlin

import java.security.Timestamp

/**
 * Created by Ziwei on 5/29/2017.
 */

data class Block(val index: Int = 0,
                 val previousHas: String,
                 val timestamp: Timestamp,
                 val blockData: String,
                 val blockHash: String)

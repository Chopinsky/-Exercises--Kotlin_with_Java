package com.kotlin

import java.math.BigDecimal

/**
 * Created by Ziwei on 5/20/2017.
 */

data class Money(var amount: BigDecimal = BigDecimal(0), val currency: String)
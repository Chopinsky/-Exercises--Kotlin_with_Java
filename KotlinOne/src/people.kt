package com.kotlin

/**
 * Created by Ziwei on 5/20/2017.
 */

data class People(val firstName: String = "", val lastName: String = "", val sex: Sex = Sex.Unknown)

enum class Sex {
    Male,
    Female,
    Unknown
}
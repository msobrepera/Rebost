package com.ymest.rebost.json

import java.util.*

data class Rebost(
    val id: Int,
    val codibarres: String,
    val datacaducitat: Long,
    val quantitat: Int,
    val consumit: Int,
    val idubicacio: Int,
    val dataalta: Long
)
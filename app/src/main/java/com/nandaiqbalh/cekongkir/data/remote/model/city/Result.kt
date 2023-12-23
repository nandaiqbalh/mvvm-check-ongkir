package com.nandaiqbalh.cekongkir.data.remote.model.city

data class Result(
    val city_id: String,
    val city_name: String,
    val postal_code: String,
    val province: String,
    val province_id: String,
    val type: String
)
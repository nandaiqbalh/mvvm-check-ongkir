package com.nandaiqbalh.cekongkir.data.remote.model.cost

data class Query(
    val courier: String,
    val destination: String,
    val key: String,
    val origin: String,
    val weight: Int
)
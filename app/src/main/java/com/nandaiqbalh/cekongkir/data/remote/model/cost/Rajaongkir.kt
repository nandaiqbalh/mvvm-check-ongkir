package com.nandaiqbalh.cekongkir.data.remote.model.cost

data class Rajaongkir(
    val destination_details: DestinationDetails,
    val origin_details: OriginDetails,
    val query: Query,
    val results: List<Result>,
    val status: Status
)
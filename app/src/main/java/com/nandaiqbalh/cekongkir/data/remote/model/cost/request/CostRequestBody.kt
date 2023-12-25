package com.nandaiqbalh.cekongkir.data.remote.model.cost.request

data class CostRequestBody(
	val key: String,
	val origin: String,
	val destination: String,
	val weight: Int,
	val courier: String
)
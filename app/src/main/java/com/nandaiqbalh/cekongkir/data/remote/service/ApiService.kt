package com.nandaiqbalh.cekongkir.data.remote.service

import com.nandaiqbalh.cekongkir.BuildConfig
import com.nandaiqbalh.cekongkir.data.remote.model.city.GetCityResponse
import com.nandaiqbalh.cekongkir.data.remote.model.cost.GetCostResponse
import com.nandaiqbalh.cekongkir.data.remote.model.cost.request.CostRequestBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {

	@GET("city")
	suspend fun getCity(
		@Query("key") key: String = BuildConfig.API_KEY,
	): GetCityResponse

	@POST("cost")
	suspend fun checkCost(
		@Query("key") key: String = BuildConfig.API_KEY,
		@Body requestBody: CostRequestBody
	): GetCostResponse

}
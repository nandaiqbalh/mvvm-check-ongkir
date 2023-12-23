package com.nandaiqbalh.cekongkir.data.remote.service

import com.nandaiqbalh.cekongkir.BuildConfig
import com.nandaiqbalh.cekongkir.data.remote.model.city.GetCityResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

	@GET("city")
	suspend fun getCity(
		@Query("key") key: String = BuildConfig.API_KEY,
	): GetCityResponse


}
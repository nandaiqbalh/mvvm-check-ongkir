package com.nandaiqbalh.cekongkir.data.remote.datasource.city

import com.nandaiqbalh.cekongkir.data.remote.model.city.GetCityResponse
import com.nandaiqbalh.cekongkir.data.remote.service.ApiService
import javax.inject.Inject

interface CityRemoteDataSource {
	suspend fun getCity(key: String): GetCityResponse
}

class CityRemoteDataSourceImpl @Inject constructor(private val apiService: ApiService) :
	CityRemoteDataSource {
	override suspend fun getCity(
		key: String,
	): GetCityResponse {
		return apiService.getCity(key)
	}


}
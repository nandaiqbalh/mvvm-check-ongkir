package com.nandaiqbalh.cekongkir.data.remote.datasource.cost

import com.nandaiqbalh.cekongkir.data.remote.model.city.GetCityResponse
import com.nandaiqbalh.cekongkir.data.remote.model.cost.GetCostResponse
import com.nandaiqbalh.cekongkir.data.remote.model.cost.request.CostRequestBody
import com.nandaiqbalh.cekongkir.data.remote.service.ApiService
import javax.inject.Inject

interface CostRemoteDataSource {
	suspend fun checkCost(key: String, costRequestBody: CostRequestBody): GetCostResponse
}

class CostRemoteDataSourceImpl @Inject constructor(private val apiService: ApiService) :
	CostRemoteDataSource {
	override suspend fun checkCost(
		key: String,
		costRequestBody: CostRequestBody
	): GetCostResponse {
		return apiService.checkCost(key, costRequestBody)
	}


}
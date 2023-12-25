package com.nandaiqbalh.cekongkir.data.remote.repository.cost

import com.nandaiqbalh.cekongkir.data.remote.datasource.cost.CostRemoteDataSource
import com.nandaiqbalh.cekongkir.data.remote.model.cost.GetCostResponse
import com.nandaiqbalh.cekongkir.data.remote.model.cost.request.CostRequestBody
import com.nandaiqbalh.cekongkir.wrapper.Resource
import javax.inject.Inject

interface CostRemoteRepository {
	suspend fun checkCost(
		key: String,
		costRequestBody: CostRequestBody
	): Resource<GetCostResponse>
}

class CostRemoteRepositoryImpl @Inject constructor(private val dataSource: CostRemoteDataSource) :
	CostRemoteRepository {
	override suspend fun checkCost(
		key: String,
		costRequestBody: CostRequestBody
	): Resource<GetCostResponse> {
		return proceed {
			dataSource.checkCost(key, costRequestBody)
		}
	}

	private suspend fun <T> proceed(coroutines: suspend () -> T): Resource<T> {
		return try {
			Resource.Success(coroutines.invoke())
		} catch (e: Exception) {
			Resource.Error(e)
		}
	}


}
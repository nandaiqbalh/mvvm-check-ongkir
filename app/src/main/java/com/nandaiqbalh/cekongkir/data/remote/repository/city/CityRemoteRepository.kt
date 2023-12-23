package com.nandaiqbalh.cekongkir.data.remote.repository.city

import com.nandaiqbalh.cekongkir.data.remote.datasource.city.CityRemoteDataSource
import com.nandaiqbalh.cekongkir.data.remote.model.city.GetCityResponse
import com.nandaiqbalh.cekongkir.wrapper.Resource
import javax.inject.Inject

interface CityRemoteRepository {
	suspend fun getCity(
		key: String,
	): Resource<GetCityResponse>
}

class CityRemoteRepositoryImpl @Inject constructor(private val dataSource: CityRemoteDataSource) :
	CityRemoteRepository {
	override suspend fun getCity(
		key: String,
	): Resource<GetCityResponse> {
		return proceed {
			dataSource.getCity(key)
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
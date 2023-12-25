package com.nandaiqbalh.cekongkir.di

import com.nandaiqbalh.cekongkir.data.remote.datasource.city.CityRemoteDataSource
import com.nandaiqbalh.cekongkir.data.remote.datasource.city.CityRemoteDataSourceImpl
import com.nandaiqbalh.cekongkir.data.remote.datasource.cost.CostRemoteDataSource
import com.nandaiqbalh.cekongkir.data.remote.datasource.cost.CostRemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {
	@Binds
	abstract fun provideCityRemoteDataSource(cityRemoteDataSourceImpl: CityRemoteDataSourceImpl): CityRemoteDataSource

	@Binds
	abstract fun provideCostRemoteDataSource(costRemoteDataSourceImpl: CostRemoteDataSourceImpl): CostRemoteDataSource

}
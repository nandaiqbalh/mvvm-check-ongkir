package com.nandaiqbalh.cekongkir.di

import com.nandaiqbalh.cekongkir.data.remote.repository.city.CityRemoteRepository
import com.nandaiqbalh.cekongkir.data.remote.repository.city.CityRemoteRepositoryImpl
import com.nandaiqbalh.cekongkir.data.remote.repository.cost.CostRemoteRepository
import com.nandaiqbalh.cekongkir.data.remote.repository.cost.CostRemoteRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
	@Binds
	abstract fun bindsCityRemoteRepository(cityRemoteRepositoryImpl: CityRemoteRepositoryImpl): CityRemoteRepository

	@Binds
	abstract fun bindsCostRemoteRepository(costRemoteRepositoryImpl: CostRemoteRepositoryImpl): CostRemoteRepository

}
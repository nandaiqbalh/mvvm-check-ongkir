package com.nandaiqbalh.cekongkir.di

import android.content.Context
import com.nandaiqbalh.cekongkir.data.local.datastore.DataStoreManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object LocalModule {

	@Provides
	fun provideDataStoreManager(@ApplicationContext context: Context): DataStoreManager =
		DataStoreManager(context)

}
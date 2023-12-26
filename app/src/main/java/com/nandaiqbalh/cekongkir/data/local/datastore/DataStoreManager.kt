package com.nandaiqbalh.cekongkir.data.local.datastore

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DataStoreManager(@ApplicationContext private val context: Context) {

	suspend fun clear() {
		context.dataStore.edit {
			it.clear()
		}
	}

	val getOriginId: Flow<String?> = context.dataStore.data.map {
		it[ORIGIN_ID_KEY]
	}

	suspend fun setOriginId(originId: String) {
		context.dataStore.edit {
			it[ORIGIN_ID_KEY] = originId
		}
	}

	val getOriginName: Flow<String?> = context.dataStore.data.map {
		it[ORIGIN_NAME_KEY]
	}

	suspend fun setOriginName(originName: String) {
		context.dataStore.edit {
			it[ORIGIN_NAME_KEY] = originName
		}
	}

	val getOriginType: Flow<String?> = context.dataStore.data.map {
		it[ORIGIN_TYPE_KEY]
	}

	suspend fun setOriginType(originType: String) {
		context.dataStore.edit {
			it[ORIGIN_TYPE_KEY] = originType
		}
	}



	val getDestinationId: Flow<String?> = context.dataStore.data.map {
		it[DESTINATION_ID_KEY]
	}

	suspend fun setDestinationId(destinationId: String) {
		context.dataStore.edit {
			it[DESTINATION_ID_KEY] = destinationId
		}
	}

	val getDestinationName: Flow<String?> = context.dataStore.data.map {
		it[DESTINATION_NAME_KEY]
	}

	suspend fun setDestinationName(destinationName: String) {
		context.dataStore.edit {
			it[DESTINATION_NAME_KEY] = destinationName
		}
	}

	val getDestinationType: Flow<String?> = context.dataStore.data.map {
		it[DESTINATION_TYPE_KEY]
	}

	suspend fun setDestinationType(destinationType: String) {
		context.dataStore.edit {
			it[DESTINATION_TYPE_KEY] = destinationType
		}
	}

	companion object {
		private const val DATASTORE_NAME = "datastore_preferences"

		private val ORIGIN_NAME_KEY = stringPreferencesKey("ORIGIN_NAME_KEY")
		private val ORIGIN_ID_KEY = stringPreferencesKey("ORIGIN_ID_KEY")
		private val ORIGIN_TYPE_KEY = stringPreferencesKey("ORIGIN_TYPE_KEY")

		private val DESTINATION_NAME_KEY = stringPreferencesKey("DESTINATION_NAME_KEY")
		private val DESTINATION_ID_KEY = stringPreferencesKey("DESTINATION_ID_KEY")
		private val DESTINATION_TYPE_KEY = stringPreferencesKey("DESTINATION_TYPE_KEY")


		private val Context.dataStore by preferencesDataStore(
			name = DATASTORE_NAME
		)
	}
}
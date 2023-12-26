package com.nandaiqbalh.cekongkir.presentation.ui.searchcity

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.nandaiqbalh.cekongkir.data.local.datastore.DataStoreManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchCityViewModel @Inject constructor(
	private val dataStoreManager: DataStoreManager
) : ViewModel(){

	fun getOriginId(): LiveData<String?> = dataStoreManager.getOriginId.asLiveData()

	fun setOriginId(originId: String) = CoroutineScope(Dispatchers.IO).launch {
		dataStoreManager.setOriginId(originId)
	}

	fun getOriginName(): LiveData<String?> = dataStoreManager.getOriginName.asLiveData()

	fun setOriginName(originName: String) = CoroutineScope(Dispatchers.IO).launch {
		dataStoreManager.setOriginName(originName)
	}

	fun getOriginType(): LiveData<String?> = dataStoreManager.getOriginType.asLiveData()

	fun setOriginType(originType: String) = CoroutineScope(Dispatchers.IO).launch {
		dataStoreManager.setOriginType(originType)
	}

	fun getDestinationId(): LiveData<String?> = dataStoreManager.getDestinationId.asLiveData()

	fun setDestinationId(DestinationId: String) = CoroutineScope(Dispatchers.IO).launch {
		dataStoreManager.setDestinationId(DestinationId)
	}

	fun getDestinationName(): LiveData<String?> = dataStoreManager.getDestinationName.asLiveData()

	fun setDestinationName(DestinationName: String) = CoroutineScope(Dispatchers.IO).launch {
		dataStoreManager.setDestinationName(DestinationName)
	}

	fun getDestinationType(): LiveData<String?> = dataStoreManager.getDestinationType.asLiveData()

	fun setDestinationType(DestinationType: String) = CoroutineScope(Dispatchers.IO).launch {
		dataStoreManager.setDestinationType(DestinationType)
	}
}
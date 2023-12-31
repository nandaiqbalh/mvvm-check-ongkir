package com.nandaiqbalh.cekongkir.presentation.ui.resultcost

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.nandaiqbalh.cekongkir.data.local.datastore.DataStoreManager
import com.nandaiqbalh.cekongkir.data.remote.model.cost.GetCostResponse
import com.nandaiqbalh.cekongkir.data.remote.model.cost.request.CostRequestBody
import com.nandaiqbalh.cekongkir.data.remote.repository.cost.CostRemoteRepository
import com.nandaiqbalh.cekongkir.wrapper.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ResultCostActivityViewModel @Inject constructor(
	private val costRemoteRepository: CostRemoteRepository,
	private val dataStoreManager: DataStoreManager
) : ViewModel() {

	private val _costResult = MutableLiveData<Resource<GetCostResponse>>()
	val costResult: LiveData<Resource<GetCostResponse>> get() = _costResult // LiveData untuk diobservasi di luar kelas

	fun checkCost(key: String, costRequestBody: CostRequestBody) {
		viewModelScope.launch(Dispatchers.IO) {
			_costResult.postValue(Resource.Loading())

			try {
				val data = costRemoteRepository.checkCost(key, costRequestBody)

//				Log.d("PAYLOAD", data.payload.toString())
				if (data.payload != null) {
					viewModelScope.launch(Dispatchers.Main) {
						_costResult.postValue(Resource.Success(data.payload))

					}
				} else {
					_costResult.postValue(Resource.Error(data.exception, null))
				}
			} catch (e: Exception) {
				viewModelScope.launch(Dispatchers.Main) {
					_costResult.postValue(Resource.Error(e, null))
				}
			}
		}
	}

	fun getOriginId(): LiveData<String?> = dataStoreManager.getOriginId.asLiveData()

	fun getOriginName(): LiveData<String?> = dataStoreManager.getOriginName.asLiveData()

	fun getOriginType(): LiveData<String?> = dataStoreManager.getOriginType.asLiveData()

	fun getDestinationId(): LiveData<String?> = dataStoreManager.getDestinationId.asLiveData()

	fun getDestinationName(): LiveData<String?> = dataStoreManager.getDestinationName.asLiveData()

	fun getDestinationType(): LiveData<String?> = dataStoreManager.getDestinationType.asLiveData()

}
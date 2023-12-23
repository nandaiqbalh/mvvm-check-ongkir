package com.nandaiqbalh.cekongkir.presentation.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nandaiqbalh.cekongkir.data.remote.model.city.GetCityResponse
import com.nandaiqbalh.cekongkir.data.remote.repository.city.CityRemoteRepository
import com.nandaiqbalh.cekongkir.wrapper.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
	private val cityRemoteRepository: CityRemoteRepository,
) : ViewModel() {

	private val _cityResult = MutableLiveData<Resource<GetCityResponse>>()
	val cityResult: LiveData<Resource<GetCityResponse>> get() = _cityResult // LiveData untuk diobservasi di luar kelas

	fun getCity(key: String) {
		viewModelScope.launch(Dispatchers.IO) {
			_cityResult.postValue(Resource.Loading())
			try {
				val data = cityRemoteRepository.getCity(key)

//				Log.d("PAYLOAD", data.payload.toString())
				if (data.payload != null) {
					viewModelScope.launch(Dispatchers.Main) {
						_cityResult.postValue(Resource.Success(data.payload))
					}
				} else {
					_cityResult.postValue(Resource.Error(data.exception, null))
				}
			} catch (e: Exception) {
				viewModelScope.launch(Dispatchers.Main) {
					_cityResult.postValue(Resource.Error(e, null))
				}
			}
		}
	}
}
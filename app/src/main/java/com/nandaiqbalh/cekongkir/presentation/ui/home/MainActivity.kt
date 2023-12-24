package com.nandaiqbalh.cekongkir.presentation.ui.home

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.nandaiqbalh.cekongkir.BuildConfig
import com.nandaiqbalh.cekongkir.R
import com.nandaiqbalh.cekongkir.databinding.ActivityMainBinding
import com.nandaiqbalh.cekongkir.presentation.ui.home.adapter.CityAdapter
import com.nandaiqbalh.cekongkir.wrapper.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

	private var _binding: ActivityMainBinding? = null
	private val binding get() = _binding!!

	private val viewModel: MainActivityViewModel by viewModels()

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		_binding = ActivityMainBinding.inflate(layoutInflater)
		setContentView(binding.root)

		// get city
		getCity()

		// button action
		buttonActionListener()
	}

	private fun getCity() {
		viewModel.getCity(BuildConfig.API_KEY)

		viewModel.cityResult.observe(this) { cityResult ->
			when (cityResult) {
				is Resource.Loading -> setLoading(true)
				is Resource.Error -> setLoading(false)
				is Resource.Success -> {
					setLoading(false)
					cityResult.payload?.rajaongkir?.results?.let { cities ->
						// Extract city names and types
						val cityNames = cities.map { it.city_name }

						// Create a custom adapter to display both names and types
						val adapter =
							CityAdapter(
								this,
								R.layout.dropdown_item_city,
								R.id.textViewCityName,
								viewModel,
								cityNames
							)
						binding.edtFrom.setAdapter(adapter)
						binding.edtTo.setAdapter(adapter)
					}
				}

				else -> {}
			}
		}

		binding.edtFrom.setOnItemClickListener { _, _, position, _ ->
			val selectedCity = binding.edtFrom.adapter.getItem(position) as String
			val selectedCityId = getCitydFromPosition(selectedCity, position)
			val selectedCityTypes = getCityTypeFromPosition(selectedCity, position)

			Log.d("Selected Id", selectedCityId.toString())
			Log.d("Selected Type", selectedCityTypes.toString())

		}

		binding.edtTo.setOnItemClickListener { _, _, position, _ ->
			val selectedCity = binding.edtTo.adapter.getItem(position) as String
			val selectedCityId = getCitydFromPosition(selectedCity, position)
			val selectedCityTypes = getCityTypeFromPosition(selectedCity, position)

			Log.d("Selected Id", selectedCityId.toString())
			Log.d("Selected Type", selectedCityTypes.toString())

		}
	}

	private fun getCitydFromPosition(cityName: String, position: Int): String? {
		val cityResult = viewModel.cityResult.value?.payload?.rajaongkir?.results

		// Find all cities with the given name
		val selectedCities = cityResult?.filter { it.city_name == cityName }

		// Use the position directly to get the city type
		return selectedCities?.getOrNull(position)?.city_id
	}

	private fun getCityTypeFromPosition(cityName: String, position: Int): String? {
		val cityResult = viewModel.cityResult.value?.payload?.rajaongkir?.results

		// Find all cities with the given name
		val selectedCities = cityResult?.filter { it.city_name == cityName }

		return selectedCities?.getOrNull(position)?.type
	}

	private fun buttonActionListener() {
		binding.btnCheckHarga.setOnClickListener {
			if (validateForm()) {
				// Handle form validation success
			}
		}
	}

	private fun validateForm(): Boolean {
		val from = binding.edtFrom.text.toString()
		val to = binding.edtTo.text.toString()
		val weight = binding.edtWeight.text.toString()

		var isFormValid = true

		if (from.isEmpty()) {
			isFormValid = false
			binding.tilFrom.error = getString(R.string.tv_error_input_blank)
		} else {
			binding.tilFrom.error = null
		}

		if (to.isEmpty()) {
			isFormValid = false
			binding.tilTo.error = getString(R.string.tv_error_input_blank)
		} else {
			binding.tilTo.error = null
		}

		if (weight.isEmpty()) {
			isFormValid = false
			binding.tilWeight.error = getString(R.string.tv_error_input_blank)
		} else {
			binding.tilWeight.error = null
		}

		return isFormValid
	}

	private fun setLoading(isLoading: Boolean) {
		binding.pbMain.isVisible = isLoading
	}

	override fun onDestroy() {
		super.onDestroy()
		_binding = null
	}
}

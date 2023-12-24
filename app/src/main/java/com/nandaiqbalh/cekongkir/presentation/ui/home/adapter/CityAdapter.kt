package com.nandaiqbalh.cekongkir.presentation.ui.home.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.nandaiqbalh.cekongkir.databinding.DropdownItemCityBinding
import com.nandaiqbalh.cekongkir.presentation.ui.home.MainActivityViewModel

class CityAdapter(
	context: Context,
	resource: Int,
	textViewResourceId: Int,
	private val viewModel: MainActivityViewModel,
	cityNames: List<String>
) : ArrayAdapter<String>(context, resource, textViewResourceId, cityNames) {

	@SuppressLint("SetTextI18n")
	override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
		val binding: DropdownItemCityBinding = if (convertView == null) {
			// Inflate the layout using view binding
			DropdownItemCityBinding.inflate(LayoutInflater.from(context), parent, false)
		} else {
			// Reuse the existing binding
			DropdownItemCityBinding.bind(convertView)
		}

		val cityName = getItem(position) ?: ""
		val cityType = getCityTypeFromPosition(cityName, position)
		binding.textViewCityName.text = "$cityName, $cityType"

		return binding.root
	}

	@SuppressLint("SetTextI18n")
	override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
		// Same as getView method, use view binding to inflate or bind the existing view
		val binding: DropdownItemCityBinding = if (convertView == null) {
			DropdownItemCityBinding.inflate(LayoutInflater.from(context), parent, false)
		} else {
			DropdownItemCityBinding.bind(convertView)
		}

		val cityName = getItem(position) ?: ""
		val cityType = getCityTypeFromPosition(cityName, position)
		binding.textViewCityName.text = "$cityName, $cityType"

		return binding.root
	}

	private fun getCityTypeFromPosition(cityName: String, position: Int): String? {
		val cityResult = viewModel.cityResult.value?.payload?.rajaongkir?.results

		// Find all cities with the given name
		val selectedCities = cityResult?.filter { it.city_name == cityName }

		// Use the position directly to get the city type

		return selectedCities?.getOrNull(position)?.type
	}


}

package com.nandaiqbalh.cekongkir.presentation.ui.home.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.nandaiqbalh.cekongkir.presentation.ui.home.MainActivityViewModel

class CityAdapter(
	context: Context,
	private val resource: Int,
	private val textViewResourceId: Int,
	private val viewModel: MainActivityViewModel,
	objects: List<String>,
) : ArrayAdapter<String>(context, resource, textViewResourceId, objects) {

	override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
		val view = super.getView(position, convertView, parent)
		val textViewCityName = view.findViewById<TextView>(textViewResourceId)
		val city = getItem(position) ?: ""
		val cityType = getCityTypeFromName(city)
		textViewCityName.text = "$city, $cityType"
		return view
	}

	override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
		val view = super.getDropDownView(position, convertView, parent)
		val textViewCityName = view.findViewById<TextView>(textViewResourceId)
		val city = getItem(position) ?: ""
		val cityType = getCityTypeFromName(city)
		textViewCityName.text = "$city, $cityType"
		return view
	}

	private fun getCityIdFromName(cityName: String?): String? {
		val cityResult = viewModel.cityResult.value?.payload?.rajaongkir?.results
		val selectedCity = cityResult?.find { it.city_name == cityName }
		return selectedCity?.city_id
	}

	private fun getCityTypeFromName(cityName: String?): String? {
		val cityResult = viewModel.cityResult.value?.payload?.rajaongkir?.results
		val selectedCity = cityResult?.find { it.city_name == cityName }
		return selectedCity?.type
	}
}

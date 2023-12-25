package com.nandaiqbalh.cekongkir.presentation.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.nandaiqbalh.cekongkir.BuildConfig
import com.nandaiqbalh.cekongkir.R
import com.nandaiqbalh.cekongkir.data.remote.model.city.City
import com.nandaiqbalh.cekongkir.data.remote.model.cost.request.CostRequestBody
import com.nandaiqbalh.cekongkir.databinding.ActivityMainBinding
import com.nandaiqbalh.cekongkir.presentation.ui.searchcity.SearchCityActivity
import com.nandaiqbalh.cekongkir.wrapper.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

	private var _binding: ActivityMainBinding? = null
	private val binding get() = _binding!!

	private val viewModel: MainActivityViewModel by viewModels()

	private var selectedCityOriginId: String? = null
	private var selectedCityDestinationId: String? = null

	private lateinit var cities : List<City>

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		_binding = ActivityMainBinding.inflate(layoutInflater)
		setContentView(binding.root)

		// get city
		getCity()

		// button action
		buttonActionListener()

		dropdownKurir()

		setLoading(true)
	}

	private fun getCity() {
		viewModel.getCity(BuildConfig.API_KEY)

		viewModel.cityResult.observe(this) { cityResult ->
			when (cityResult) {
				is Resource.Loading -> setLoading(true)
				is Resource.Error -> setLoading(false)
				is Resource.Success -> {
					setLoading(false)
					cities = cityResult.payload?.rajaongkir!!.results

					Log.d("CITY RESULT", cities.toString())
				}

				else -> {}
			}
		}

	}

	private fun dropdownKurir() {
		val courierNames = arrayOf("JNE", "TIKI", "POS")
		val spinnerCourier = binding.spinnerCourier

		val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, courierNames)
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
		spinnerCourier.adapter = adapter

		// Set the item selected listener
		spinnerCourier.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
			override fun onItemSelected(
				parentView: AdapterView<*>,
				selectedItemView: View?,
				position: Int,
				id: Long,
			) {
				// Get the selected courier name
				val selectedCourier = courierNames[position]

				// Do something with the selected courier value
				Log.d("Selected Courier", selectedCourier)
			}

			override fun onNothingSelected(parentView: AdapterView<*>) {
				// Do nothing here if nothing is selected
			}
		}
	}


	private fun buttonActionListener() {
		binding.btnCheckHarga.setOnClickListener {
			if (validateForm()) {

				val weight = binding.edtWeight.text.toString().toInt()
				val selectedCourier = binding.spinnerCourier.selectedItem.toString().toLowerCase()

				viewModel.checkCost(
					BuildConfig.API_KEY,
					CostRequestBody(
						BuildConfig.API_KEY,
						selectedCityOriginId!!,
						selectedCityDestinationId!!,
						weight,
						selectedCourier
					)
				)

				viewModel.costResult.observe(this) { costResult ->
					when (costResult) {
						is Resource.Loading -> setLoading(true)
						is Resource.Error -> setLoading(false)
						is Resource.Success -> {
							setLoading(false)

							Log.d("COST RESULT", costResult.data.rajaongkir.results.toString())
							Log.d("ORIGIN", costResult.data.rajaongkir.origin_details.toString())
							Log.d("DESTINATION", costResult.data.rajaongkir.destination_details.toString())

						}

						else -> {}
					}
				}
			}
		}

		binding.edtFrom.setOnClickListener {
			val intent= Intent(this@MainActivity, SearchCityActivity::class.java)
			intent.putExtra("by", "tilfrom")
			intent.putParcelableArrayListExtra("city_result", ArrayList(cities))
			startActivity(intent)
		}

		binding.edtTo.setOnClickListener {
			val intent= Intent(this@MainActivity, SearchCityActivity::class.java)
			intent.putExtra("by", "tilto")
			intent.putParcelableArrayListExtra("city_result", ArrayList(cities))
			startActivity(intent)
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
		if (isLoading){
			binding.pbMain.visibility = View.VISIBLE
		} else {
			binding.pbMain.visibility = View.GONE
		}
	}

	override fun onDestroy() {
		super.onDestroy()
		_binding = null
	}
}

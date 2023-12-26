package com.nandaiqbalh.cekongkir.presentation.ui.searchcity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.nandaiqbalh.cekongkir.R
import com.nandaiqbalh.cekongkir.data.remote.model.city.City
import com.nandaiqbalh.cekongkir.databinding.ActivitySearchCityBinding
import com.nandaiqbalh.cekongkir.presentation.ui.home.MainActivity
import com.nandaiqbalh.cekongkir.presentation.ui.searchcity.adapter.CityAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchCityActivity : AppCompatActivity() {

	private var _binding: ActivitySearchCityBinding? = null
	private val binding get() = _binding!!

	private var clickedBy: String = ""
	private var selectedCity: City? = null

	private val viewModel:SearchCityViewModel by viewModels()

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		_binding = ActivitySearchCityBinding.inflate(layoutInflater)
		setContentView(binding.root)

		clickedBy = intent.getStringExtra("by").toString()

		setViewBasedOnOriginActivity()

	}

	private fun setViewBasedOnOriginActivity() {
		if (clickedBy == "tilfrom") {
			binding.tvGreetings.text = getString(R.string.tv_judul_pilih_kota_asal)
			binding.tvGreetingSub.text = getString(R.string.tv_sub_pilih_kota_asal)

			val cities = intent.getParcelableArrayListExtra<City>("city_result")
			if (cities != null) {
				val adapter = CityAdapter()
				adapter.setList(cities)

				binding.edtSearch.addTextChangedListener(object : TextWatcher {
					override fun beforeTextChanged(
						charSequence: CharSequence?,
						p1: Int,
						p2: Int,
						p3: Int,
					) {
					}

					override fun onTextChanged(
						charSequence: CharSequence?,
						p1: Int,
						p2: Int,
						p3: Int,
					) {
						val filteredCities = if (charSequence.isNullOrBlank()) {
							cities
						} else {
							cities.filter { city ->
								city.city_name.contains(charSequence.toString(), true)
							}
						}
						adapter.setList(filteredCities)
					}

					override fun afterTextChanged(editable: Editable?) {}
				})


				adapter.itemClickListener = {
					val intent = Intent(this@SearchCityActivity, MainActivity::class.java)

					viewModel.setOriginId(it.city_id)
					viewModel.setOriginName(it.city_name)
					viewModel.setOriginType(it.type)

					startActivity(intent)
				}

				binding.apply {
					rvSearch.layoutManager = LinearLayoutManager(
						this@SearchCityActivity,
						LinearLayoutManager.VERTICAL,
						false
					)
					rvSearch.adapter = adapter
				}
			}
		} else {
			binding.tvGreetings.text = getString(R.string.tv_judul_pilih_kota_tujuan)
			binding.tvGreetingSub.text = getString(R.string.tv_sub_pilih_kota_tujuan)

			val cities = intent.getParcelableArrayListExtra<City>("city_result")
			if (cities != null) {
				val adapter = CityAdapter()
				adapter.setList(cities)

				binding.edtSearch.addTextChangedListener(object : TextWatcher {
					override fun beforeTextChanged(
						charSequence: CharSequence?,
						p1: Int,
						p2: Int,
						p3: Int,
					) {
					}

					override fun onTextChanged(
						charSequence: CharSequence?,
						p1: Int,
						p2: Int,
						p3: Int,
					) {
						val filteredCities = if (charSequence.isNullOrBlank()) {
							cities
						} else {
							cities.filter { city ->
								city.city_name.contains(charSequence.toString(), true)
							}
						}
						adapter.setList(filteredCities)
					}

					override fun afterTextChanged(editable: Editable?) {}
				})


				adapter.itemClickListener = {
					val intent = Intent(this@SearchCityActivity, MainActivity::class.java)

					viewModel.setDestinationId(it.city_id)
					viewModel.setDestinationName(it.city_name)
					viewModel.setDestinationType(it.type)

					startActivity(intent)
				}

				binding.apply {
					rvSearch.layoutManager = LinearLayoutManager(
						this@SearchCityActivity,
						LinearLayoutManager.VERTICAL,
						false
					)
					rvSearch.adapter = adapter
				}
			}
		}
	}

	override fun onDestroy() {
		super.onDestroy()
		_binding = null
	}
}
package com.nandaiqbalh.cekongkir.presentation.ui.searchcity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.nandaiqbalh.cekongkir.R
import com.nandaiqbalh.cekongkir.databinding.ActivitySearchCityBinding

class SearchCityActivity : AppCompatActivity() {

	private var _binding: ActivitySearchCityBinding? = null
	private val binding get() = _binding!!

	private var clickedBy: String = ""
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
		} else {
			binding.tvGreetings.text = getString(R.string.tv_judul_pilih_kota_tujuan)
			binding.tvGreetingSub.text = getString(R.string.tv_sub_pilih_kota_tujuan)
		}
	}


	override fun onDestroy() {
		super.onDestroy()
		_binding = null
	}
}
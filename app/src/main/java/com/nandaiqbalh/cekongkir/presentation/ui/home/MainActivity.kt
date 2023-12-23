package com.nandaiqbalh.cekongkir.presentation.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import com.nandaiqbalh.cekongkir.BuildConfig

import com.nandaiqbalh.cekongkir.databinding.ActivityMainBinding
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

		viewModel.getCity(BuildConfig.API_KEY)

		viewModel.cityResult.observe(this){ cityResult ->

			Log.d("CITY RESULT", cityResult.payload?.rajaongkir?.results.toString())
		}

	}

	override fun onDestroy() {
		super.onDestroy()
		_binding = null
	}
}
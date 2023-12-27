package com.nandaiqbalh.cekongkir.presentation.ui.resultcost

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.nandaiqbalh.cekongkir.BuildConfig
import com.nandaiqbalh.cekongkir.R
import com.nandaiqbalh.cekongkir.data.remote.model.cost.request.CostRequestBody
import com.nandaiqbalh.cekongkir.databinding.ActivityResultCostBinding
import com.nandaiqbalh.cekongkir.presentation.ui.resultcost.adapter.CostAdapter
import com.nandaiqbalh.cekongkir.wrapper.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ResultCostActivity : AppCompatActivity() {

	private var _binding: ActivityResultCostBinding? = null
	private val binding get() = _binding!!

	private val viewModel: ResultCostActivityViewModel by viewModels()
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		_binding = ActivityResultCostBinding.inflate(layoutInflater)
		setContentView(binding.root)

		observeCostResult()


	}

	@SuppressLint("SetTextI18n")
	private fun observeCostResult() {


		val selectedOriginId = intent.getStringExtra("selectedOriginId")
		val selectedDestinationId = intent.getStringExtra("selectedDestinationId")
		val weight = intent.getStringExtra("weight")
		val selectedCourier = intent.getStringExtra("selectedCourier")

		viewModel.checkCost(
			BuildConfig.API_KEY, CostRequestBody(
				BuildConfig.API_KEY,
				selectedOriginId.toString(),
				selectedDestinationId.toString(),
				weight.toString().toInt(),
				selectedCourier.toString()
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
					Log.d(
						"DESTINATION",
						costResult.data.rajaongkir.destination_details.toString()
					)
					val adapter = CostAdapter()

					adapter.setList(costResult.payload?.rajaongkir?.results)
					binding.rvResult.layoutManager = LinearLayoutManager(
						this@ResultCostActivity,
						LinearLayoutManager.VERTICAL,
						false
					)
					binding.rvResult.adapter = adapter

					// update UI
					binding.tvCityOrigin.text =
						"${costResult.payload?.rajaongkir?.origin_details?.city_name}, ${costResult.payload?.rajaongkir?.origin_details?.type}"
					binding.tvProvinceOrigin.text =
						"${costResult.payload?.rajaongkir?.origin_details?.province}"


					binding.tvCityDestination.text =
						"${costResult.payload?.rajaongkir?.destination_details?.city_name}, ${costResult.payload?.rajaongkir?.destination_details?.type}"
					binding.tvProvinceDestination.text =
						"${costResult.payload?.rajaongkir?.destination_details?.province}"

					when (costResult.payload?.rajaongkir?.query?.courier) {
						"jne" -> binding.ivLogoCourier.setImageResource(R.drawable.logo_jne)
						"tiki" -> binding.ivLogoCourier.setImageResource(R.drawable.logo_tiki)
						"pos" -> binding.ivLogoCourier.setImageResource(R.drawable.logo_pos)
						else -> {
						}
					}

					binding.tvCourierName.text =
						"${costResult.payload?.rajaongkir?.results?.get(0)?.name}"
					binding.tvCourierCode.text =
						"${costResult.payload?.rajaongkir?.results?.get(0)?.code}"

				}

				else -> {}
			}
		}

	}

	private fun setLoading(isLoading: Boolean) {
		if (isLoading) {
			binding.pbResult.visibility = View.VISIBLE
			binding.overlayLayout.visibility = View.VISIBLE
			window.setFlags(
				WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
				WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
			)
		} else {
			binding.pbResult.visibility = View.GONE
			binding.overlayLayout.visibility = View.GONE
			window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
		}
	}

	override fun onDestroy() {
		super.onDestroy()
		_binding = null
	}
}
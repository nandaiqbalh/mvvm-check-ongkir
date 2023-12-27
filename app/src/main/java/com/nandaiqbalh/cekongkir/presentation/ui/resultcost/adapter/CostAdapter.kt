package com.nandaiqbalh.cekongkir.presentation.ui.resultcost.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.nandaiqbalh.cekongkir.data.remote.model.cost.CostX
import com.nandaiqbalh.cekongkir.data.remote.model.cost.Result
import com.nandaiqbalh.cekongkir.databinding.ItemResultBinding
import java.text.NumberFormat
import java.util.Locale

class CostAdapter: RecyclerView.Adapter<CostAdapter.HomeViewHolder>() {
	private var costList: List<CostX> = emptyList()

	var itemClickListener: ((item: Result) -> Unit)? = null

	private lateinit var onItemClickCallBack: OnItemClickCallBack

	fun setOnItemClickCallback(onItemClickCallBack: OnItemClickCallBack) {
		this.onItemClickCallBack = onItemClickCallBack
	}

	private val diffCallback = object : DiffUtil.ItemCallback<Result>() {
		override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
			return oldItem.code == newItem.code
		}

		override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
			return oldItem.hashCode() == newItem.hashCode()
		}
	}

	private val differ = AsyncListDiffer(this, diffCallback)

	fun setList(cities: List<Result>?) {
		differ.submitList(cities)
	}

	inner class HomeViewHolder(private val binding: ItemResultBinding) : RecyclerView.ViewHolder(binding.root) {
		@SuppressLint("SetTextI18n")
		fun bind(result: Result) {
			binding.apply {
				// Display costs information
				val costsText = result.costs.joinToString("\n") { cost ->
					val formattedValue = formatCurrency(cost.cost[0].value)
					"${cost.description} (${cost.service})\nHarga: $formattedValue\nPerkiraan hari: ${cost.cost[0].etd} hari\n"
				}
				tvPrice.text = costsText
			}


			binding.root.setOnClickListener {
				itemClickListener?.invoke(result)

			}
		}

		private fun formatCurrency(value: Int): String {
			val localeID = Locale("id", "ID")
			val formatRupiah = NumberFormat.getCurrencyInstance(localeID)
			return formatRupiah.format(value.toLong())
		}
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
		val binding = ItemResultBinding.inflate(LayoutInflater.from(parent.context), parent, false)
		return HomeViewHolder(binding)
	}

	override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
		holder.bind(differ.currentList[position])
	}

	override fun getItemCount(): Int {
		return differ.currentList.size
	}

	interface OnItemClickCallBack {
		fun onItemClicked(data: Result)
	}
}

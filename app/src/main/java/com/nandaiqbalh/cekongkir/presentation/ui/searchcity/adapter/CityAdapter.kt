package com.nandaiqbalh.cekongkir.presentation.ui.searchcity.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.nandaiqbalh.cekongkir.data.remote.model.city.City
import com.nandaiqbalh.cekongkir.databinding.ItemSearchBinding

class CityAdapter: RecyclerView.Adapter<CityAdapter.HomeViewHolder>() {

	var itemClickListener: ((item: City) -> Unit)? = null

	private lateinit var onItemClickCallBack: OnItemClickCallBack

	fun setOnItemClickCallback(onItemClickCallBack: OnItemClickCallBack) {
		this.onItemClickCallBack = onItemClickCallBack
	}

	private val diffCallback = object : DiffUtil.ItemCallback<City>() {
		override fun areItemsTheSame(oldItem: City, newItem: City): Boolean {
			return oldItem.city_id == newItem.city_id
		}

		override fun areContentsTheSame(oldItem: City, newItem: City): Boolean {
			return oldItem.hashCode() == newItem.hashCode()
		}
	}

	private val differ = AsyncListDiffer(this, diffCallback)

	fun setList(cities: List<City>?) {
		differ.submitList(cities)
	}

	inner class HomeViewHolder(private val binding: ItemSearchBinding) : RecyclerView.ViewHolder(binding.root) {
		@SuppressLint("SetTextI18n")
		fun bind(city: City) {
			binding.apply {
				binding.itemCity.text = "${city.city_name},"
				binding.itemType.text = city.type
				binding.itemProvince.text = city.province

			}

			binding.root.setOnClickListener {
				itemClickListener?.invoke(city)

			}
		}
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
		val binding = ItemSearchBinding.inflate(LayoutInflater.from(parent.context), parent, false)
		return HomeViewHolder(binding)
	}

	override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
		holder.bind(differ.currentList[position])
	}

	override fun getItemCount(): Int {
		return differ.currentList.size
	}

	interface OnItemClickCallBack {
		fun onItemClicked(data: City)
	}
}

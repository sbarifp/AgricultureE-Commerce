package com.sbarifp.pemasaranproduk.presentation.myads

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sbarifp.pemasaranproduk.databinding.ItemMyAdBinding

class MyAdsAdapter: RecyclerView.Adapter<MyAdsAdapter.ViewHolder>() {

    private var listener: ((View) -> Unit)? = null

    class ViewHolder(private val binding: ItemMyAdBinding)
        : RecyclerView.ViewHolder(binding.root){
        fun bindItem(listener: ((View) -> Unit)?) {
            binding.btnMoreMyAd.setOnClickListener { view ->
                listener?.let {
                    it(view)
                }
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemMyAdBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = 10

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(listener)
    }

    fun onClick(listener: ((View) -> Unit)) {
        this.listener = listener
    }
}
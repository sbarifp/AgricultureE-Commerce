package com.sbarifp.pemasaranproduk.presentation.resultproduct

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sbarifp.pemasaranproduk.databinding.ItemProductBinding

class ResultProductAdapter: RecyclerView.Adapter<ResultProductAdapter.ViewHolder>() {
    class ViewHolder(private val binding: ItemProductBinding)
        : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemProductBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = 20

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

    }
}
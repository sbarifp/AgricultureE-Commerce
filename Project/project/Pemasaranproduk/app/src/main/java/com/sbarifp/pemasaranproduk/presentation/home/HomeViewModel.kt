package com.sbarifp.pemasaranproduk.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.sbarifp.pemasaranproduk.data.repository.product.ProductRepository

class HomeViewModel: ViewModel() {

    fun getDataProducts(token: String, isSwipe: Boolean) =
        ProductRepository.showProduct(token, isSwipe).asLiveData()
}
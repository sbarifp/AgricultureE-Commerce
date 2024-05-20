package com.sbarifp.pemasaranproduk.data.repository.product

import com.sbarifp.pemasaranproduk.data.model.product.ProductResponse

class ProductCacheDataSource {
    private var productResponse: ProductResponse? = null

    fun saveDataProduct(productResponse: ProductResponse){
        this.productResponse = productResponse
    }

    fun getDataProduct() = productResponse
}
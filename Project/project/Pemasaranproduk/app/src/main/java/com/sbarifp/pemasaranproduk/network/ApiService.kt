package com.sbarifp.pemasaranproduk.network

import com.sbarifp.pemasaranproduk.network.api.AdsService
import com.sbarifp.pemasaranproduk.network.api.AuthService
import com.sbarifp.pemasaranproduk.network.api.ProductService

object ApiService {
    fun getAuthService(): AuthService{
        return RetrofitClient.newInstance()
            .getRetrofitInstance()
            .create(AuthService::class.java)
    }

    fun productService(): ProductService{
        return RetrofitClient.newInstance()
            .getRetrofitInstance()
            .create(ProductService::class.java)
    }

    fun adsService(): AdsService{
        return RetrofitClient.newInstance()
            .getRetrofitInstance()
            .create(AdsService::class.java)
    }
}
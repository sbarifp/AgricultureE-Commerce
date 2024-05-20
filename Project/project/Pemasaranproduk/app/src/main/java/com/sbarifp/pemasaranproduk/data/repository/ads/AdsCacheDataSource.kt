package com.sbarifp.pemasaranproduk.data.repository.ads

import com.sbarifp.pemasaranproduk.data.model.product.ProductResponse

class AdsCacheDataSource {
    private var findAdsResponse: ProductResponse? = null

    fun getDataFindAds() = findAdsResponse

    fun saveDataFindAds(findAdsResponse: ProductResponse){
        this.findAdsResponse = findAdsResponse
    }
}
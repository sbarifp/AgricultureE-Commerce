package com.sbarifp.pemasaranproduk.data.repository.ads

import com.sbarifp.pemasaranproduk.data.model.ApiResponse
import com.sbarifp.pemasaranproduk.data.model.Resource
import com.sbarifp.pemasaranproduk.data.model.product.ProductResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import java.lang.Exception

object AdsRepository {
    private val adsRemoteDataSource = AdsRemoteDataSource()
    private val adsCacheDataSource = AdsCacheDataSource()

    fun findAds(lat: Double, lon: Double, title: String): Flow<Resource<ProductResponse>> = flow {
        emit(Resource.Loading)
        var productResponse: ProductResponse? = null

        try {
            productResponse = adsCacheDataSource.getDataFindAds()
        }catch (e: Exception){
            emit(Resource.Error(e.message.toString()))
        }

        if (productResponse != null){
            emit(Resource.Success(productResponse))
        }else{
            when(val apiResponse = adsRemoteDataSource.findAds(lat, lon, title).first()){
                ApiResponse.Empty -> emit(Resource.Empty)
                is ApiResponse.Error -> {
                    val errorMessage = apiResponse.errorMessage
                    emit(Resource.Error(errorMessage))
                }
                is ApiResponse.Success -> {
                    val data = apiResponse.data
                    adsCacheDataSource.saveDataFindAds(data)
                    emit(Resource.Success(data))
                }
            }
        }
    }
}
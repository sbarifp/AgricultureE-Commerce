package com.sbarifp.pemasaranproduk.data.repository.product

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.sbarifp.pemasaranproduk.data.model.ApiResponse
import com.sbarifp.pemasaranproduk.data.model.auth.RegisterResponse
import com.sbarifp.pemasaranproduk.data.model.product.CreateAdsResponse
import com.sbarifp.pemasaranproduk.data.model.product.DeleteImageResponse
import com.sbarifp.pemasaranproduk.data.model.product.ProductResponse
import com.sbarifp.pemasaranproduk.data.model.product.UpdateProductResponse
import com.sbarifp.pemasaranproduk.data.model.product.UploadImageResponse
import com.sbarifp.pemasaranproduk.network.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.MultipartBody

class ProductRemoteDataSource {

    suspend fun createAds(token: String, body: String): Flow<ApiResponse<CreateAdsResponse>> = flow {
        try {
            val response = ApiService.productService().createAds(token, body)
            if (response.isSuccessful){
                val data = response.body()
                if (data != null){
                    emit(ApiResponse.Success(data))
                }else{
                    emit(ApiResponse.Empty)
                }
            }else{
                val type = object : TypeToken<CreateAdsResponse>(){}.type
                val errorResponse: CreateAdsResponse = Gson().fromJson(response.errorBody()?.charStream(), type)
                emit(ApiResponse.Error(errorResponse.message.toString()))
            }
        }catch (t: Throwable){
            emit(ApiResponse.Error(t.message.toString()))
        }
    }.flowOn(Dispatchers.IO)

    suspend fun updateAds(token: String, body: String, idProduct: Int): Flow<ApiResponse<UpdateProductResponse>> = flow {
        try {
            val response = ApiService.productService().updateAds(token, body, idProduct)
            if (response.isSuccessful){
                val data = response.body()
                if (data != null){
                    emit(ApiResponse.Success(data))
                }else{
                    emit(ApiResponse.Empty)
                }
            }else{
                val type = object : TypeToken<UpdateProductResponse>(){}.type
                val errorResponse: UpdateProductResponse = Gson().fromJson(response.errorBody()?.charStream(), type)
                emit(ApiResponse.Error(errorResponse.message.toString()))
            }
        }catch (t: Throwable){
            emit(ApiResponse.Error(t.message.toString()))
        }
    }.flowOn(Dispatchers.IO)

    suspend fun uploadImages(token: String, id: Int, images: List<MultipartBody.Part>): Flow<ApiResponse<UploadImageResponse>> = flow {
        try {
            val response = ApiService.productService().uploadImages(token, id, images)
            if (response.isSuccessful){
                val data = response.body()
                if (data != null){
                    emit(ApiResponse.Success(data))
                }else{
                    emit(ApiResponse.Empty)
                }
            }else{
                val type = object : TypeToken<UploadImageResponse>(){}.type
                val errorResponse: UploadImageResponse = Gson().fromJson(response.errorBody()?.charStream(), type)
                emit(ApiResponse.Error(errorResponse.message.toString()))
            }
        }catch (t: Throwable){
            emit(ApiResponse.Error(t.message.toString()))
        }
    }.flowOn(Dispatchers.IO)

    suspend fun deleteImage(token: String, idProduct: Int): Flow<ApiResponse<DeleteImageResponse>> = flow {
        try {
            val response = ApiService.productService().deleteImage(token, idProduct)
            if (response.isSuccessful){
                val data = response.body()
                if (data != null){
                    emit(ApiResponse.Success(data))
                }else{
                    emit(ApiResponse.Empty)
                }
            }else{
                val type = object : TypeToken<DeleteImageResponse>(){}.type
                val errorResponse: DeleteImageResponse = Gson().fromJson(response.errorBody()?.charStream(), type)
                emit(ApiResponse.Error(errorResponse.message.toString()))
            }
        }catch (t: Throwable){
            emit(ApiResponse.Error(t.message.toString()))
        }
    }.flowOn(Dispatchers.IO)

    suspend fun showAllProduct(token: String, page: Int): Flow<ApiResponse<ProductResponse>> = flow {
        try {
            val response = ApiService.productService().showAllProduct(token, page)
            if (response.isSuccessful){
                val data = response.body()
                if (data != null){
                    emit(ApiResponse.Success(data))
                }else{
                    emit(ApiResponse.Empty)
                }
            }else{
                val type = object : TypeToken<ProductResponse>(){}.type
                val errorResponse: ProductResponse = Gson().fromJson(response.errorBody()?.charStream(), type)
                emit(ApiResponse.Error(errorResponse.message.toString()))
            }
        }catch (t: Throwable){
            emit(ApiResponse.Error(t.message.toString()))
        }
    }.flowOn(Dispatchers.IO)
}
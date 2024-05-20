package com.sbarifp.pemasaranproduk.network.api

import com.sbarifp.pemasaranproduk.data.model.product.CreateAdsResponse
import com.sbarifp.pemasaranproduk.data.model.product.DeleteImageResponse
import com.sbarifp.pemasaranproduk.data.model.product.DeleteProductResponse
import com.sbarifp.pemasaranproduk.data.model.product.ProductResponse
import com.sbarifp.pemasaranproduk.data.model.product.UpdateProductResponse
import com.sbarifp.pemasaranproduk.data.model.product.UploadImageResponse
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface ProductService {

    @Headers("Content-type: application/json")
    @POST("product")
    suspend fun createAds(
        @Header("authorization") token: String,
        @Body body: String
    ) : Response<CreateAdsResponse>

    @Headers("Content-type: application/json")
    @PATCH("product/{id}")
    suspend fun updateAds(
        @Header("authorization") token: String,
        @Body body: String,
        @Path("id") id: Int
    ) : Response<UpdateProductResponse>

    @Multipart
    @POST("product/{id}/upload")
    suspend fun uploadImages(
        @Header("authorization") token: String,
        @Path("id") id: Int,
        @Part images: List<MultipartBody.Part>
    ): Response<UploadImageResponse>

    @DELETE("image/{id}")
    suspend fun deleteImage(
        @Header("authorization") token: String,
        @Path("id") id: Int
    ): Response<DeleteImageResponse>

    @GET("product")
    suspend fun showAllProduct(
        @Header("authorization") token: String,
        @Query("page") page: Int,
        @Query("size") sizeData: Int = 10
    ): Response<ProductResponse>

    @GET("product")
    suspend fun showMyAds(
        @Header("authorization") token: String,
        @Query("user_id") userId: Int,
        @Query("page") page: Int,
        @Query("size") sizeData: Int = 10
    ): Response<ProductResponse>

    @DELETE("delete/{id}")
    suspend fun deleteProduct(
        @Header("authorization") token: String,
        @Path("id") id: Int
    ): Response<DeleteProductResponse>
}
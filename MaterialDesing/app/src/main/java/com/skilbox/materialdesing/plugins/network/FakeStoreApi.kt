package com.skilbox.materialdesing.plugins.network

import com.skilbox.materialdesing.fakestoreapi.data.Product
import retrofit2.http.GET
import retrofit2.http.Path

interface FakeStoreApi {
    @GET("/products")
    suspend fun getAllProducts(): List<Product>

    @GET("/products/{product_id}")
    suspend fun getAllProducts(
        @Path("product_id") product_id: Int
    ): Product
}

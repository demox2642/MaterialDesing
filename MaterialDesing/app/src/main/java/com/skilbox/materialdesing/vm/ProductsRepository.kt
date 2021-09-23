package com.skilbox.materialdesing.vm

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.skilbox.materialdesing.fakestoreapi.data.Product
import com.skilbox.materialdesing.plugins.network.NetworkRetrofit

class ProductsRepository : PagingSource<Int, Product>() {
    override fun getRefreshKey(state: PagingState<Int, Product>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Product> {
        return try {
            val nextOffset: Int = params.key ?: FIRST_OFFSET_INDEX
            val response = NetworkRetrofit.fakeStoreApi.getAllProducts()
            val prevOffset = if (nextOffset == 0) null else nextOffset - OFFSET_STEP

            LoadResult.Page(
                data = response,
                prevKey = prevOffset,
                nextKey = nextOffset.plus(OFFSET_STEP)
            )
        } catch (e: Exception) {
            Log.e("ProductsRepository", "Exception:$e")
            LoadResult.Error(e)
        }
    }

    suspend fun getProduct(){
        val product = NetworkRetrofit.fakeStoreApi.getAllProducts(1)
    }

    companion object {
        private const val FIRST_OFFSET_INDEX = 0
        private const val OFFSET_STEP = 10
    }
}

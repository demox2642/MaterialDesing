package com.skilbox.materialdesing.vm

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.skilbox.materialdesing.fakestoreapi.data.Product
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class ProductViewModel : ViewModel() {

    private val productLiveData = MutableLiveData<Product>()
    val productInfo: LiveData<Product>
        get() = productLiveData

    fun getAllProducts(): Flow<PagingData<Product>> {
        return Pager(
            config = PagingConfig(pageSize = 10),
            pagingSourceFactory = { ProductsRepository() }

        ).flow.cachedIn(viewModelScope)
    }

    fun getProduct(id_product: Int) {
        viewModelScope.launch {
            try {
                productLiveData.postValue(ProductsRepository().getProduct(id_product))
            } catch (e: Exception) {
                Log.e("VM", "$e")
            }
        }
    }
}

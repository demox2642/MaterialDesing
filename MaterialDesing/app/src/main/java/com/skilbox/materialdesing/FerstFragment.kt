package com.skilbox.materialdesing

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import com.skilbox.materialdesing.databinding.FragmentFerstBinding
import com.skilbox.materialdesing.listadapter.ProductListAdapter
import com.skilbox.materialdesing.plugins.ViewBindingFragment
import com.skilbox.materialdesing.vm.ProductViewModel
import kotlinx.coroutines.flow.collectLatest

class FerstFragment : ViewBindingFragment<FragmentFerstBinding>(FragmentFerstBinding::inflate) {

    private var productAdapter: ProductListAdapter ? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ProductViewModel().getProduct()
        initList()
        initViewModel()
    }

    private fun initList() {
        productAdapter = ProductListAdapter()

        with(binding.productRecycler) {
            adapter = productAdapter
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            layoutManager = GridLayoutManager(context, 2)
            setHasFixedSize(true)
        }
    }

    private fun initViewModel() {
        val viewModel = ViewModelProvider(this).get(ProductViewModel::class.java)
        lifecycleScope.launchWhenCreated {
            viewModel.getAllProducts().collectLatest {
                Log.e("FerstFragment", "initViewModel $it")
                productAdapter!!.submitData(it)
            }
        }
    }
}

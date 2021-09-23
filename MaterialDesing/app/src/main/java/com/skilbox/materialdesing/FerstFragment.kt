package com.skilbox.materialdesing

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.skilbox.materialdesing.databinding.FragmentFerstBinding
import com.skilbox.materialdesing.plugins.ViewBindingFragment
import com.skilbox.materialdesing.vm.ProductViewModel
import kotlinx.coroutines.flow.collectLatest


class FerstFragment : ViewBindingFragment<FragmentFerstBinding>(FragmentFerstBinding::inflate) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ProductViewModel().getProduct()
        initViewModel()
    }

    private fun initViewModel() {
        val viewModel = ViewModelProvider(this).get(ProductViewModel::class.java)
        lifecycleScope.launchWhenCreated {
            viewModel.getAllProducts().collectLatest {
                Log.e("FerstFragment","initViewModel $it")
               // movieAdapter!!.submitData(it)
            }
        }
    }
}
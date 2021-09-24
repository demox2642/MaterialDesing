package com.skilbox.materialdesing

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.skilbox.materialdesing.databinding.FragmentFerstBinding
import com.skilbox.materialdesing.listadapter.ProductListAdapter
import com.skilbox.materialdesing.plugins.ViewBindingFragment
import com.skilbox.materialdesing.vm.ProductViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_ferst.*
import kotlinx.coroutines.flow.collectLatest

class FerstFragment : ViewBindingFragment<FragmentFerstBinding>(FragmentFerstBinding::inflate) {

    private var productAdapter: ProductListAdapter ? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initList()
        initViewModel()

        Handler().postDelayed(
            {
                Snackbar.make(view, "Соединение с сервером отсутствует, показаны сохранённые объекты", Snackbar.LENGTH_INDEFINITE)
                    .setAction("Повторить") {
                        Snackbar.make(view, "Список обновлён", Snackbar.LENGTH_LONG)
                            .show()
                    }
                    .show()
            },
            3000
        )
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

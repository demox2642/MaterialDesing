package com.skilbox.materialdesing

import MyLinearLayoutManager
import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.skilbox.materialdesing.databinding.FragmentFerstBinding
import com.skilbox.materialdesing.listadapter.CustomItemDecotion
import com.skilbox.materialdesing.listadapter.ProductListAdapter
import com.skilbox.materialdesing.listadapter.animators.FadeInDownAnimator
import com.skilbox.materialdesing.listadapter.animators.MyGridLayoutManager
import com.skilbox.materialdesing.plugins.ViewBindingFragment
import com.skilbox.materialdesing.vm.ProductViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_ferst.*
import kotlinx.coroutines.flow.collectLatest

class FerstFragment : ViewBindingFragment<FragmentFerstBinding>(FragmentFerstBinding::inflate) {

    private var productAdapter: ProductListAdapter ? = null
    private var spanCount = 3

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (resources.configuration.screenLayout and Configuration.SCREENLAYOUT_SIZE_MASK === Configuration.SCREENLAYOUT_SIZE_LARGE) {
            spanCount = 2
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initList()

        Handler().postDelayed(
            {
                Snackbar.make(view, "Соединение с сервером отсутствует, показаны сохранённые объекты", Snackbar.LENGTH_INDEFINITE)
                    .setAction("Повторить") {
                        initViewModel()
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
            addItemDecoration(CustomItemDecotion(R.layout.product_view_for_list,100,0))
//            layoutManager = object : GridLayoutManager(context, 3) {
//                override fun checkLayoutParams(lp: RecyclerView.LayoutParams): Boolean {
//                    // force size of viewHolder here, this will override layout_height and layout_width from xml
//                    lp.height = height / spanCount
//                    lp.width = width / spanCount
//                    return true
//                }
//
//
//            }

            layoutManager= StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL)
           // layoutManager = MyGridLayoutManager(context,3)
              //  layoutManager = MyLinearLayoutManager(context,LinearLayoutManager.VERTICAL,true)
            itemAnimator=  FadeInDownAnimator()
            setHasFixedSize(true)

        }
    }

    private fun initViewModel() {
        val viewModel = ViewModelProvider(this).get(ProductViewModel::class.java)
        lifecycleScope.launchWhenCreated {
            viewModel.getAllProducts().collectLatest {
                productAdapter!!.submitData(it)
            }
        }
    }

}

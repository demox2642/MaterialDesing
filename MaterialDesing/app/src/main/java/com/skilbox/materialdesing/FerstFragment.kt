package com.skilbox.materialdesing

import android.content.res.Configuration
import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.doOnPreDraw
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.transition.MaterialElevationScale
import com.skilbox.materialdesing.databinding.FragmentFerstBinding
import com.skilbox.materialdesing.listadapter.ProductListAdapter
import com.skilbox.materialdesing.listadapter.animators.FadeInDownAnimator
import com.skilbox.materialdesing.plugins.ViewBindingFragment
import com.skilbox.materialdesing.vm.ProductViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class FerstFragment : ViewBindingFragment<FragmentFerstBinding>(FragmentFerstBinding::inflate) {

    private var productAdapter: ProductListAdapter ? = null
    private var spanCount = 3

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.e("Orient", "${Resources.getSystem().configuration.orientation }")
        if (Resources.getSystem().configuration.orientation == 2) {
            spanCount = getScreenSizeCategory() + 1
        } else {
            spanCount = getScreenSizeCategory()
        }

        postponeEnterTransition()
        view.doOnPreDraw { startPostponedEnterTransition() }

        initList()
        lifecycleScope.launch {
            delay(2000)
            Snackbar.make(view, "Соединение с сервером отсутствует, показаны сохранённые объекты", Snackbar.LENGTH_INDEFINITE)
                .setAction("Повторить") {
                    initViewModel()
                    Snackbar.make(view, "Список обновлён", Snackbar.LENGTH_LONG)
                        .show()
                }
                .show()
        }
    }
    private fun getScreenSizeCategory(): Int {
        return when (
            resources.configuration.screenLayout
                and Configuration.SCREENLAYOUT_SIZE_MASK
        ) {
            Configuration.SCREENLAYOUT_SIZE_XLARGE -> 3
            Configuration.SCREENLAYOUT_SIZE_LARGE -> 3
            Configuration.SCREENLAYOUT_SIZE_NORMAL -> 2
            Configuration.SCREENLAYOUT_SIZE_SMALL -> 2
            Configuration.SCREENLAYOUT_SIZE_UNDEFINED -> 1
            else -> 0
        }
    }

    private fun initList() {
        productAdapter = ProductListAdapter { id_product -> showDetailInfo(id_product) }
        with(binding.productRecycler) {
            adapter = productAdapter
            layoutManager = GridLayoutManager(context, spanCount)
            itemAnimator = FadeInDownAnimator()
            setHasFixedSize(true)
        }
    }
    private fun showDetailInfo(id_product: Int) {
        exitTransition = MaterialElevationScale(false).apply {
            duration = 300.toLong()
        }
        reenterTransition = MaterialElevationScale(true).apply {
            duration = 300.toLong()
        }

        val args = FerstFragmentDirections.actionFerstFragmentToDetailInfoFragment(id_product)
        findNavController().navigate(args)
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

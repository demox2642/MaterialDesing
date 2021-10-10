package com.skilbox.materialdesing

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.google.android.material.transition.MaterialContainerTransform
import com.skilbox.materialdesing.databinding.FragmentDetailInfoBinding
import com.skilbox.materialdesing.fakestoreapi.data.Product
import com.skilbox.materialdesing.vm.ProductViewModel

class DetailInfoFragment : ViewBindingFragment<FragmentDetailInfoBinding>(FragmentDetailInfoBinding::inflate){

    private val args : DetailInfoFragmentArgs by navArgs()
    private val viewModel:ProductViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition = MaterialContainerTransform().apply {
            drawingViewId = R.id.nav_fragment
            duration = 400.toLong()
            scrimColor = Color.TRANSPARENT

        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getProduct(args.productId)
        bindViewModel()
    }


    private fun bindViewModel() {
        viewModel.productInfo.observe(viewLifecycleOwner){updateView(it)}
    }

    private fun updateView(product: Product){
        binding.producktName.text = product.title
        binding.category.text=product.category
        binding.description.text=product.description
        binding.productPrice.text = product.price+"$"

        Glide.with(binding.producktImage)
            .load(product.image)
            .placeholder(R.drawable.load_imege)
            .into(binding.producktImage)
            .view
    }
}

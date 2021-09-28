package com.skilbox.materialdesing.listadapter

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.transition.AutoTransition
import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.skilbox.materialdesing.R
import com.skilbox.materialdesing.fakestoreapi.data.Product
import kotlinx.android.synthetic.main.product_view_for_list.view.*

class ProductListAdapter :
    PagingDataAdapter<Product, ProductListAdapter.ProductViewHolder>(DiffUtilCallBack()) {

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProductViewHolder {
        val inflater =
            LayoutInflater.from(parent.context).inflate(R.layout.product_view_for_list, parent, false)

        return ProductViewHolder(inflater)
    }

    class ProductViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        @SuppressLint("SetTextI18n")
        fun bind(data: Product) {
            itemView.product_name.text = data.title
            itemView.price.text = "${data.price}$"
            itemView.rating.text = "rating:${data.rating.rate} of votes:${data.rating.count} "
            Glide.with(itemView.image_product)
                .load(data.image)
                .into(itemView.image_product)
                .view

            itemView.setOnClickListener {
                if (itemView.expandableView.visibility == View.GONE) {
                    TransitionManager.beginDelayedTransition(itemView.product_card, AutoTransition())
                    itemView.expandableView.visibility = View.VISIBLE

                    val animator = ObjectAnimator.ofFloat(itemView.shopping_cart, "alpha", 0f, 1f)
                    animator.duration = 1000
                    animator.addListener(object : AnimatorListenerAdapter() {
                        override fun onAnimationStart(animation: Animator?) {
                            itemView.shopping_cart.isEnabled = false
                        }
                        override fun onAnimationEnd(animation: Animator?) {
                            itemView.shopping_cart.isEnabled = true
                        }
                    })
                    animator.start()
                } else {
                    TransitionManager.beginDelayedTransition(itemView.product_card, AutoTransition())
                    itemView.expandableView.visibility = View.GONE

                    val animator = ObjectAnimator.ofFloat(itemView.baseline_info, "alpha", 1f, 0f)
                    animator.duration = 1000
                    animator.addListener(object : AnimatorListenerAdapter() {
                        override fun onAnimationStart(animation: Animator?) {
                            itemView.shopping_cart.isEnabled = false
                        }
                        override fun onAnimationEnd(animation: Animator?) {
                            itemView.shopping_cart.isEnabled = true
                        }
                    })
                    animator.start()
                }
            }
        }
    }

    class DiffUtilCallBack : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem == newItem
        }
    }
}

package com.skilbox.materialdesing.listadapter

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.transition.AutoTransition
import android.transition.TransitionManager
import android.util.Log
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

class ProductListAdapter(
    private val  onItemClick: (product_id: Int) -> Unit
) :
    PagingDataAdapter<Product, ProductListAdapter.ProductViewHolder>(DiffUtilCallBack()) {

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it,onItemClick = onItemClick)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {

        val inflater =
            LayoutInflater.from(parent.context).inflate(R.layout.product_view_for_list, parent, false)

        return ProductViewHolder(inflater)
    }


    class ProductViewHolder(view: View) : RecyclerView.ViewHolder(view) {

       var parent = itemView.height



        @SuppressLint("SetTextI18n")
        fun bind(data: Product, onItemClick: (product_id: Int) -> Unit) {
            itemView.product_name.text = data.title
            itemView.price.text = "${data.price}$"
            itemView.rating.text = "rating:${data.rating.rate} of votes:${data.rating.count} "
            Glide.with(itemView.image_product)
                .load(data.image)
                .into(itemView.image_product)
                .view

            itemView.product_info.setOnClickListener{
                onItemClick(data.id)
            }


            itemView.setOnClickListener {
                Log.e("ProductViewHolder","setOnClickListener before if height=${itemView.height} width=${parent}")
                parent=itemView.height
                if (itemView.expandableView.visibility == View.GONE) {
                    Log.e("ProductViewHolder","setOnClickListener if true height=${itemView.height} width=${parent}")
                    itemAnimation(true)
                    itemView.parent.showContextMenuForChild(itemView,0f,1f)

                } else {


                    itemAnimation(false)
                    Log.e("ProductViewHolder","setOnClickListener if false height=${itemView.height} width=${parent}")
                  itemView.layoutParams.height=419
                }
            }
        }



        private fun itemAnimation(itemStatus:Boolean){
            TransitionManager.beginDelayedTransition(itemView.product_card, AutoTransition())
            val animator:ObjectAnimator
            when(itemStatus){
                    true-> {
                        animator = ObjectAnimator.ofFloat(itemView.shopping_cart, "alpha", 0f, 1f)
                        itemView.expandableView.visibility = View.VISIBLE
                    }
                    false->{
                        animator = ObjectAnimator.ofFloat(itemView.shopping_cart, "alpha", 1f, 0f)
                        itemView.expandableView.visibility = View.GONE

                    }

            }
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

    class DiffUtilCallBack : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem == newItem
        }
    }


}

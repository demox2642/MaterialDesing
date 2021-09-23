package com.skilbox.materialdesing.listadapter

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

        fun bind(data: Product) {
//            itemView.movie_title.text = data.display_title
//            itemView.movie_summary_short.text = data.summary_short
//
            Glide.with(itemView.image_product)
                .load(data.image)
               // .placeholder(R.drawable.nytimes_logo)
                .into(itemView.image_product)
                .view
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

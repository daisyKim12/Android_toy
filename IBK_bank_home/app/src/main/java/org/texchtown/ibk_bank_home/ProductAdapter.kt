package org.texchtown.ibk_bank_home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import org.texchtown.ibk_bank_home.databinding.PdtItemsBinding

class ProductAdapter(private val productList: List<Product>)
    : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    inner class ProductViewHolder(private val binding: PdtItemsBinding)
        :RecyclerView.ViewHolder(binding.root){

        fun bindItem(product: Product){
            binding.category.setImageResource(product.category)
            binding.title.text = product.title
            binding.detail1.text = product.detail1
            binding.pdtIv.setImageResource(product.photo)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        return ProductViewHolder(
            PdtItemsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bindItem(productList[position])
    }

    override fun getItemCount(): Int {
        return productList.size
    }
}
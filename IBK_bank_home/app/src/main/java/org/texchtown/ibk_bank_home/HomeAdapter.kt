package org.texchtown.ibk_bank_home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.texchtown.ibk_bank_home.databinding.HomeItemsBinding

class HomeAdapter(private val homeList: List<Home>)
    : RecyclerView.Adapter<HomeAdapter.HomeViewHolder>() {

        inner class HomeViewHolder(private val binding: HomeItemsBinding)
            : RecyclerView.ViewHolder(binding.root){

            fun bindItem(home: Home) {
                binding.homeIv.setImageResource(home.advertisePhoto)
            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        return HomeViewHolder(
            HomeItemsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        holder.bindItem(homeList[position])
    }

    override fun getItemCount(): Int {
        return homeList.size
    }

}
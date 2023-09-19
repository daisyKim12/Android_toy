package org.texchtown.cgv.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.texchtown.cgv.data.adv.TopAdv
import org.texchtown.cgv.databinding.RvHomeTopItemBinding

class HomeTopAdvAdapter(private val homeList: List<TopAdv>)
    : RecyclerView.Adapter<HomeTopAdvAdapter.HomeViewHolder>() {

    inner class HomeViewHolder(private val binding: RvHomeTopItemBinding)
        : RecyclerView.ViewHolder(binding.root) {

            fun bindItem(homeTopAdv: TopAdv) {
                binding.banner.setImageResource(homeTopAdv.photo)
            }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        return HomeViewHolder(
            RvHomeTopItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false)
        )
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        val position = position % 3
        holder.bindItem(homeList[position])

    }

    override fun getItemCount(): Int {
        //return homeList.size
        return Integer.MAX_VALUE;
    }


}
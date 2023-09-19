package org.texchtown.cgv.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.texchtown.cgv.data.adv.BottomAdv
import org.texchtown.cgv.databinding.RvBottomItemBinding

class BottomSheetAdvAdapter(private val bottomList: List<BottomAdv>)
    : RecyclerView.Adapter<BottomSheetAdvAdapter.BottomViewHolder>() {

    inner class BottomViewHolder(private val binding: RvBottomItemBinding)
        : RecyclerView.ViewHolder(binding.root) {

            fun bindItem(bottomAdv: BottomAdv) {
                binding.advertise.setImageResource(bottomAdv.photo)
            }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BottomViewHolder {
        return BottomViewHolder(
            RvBottomItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false)
        )

    }

    override fun onBindViewHolder(holder: BottomViewHolder, position: Int) {
        holder.bindItem(bottomList[position])
    }

    override fun getItemCount(): Int {
        return bottomList.size
    }


}
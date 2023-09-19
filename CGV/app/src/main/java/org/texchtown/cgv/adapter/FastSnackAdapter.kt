package org.texchtown.cgv.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.texchtown.cgv.data.adv.BottomAdv
import org.texchtown.cgv.databinding.RvEventItemBinding
import org.texchtown.cgv.databinding.RvFastSnackItemBinding

class FastSnackAdapter (private val snackList: List<BottomAdv>)
    : RecyclerView.Adapter<FastSnackAdapter.SnackViewHolder>(){

    inner class SnackViewHolder(private val binding: RvFastSnackItemBinding)
        : RecyclerView.ViewHolder(binding.root) {

        fun bindItem(snackAdv: BottomAdv) {
            binding.snack.setImageResource(snackAdv.photo)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SnackViewHolder {
        return SnackViewHolder(
            RvFastSnackItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: SnackViewHolder, position: Int) {
        holder.bindItem(snackList[position])
    }

    override fun getItemCount(): Int {
        return snackList.size
    }
}
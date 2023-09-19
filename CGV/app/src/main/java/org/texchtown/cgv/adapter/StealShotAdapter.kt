package org.texchtown.cgv.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import org.texchtown.cgv.data.naverstealshot.ItemX
import org.texchtown.cgv.databinding.RvStealShotItemBinding

class StealShotAdapter(private val context: Context, private val itemXList: List<ItemX>)
    : RecyclerView.Adapter<StealShotAdapter.StealShotViewHoler>() {

    inner class StealShotViewHoler(private val binding: RvStealShotItemBinding)
        : RecyclerView.ViewHolder(binding.root) {

        fun bindItem(itemX: ItemX) {
            Glide.with(context).load(itemX.link).into(binding.imgStealshot)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StealShotViewHoler {

        return StealShotViewHoler(
            RvStealShotItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false)
        )
    }

    override fun onBindViewHolder(holder: StealShotViewHoler, position: Int) {
        holder.bindItem(itemXList[position])
    }

    override fun getItemCount(): Int {
        return itemXList.size
    }
}
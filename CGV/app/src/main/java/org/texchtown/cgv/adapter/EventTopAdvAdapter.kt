package org.texchtown.cgv.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.texchtown.cgv.data.adv.TopAdv
import org.texchtown.cgv.databinding.RvEventTopItemBinding
import org.texchtown.cgv.databinding.RvHomeTopItemBinding

class EventTopAdvAdapter(private val eventAdvList: List<TopAdv>)
    : RecyclerView.Adapter<EventTopAdvAdapter.EventViewHolder>() {

    inner class EventViewHolder(private val binding: RvEventTopItemBinding)
        : RecyclerView.ViewHolder(binding.root) {

        fun bindItem(homeTopAdv: TopAdv) {
            binding.banner.setImageResource(homeTopAdv.photo)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventTopAdvAdapter.EventViewHolder {
        return EventViewHolder(
            RvEventTopItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )

        )
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        val position = position % 7
        holder.bindItem(eventAdvList[position])
    }

    override fun getItemCount(): Int {
        return Integer.MAX_VALUE;    }
}
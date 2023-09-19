package org.texchtown.cgv.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.texchtown.cgv.data.adv.BottomAdv
import org.texchtown.cgv.data.adv.TopAdv
import org.texchtown.cgv.databinding.RvEventItemBinding
import org.texchtown.cgv.databinding.RvEventTopItemBinding

class EventAdapter (private val eventList: List<BottomAdv>)
    : RecyclerView.Adapter<EventAdapter.EventViewHolder>(){

    inner class EventViewHolder(private val binding: RvEventItemBinding)
        : RecyclerView.ViewHolder(binding.root) {

        fun bindItem(homeTopAdv: BottomAdv) {
            binding.event.setImageResource(homeTopAdv.photo)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventAdapter.EventViewHolder {
        return EventViewHolder(
            RvEventItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false)
        )

    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        holder.bindItem(eventList[position])
    }

    override fun getItemCount(): Int {
        return eventList.size
    }
}
package org.texchtown.cgv.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.texchtown.cgv.data.adv.BottomAdv
import org.texchtown.cgv.data.adv.Gift
import org.texchtown.cgv.data.adv.Group
import org.texchtown.cgv.databinding.RvEventItemBinding
import org.texchtown.cgv.databinding.RvGiftHeaderBinding
import org.texchtown.cgv.databinding.RvGiftItemBinding
import org.texchtown.cgv.fragment.GiftShopFragment

class GiftAdapter(val groupList: List<Group>, val giftList: List<Gift>)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var giftPosition = 0

    inner class GroupViewHolder(private val binding : RvGiftHeaderBinding)
        : RecyclerView.ViewHolder(binding.root){

        fun bindItem(group:Group) {
            binding.txtGroup.text = group.groupName
        }
    }

    inner class GiftViewHolder(private val binding: RvGiftItemBinding)
        : RecyclerView.ViewHolder(binding.root) {

        fun bindItem(gift: Gift) {
            binding.img.setImageResource(gift.giftPhoto)
            binding.txtGiftName.text = gift.giftName
            binding.txtGiftDetail.text = gift.giftDetail
            binding.txtGiftPrice.text = gift.giftPrice
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if(viewType == 0) {
            return GroupViewHolder(
                RvGiftHeaderBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        } else {
            return GiftViewHolder(
                RvGiftItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(position % 4 == 0) {
            (holder as GroupViewHolder).bindItem(groupList[position % 4])
        } else {
            (holder as GiftViewHolder).bindItem(giftList[giftPosition++])
            //(position%4) + position/4*3
        }
    }

    override fun getItemViewType(position: Int): Int {
        return position % 4
    }

    override fun getItemCount(): Int {
        return groupList.size + giftList.size
    }
}
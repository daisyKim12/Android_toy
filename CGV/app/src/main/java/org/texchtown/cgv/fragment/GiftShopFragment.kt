package org.texchtown.cgv.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import org.texchtown.cgv.R
import org.texchtown.cgv.adapter.FastSnackAdapter
import org.texchtown.cgv.adapter.GiftAdapter
import org.texchtown.cgv.data.adv.PageLists
import org.texchtown.cgv.databinding.FragmentGiftShopBinding

class GiftShopFragment : Fragment() {

    private lateinit var binding: FragmentGiftShopBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGiftShopBinding.inflate(inflater, container, false)
        setUpRecyclerView()

        return binding.root
    }

    private fun setUpRecyclerView() {
        binding.rvGift.setHasFixedSize(true)
        val linearLayoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        binding.rvGift.layoutManager = linearLayoutManager
        val adapter = GiftAdapter(PageLists.groupList, PageLists.giftSlide)
        adapter.notifyDataSetChanged()
        binding.rvGift.adapter = adapter
    }

}
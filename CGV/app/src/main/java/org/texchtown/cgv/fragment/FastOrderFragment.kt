package org.texchtown.cgv.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import org.texchtown.cgv.R
import org.texchtown.cgv.adapter.EventAdapter
import org.texchtown.cgv.adapter.FastSnackAdapter
import org.texchtown.cgv.data.adv.PageLists
import org.texchtown.cgv.databinding.FragmentFastOrderBinding
import org.texchtown.cgv.databinding.RvFastSnackItemBinding

class FastOrderFragment : Fragment() {

    private lateinit var binding: FragmentFastOrderBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFastOrderBinding.inflate(inflater, container, false)

        setUpRecyclerView()
        return binding.root
    }

    private fun setUpRecyclerView() {
        binding.rvSnack.setHasFixedSize(true)
        val linearLayoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        binding.rvSnack.layoutManager = linearLayoutManager
        val adapter = FastSnackAdapter(PageLists.snackSlides)
        adapter.notifyDataSetChanged()
        binding.rvSnack.adapter = adapter
    }

}
package org.texchtown.cgv.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.texchtown.cgv.R
import org.texchtown.cgv.databinding.FragmentAtCgvBinding

class AtCgvFragment : Fragment() {

    private lateinit var binding : FragmentAtCgvBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAtCgvBinding.inflate(inflater, container, false)
        return binding.root
    }

}
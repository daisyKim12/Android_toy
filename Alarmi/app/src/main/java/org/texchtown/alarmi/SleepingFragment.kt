package org.texchtown.alarmi

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.texchtown.alarmi.databinding.FragmentSleepingBinding

class SleepingFragment : Fragment() {

    private lateinit var binding: FragmentSleepingBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSleepingBinding.inflate(inflater, container, false)
        return binding.root
    }



}
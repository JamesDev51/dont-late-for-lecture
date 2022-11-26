package com.team8.dlfl.view.mainview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.team8.dlfl.R
import com.team8.dlfl.databinding.FragmentMarkBinding
import com.team8.dlfl.databinding.FragmentMyPageBinding


class MyPageFragment : Fragment() {

    var binding : FragmentMyPageBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMyPageBinding.inflate(inflater)


        return binding?.root
    }


}
package com.team8.dlfl

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.team8.dlfl.databinding.FragmentEntryBinding


class EntryFragment : Fragment() {

    var binding: FragmentEntryBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEntryBinding.inflate(inflater)

        // Inflate the layout for this fragment
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        binding?.btnTweb?.setOnClickListener {  //  최신알림 페이지 가기
//            findNavController().navigate(R.id.action_entryFragment_to_twitterWebFragment)
//        }
//        binding?.btnMark?.setOnClickListener {  //  즐겨찾기 페이지 가기
//            findNavController().navigate(R.id.action_entryFragment_to_markFragment)
//        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }


}
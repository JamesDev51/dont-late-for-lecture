package com.team8.dlfl

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.team8.dlfl.databinding.FragmentAddBinding


class AddFragment : Fragment() {

    var binding: FragmentAddBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddBinding.inflate(inflater)
        // Inflate the layout for this fragment
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.btnAdd?.setOnClickListener { //  등록하고 즐겨찾기 페이지 가기
            findNavController().navigate(R.id.action_addFragment_to_markFragment)
        }
        binding?.btnCancel?.setOnClickListener {  //  등록 취소하면 즐겨찾기 페이지 가기
            findNavController().navigate(R.id.action_addFragment_to_markFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

}
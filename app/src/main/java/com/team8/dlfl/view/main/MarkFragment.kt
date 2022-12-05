package com.team8.dlfl.view.main

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.team8.dlfl.R
import com.team8.dlfl.adapter.MarkListAdapter
import com.team8.dlfl.databinding.FragmentMarkBinding
import com.team8.dlfl.viewmodel.MarkViewModel
import com.team8.dlfl.viewmodel.StationStatusViewModel

private const val TAG = "MarkFragment"

class MarkFragment : Fragment() {

    val markViewModel: MarkViewModel by activityViewModels()
    val stationStatusViewModel: StationStatusViewModel by activityViewModels()

    var binding : FragmentMarkBinding? = null



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): ConstraintLayout? {
        binding = FragmentMarkBinding.inflate(inflater)

        return binding?.root// 원래있던거
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        markViewModel.retrieveMarkList()

        markViewModel.markList.observe(viewLifecycleOwner) {
            binding?.recItems?.adapter?.notifyDataSetChanged()
        }

        binding?.btnPlus?.setOnClickListener {  //  즐겨찾기 등록 페이지 가기
            findNavController().navigate(R.id.action_markFragment_to_addFragment)
        }

        binding?.recItems?.layoutManager = LinearLayoutManager(context) //차곡차곡
        var markListAdapter = MarkListAdapter(object: MarkListAdapter.OnItemClickListener {
            override fun onItemClick(selectedStationName:String) {
                Log.d(TAG, "onItemClick 호출")
                val bundle = bundleOf("selectedStationName" to selectedStationName )
                findNavController().navigate(R.id.action_markFragment_to_latelyFragment, bundle)
            }
        }, markViewModel.markList)
        binding?.recItems?.adapter= markListAdapter

        binding?.btnDelete?.setOnClickListener {
            markViewModel.deleteMark()
            markListAdapter= MarkListAdapter(object: MarkListAdapter.OnItemClickListener {
                override fun onItemClick(selectedStationName:String) {
                    Log.d(TAG, "onItemClick 호출")
                    val bundle = bundleOf("selectedStationName" to selectedStationName )
                    findNavController().navigate(R.id.action_markFragment_to_latelyFragment,bundle)
                }
            },markViewModel.markList)
            binding?.recItems?.adapter=markListAdapter
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}
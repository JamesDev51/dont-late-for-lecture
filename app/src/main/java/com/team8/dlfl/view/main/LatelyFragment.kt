package com.team8.dlfl.view.main

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.team8.dlfl.adapter.StationStatusListAdapter
import com.team8.dlfl.databinding.FragmentLatelyBinding
import com.team8.dlfl.viewmodel.StationStatusViewModel


class LatelyFragment : Fragment() {

    var binding: FragmentLatelyBinding? = null
    private val viewModel: StationStatusViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            viewModel.selectedStationName=it.getString("selectedStationName" ?: "화전").toString()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding= FragmentLatelyBinding.inflate(inflater)
        return binding?.root
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.retrieveStationStatusList()
        binding?.recItems?.adapter = StationStatusListAdapter(viewModel.stationStatusList)
        viewModel.stationStatusList.observe(viewLifecycleOwner) {
            binding?.recItems?.adapter?.notifyDataSetChanged()
        }

        binding?.recItems?.layoutManager = LinearLayoutManager(context)
    }
    override fun onDestroyView() {
        super.onDestroyView()

        binding = null
    }
}
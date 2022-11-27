package com.team8.dlfl.view.mainview

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.team8.dlfl.R
import com.team8.dlfl.adapter.MarkListAdapter
import com.team8.dlfl.databinding.FragmentMarkBinding
import com.team8.dlfl.viewmodel.MarkViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

private const val TAG = "MarkFragment"

class MarkFragment : Fragment() {

    val viewModel: MarkViewModel by activityViewModels()
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

        viewModel.retrieveMarkList()

        viewModel.markList.observe(viewLifecycleOwner) {
            binding?.recMarks?.adapter?.notifyDataSetChanged()
        }

        binding?.btnPlus?.setOnClickListener {  //  즐겨찾기 등록 페이지 가기
            findNavController().navigate(R.id.action_markFragment_to_addFragment)
        }

        binding?.recMarks?.layoutManager = LinearLayoutManager(context) //차곡차곡
        var markListAdapter = MarkListAdapter(viewModel.markList)
        binding?.recMarks?.adapter= markListAdapter

        binding?.btnDelete?.setOnClickListener {
            viewModel.deleteMark()
            markListAdapter= MarkListAdapter(viewModel.markList)
            binding?.recMarks?.adapter=markListAdapter
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}
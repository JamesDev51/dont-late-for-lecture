package com.team8.dlfl.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.team8.dlfl.R
import com.team8.dlfl.adapter.MarksAdapter
import com.team8.dlfl.databinding.FragmentMarkBinding
import com.team8.dlfl.viewmodel.MarkViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

private const val TAG = "MarkFragment"

class MarkFragment : Fragment() {

    val viewModel: MarkViewModel by activityViewModels()
    lateinit var binding : FragmentMarkBinding  //추가



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMarkBinding.inflate(inflater)

        binding.recMarks.layoutManager = LinearLayoutManager(context) //차곡차곡

        CoroutineScope(Dispatchers.IO).launch {
            runBlocking {
                if (viewModel.init()) {
                    val marksAdapter= viewModel.markList.value?.let { MarksAdapter(it) }
                    viewModel.repository.marksAdapter=marksAdapter!!
                    Log.d(TAG,"viewModel.markList.value : ${viewModel.markList.value}")
                    Log.d(TAG,"marksAdapter : $marksAdapter")
                    activity?.runOnUiThread{
                        
                        Log.d(TAG,"등록 부분 : 이제 등록")
                        binding.recMarks.adapter = marksAdapter // 추가
                    }
                }
            }
        }



        viewModel.markList.observe(viewLifecycleOwner){

        }


        return binding.root// 원래있던거
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.btnPlus?.setOnClickListener {  //  즐겨찾기 등록 페이지 가기
            findNavController().navigate(R.id.action_markFragment_to_addFragment)
        }
        //  실시간 지하철 위치정보 페이지는 리사이클러 뷰를 이용해서 가기
    }

    companion object {
        @JvmStatic fun newInstance() =
            LoginFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }

}
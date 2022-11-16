package com.team8.dlfl.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.team8.dlfl.R
import com.team8.dlfl.databinding.FragmentMarkBinding

class MarkFragment : Fragment() {

    val marks = arrayOf( // 파이어베이스에서 데이터 가져오기
        Mark("홍대입구", "2호선", "대곡", "경의선"),
        Mark("구파발", "3호선", "화전", "경의선")
    )
    //val marks = mutableListOf<Mark>()



    //var binding: FragmentMarkBinding? = null  이게 원래있던거
    lateinit var binding : FragmentMarkBinding  //추가

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMarkBinding.inflate(inflater)
        // Inflate the layout for this fragment

        binding.recMarks.layoutManager = LinearLayoutManager(context) //차곡차곡
        binding.recMarks.adapter = MarksAdapter(marks) // 추가

        return binding?.root// 원래있던거
        //return inflater.inflate(R.layout.fragment_mark, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.btnPlus?.setOnClickListener {  //  즐겨찾기 등록 페이지 가기
            findNavController().navigate(R.id.action_markFragment_to_addFragment)
        }
        //  실시간 지하철 위치정보 페이지는 리사이클러 뷰를 이용해서 가기
    }


}
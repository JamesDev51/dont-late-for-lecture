package com.team8.dlfl.twitter.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.team8.dlfl.databinding.FragmentTwitterWebBinding
import com.team8.dlfl.twitter.adapter.TwitterAdapter
import com.team8.dlfl.twitter.TwitterViewModel


class TwitterWebFragment : Fragment() {

    var binding: FragmentTwitterWebBinding? = null
    private val viewModel: TwitterViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTwitterWebBinding.inflate(inflater)

        return binding?.root
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 뷰모델의 numPosts를 보고 있다가 바뀌면 바꿔준다
        viewModel.notices.observe(viewLifecycleOwner) {
            binding?.txtNumNotices?.text = it.size.toString()
            binding?.recNotices?.adapter?.notifyDataSetChanged()
        }
        binding?.btnLately?.setOnClickListener {
            viewModel.retrieveNotices()
        }

        binding?.recNotices?.layoutManager = LinearLayoutManager(context)
        binding?.recNotices?.adapter = TwitterAdapter(viewModel.notices)
    }

    override fun onDestroyView() {
        super.onDestroyView()

        binding = null
    }


}
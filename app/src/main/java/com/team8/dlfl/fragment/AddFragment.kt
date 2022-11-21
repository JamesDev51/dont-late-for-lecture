package com.team8.dlfl.fragment

import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AutoCompleteTextView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.gms.common.internal.service.Common
import com.team8.dlfl.R
import com.team8.dlfl.activity.MainActivity
import com.team8.dlfl.databinding.FragmentAddBinding
import com.team8.dlfl.dto.CommonResponseDto
import com.team8.dlfl.model.MarkModel
import com.team8.dlfl.model.StationModel
import com.team8.dlfl.viewmodel.MarkViewModel
import com.team8.dlfl.viewmodel.SubwayViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


private const val TAG="AddFragment"
class AddFragment : Fragment() {

    private var binding: FragmentAddBinding? = null
    private val markViewModel: MarkViewModel by activityViewModels()
    private val subwayViewModel: SubwayViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddBinding.inflate(inflater)
        markViewModel.mainActivity=this.activity as MainActivity
        return binding?.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val editTextDeparture = binding?.editTextDeparture
        val editTextArrival = binding?.editTextArrival
        val btnAdd = binding?.btnAdd

        editTextDeparture?.setOnKeyListener { _, keyCode, event ->
            if(event.action ==  KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_ENTER){
                subwayViewModel.findStationName(editTextDeparture,"departure")
            }
            false
        }
        editTextArrival?.setOnKeyListener { _, keyCode, event ->
            if(event.action ==  KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_ENTER){
                subwayViewModel.findStationName(editTextArrival,"arrival")
            }
            false
        }

        btnAdd?.setOnClickListener {

            CoroutineScope(Dispatchers.IO).launch {
                var result:Boolean
                runBlocking {
                    result = markViewModel.uploadMark(subwayViewModel.departure, subwayViewModel.arrival)


                }
                Log.d(TAG, "markViwModel result: $result")
                if(result){
                    activity?.runOnUiThread {
                        findNavController().navigate(R.id.action_addFragment_to_markFragment)
                    }
                }
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}

package com.team8.dlfl.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AutoCompleteTextView
import androidx.fragment.app.Fragment
import com.team8.dlfl.databinding.FragmentAddBinding
import com.team8.dlfl.model.Station


class AddFragment : Fragment() {

    private var binding: FragmentAddBinding? = null

    private var stationList = ArrayList<Station>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentAddBinding.inflate(layoutInflater)


    }

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

        val editTextDeparture = binding?.editTextDeparture
        val editTextArrival = binding?.editTextArrival

        editTextDeparture?.setOnKeyListener { _, _, _ ->
            findStationName(editTextDeparture)
            false
        }
        editTextArrival?.setOnKeyListener { _, _, _ ->
            findStationName(editTextArrival)
            false
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private fun findStationName(editText: AutoCompleteTextView?) {

        val keyword = editText?.text.toString()

    }
}
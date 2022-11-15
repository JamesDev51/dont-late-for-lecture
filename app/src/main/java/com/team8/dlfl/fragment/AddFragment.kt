package com.team8.dlfl.fragment

import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AutoCompleteTextView
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.team8.dlfl.adapter.StationAdapter
import com.team8.dlfl.databinding.FragmentAddBinding
import com.team8.dlfl.db.DBHelper
import com.team8.dlfl.dto.StationDto
import com.team8.dlfl.model.Station


private const val TAG="AddFragment"
class AddFragment : Fragment() {

    private var binding: FragmentAddBinding? = null
    private var stationList = ArrayList<Station>()
    private var mAuth: FirebaseAuth? = null
    lateinit var user:FirebaseUser

    private lateinit var departure: HashMap<String, String>
    private lateinit var middle: HashMap<String, String>
    private lateinit var arrival: HashMap<String, String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentAddBinding.inflate(layoutInflater)


    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddBinding.inflate(inflater)

        mAuth = FirebaseAuth.getInstance()
        val user= mAuth?.currentUser

        Log.d(TAG,"user info:"+user?.uid  )


        return binding?.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val editTextDeparture = binding?.editTextDeparture
        val editTextMiddle = binding?.editTextMiddle
        val editTextArrival = binding?.editTextArrival
        val btnAdd = binding?.btnAdd
        val btnCancel = binding?.btnCancel

        editTextDeparture?.setOnKeyListener { _, keyCode, event ->
            if(event.action ==  KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_ENTER){
                findStationName(editTextDeparture)
            }
            false
        }
        editTextMiddle?.setOnKeyListener { _, keyCode, event ->
            if(event.action ==  KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_ENTER){
                findStationName(editTextMiddle)
            }
            false
        }
        editTextArrival?.setOnKeyListener { _, keyCode, event ->
            if(event.action ==  KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_ENTER){
                findStationName(editTextArrival)
            }
            false
        }

        btnAdd?.setOnClickListener {
            setStationDtoFromText(editTextDeparture, editTextMiddle, editTextArrival)

        }
    }

    private fun setStationDtoFromText(editTextDeparture: AutoCompleteTextView?, editTextMiddle: AutoCompleteTextView?, editTextArrival: AutoCompleteTextView?) {
        val departureStr = editTextDeparture?.text
        val middleStr = editTextMiddle?.text
        val arrivalStr = editTextArrival?.text
        Log.d(TAG, "$departureStr / $middleStr / $arrivalStr")

        val departureArr = departureStr?.split("  ")?.toMutableList()
        val middleArr = middleStr?.split("  ")?.toMutableList()
        val arrivalArr = arrivalStr?.split("  ")?.toMutableList()

        departureArr?.get(2)?.trimStart('(')?.trimEnd(')')?.let { it1 -> departureArr.set(2, it1) }
        middleArr?.get(2)?.trimStart('(')?.trimEnd(')')?.let { it1 -> middleArr.set(2, it1) }
        arrivalArr?.get(2)?.trimStart('(')?.trimEnd(')')?.let { it1 -> arrivalArr.set(2, it1) }
        Log.d(TAG, "$departureArr / $middleArr / $arrivalArr")


        TODO("해시맵에 넣고 파이어베이스로 추가하는것까지")

        Log.d(TAG, "$departure / $middle / $arrival")

    }


    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
    private fun createSubwayStationList(keyword:String) {
        val dbHelper = DBHelper(context)
        dbHelper.createDataBase()
        if (dbHelper.openDataBase()) {
            val db = dbHelper.mDataBase
            val query = "SELECT * FROM subway_station WHERE station_name LIKE '%$keyword%'"
            Log.d(TAG,"Query : $query")
            val cursor = db.rawQuery("SELECT * FROM subway_station WHERE station_name LIKE '%$keyword%'", null)
            while (cursor.moveToNext()) {

                stationList.add(Station(cursor.getString(0)?:"", cursor.getString(1)?:"", cursor.getString(2) ?: "", cursor.getString(3) ?: "", cursor.getString(4) ?: ""))
            }
            cursor.close()
            dbHelper.close()
        }
    }

    private fun findStationName(editText: AutoCompleteTextView?) {
        stationList.clear()

        val keyword = editText?.text.toString()
        if (keyword != "") {
            createSubwayStationList(keyword)

            val adapter = StationAdapter(requireContext(),stationList)
            editText?.setAdapter(adapter)
        }
    }
}

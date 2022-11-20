package com.team8.dlfl.fragment

import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AutoCompleteTextView
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.team8.dlfl.adapter.StationAdapter
import com.team8.dlfl.databinding.FragmentAddBinding
import com.team8.dlfl.db.DBHelper
import com.team8.dlfl.model.MarkModel
import com.team8.dlfl.model.StationModel
import com.team8.dlfl.repository.MarkRepository


private const val TAG="AddFragment"
class AddFragment : Fragment() {

    private var binding: FragmentAddBinding? = null
    private var stationModelList = ArrayList<StationModel>()
    private var mAuth: FirebaseAuth? = null
    lateinit var user:FirebaseUser
    private val markRepository = MarkRepository()
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
        val editTextArrival = binding?.editTextArrival
        val btnAdd = binding?.btnAdd
        val btnCancel = binding?.btnCancel

        editTextDeparture?.setOnKeyListener { _, keyCode, event ->
            if(event.action ==  KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_ENTER){
                findStationName(editTextDeparture)
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
            setStationDtoFromText(editTextDeparture, editTextArrival)

        }
    }

    private fun setStationDtoFromText(editTextDeparture: AutoCompleteTextView?, editTextArrival: AutoCompleteTextView?) {
        val departureStr = editTextDeparture?.text
        val arrivalStr = editTextArrival?.text
        Log.d(TAG, "$departureStr / $arrivalStr")

        val departureArr = departureStr?.split("  ")?.toMutableList()
        val arrivalArr = arrivalStr?.split("  ")?.toMutableList()

        departureArr?.get(2)?.trimStart('(')?.trimEnd(')')?.let { it1 -> departureArr.set(2, it1) }
        arrivalArr?.get(2)?.trimStart('(')?.trimEnd(')')?.let { it1 -> arrivalArr.set(2, it1) }
        Log.d(TAG, "$departureArr /  $arrivalArr")

        val departure = departureArr.let {
            StationModel(
                it!![1],
                "",
                it[0],
                it[2]
            )
        }
        val arrival = arrivalArr.let {
            StationModel(
                it!![1],
                "",
                it[0],
                it[2]
            )
        }
        Log.d(TAG, "obj : $departure / $arrival")

        val mark = MarkModel(departure=departure, arrival=arrival)
        markRepository.postMark(mark)


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

                stationModelList.add(StationModel(cursor.getString(1)?:"", cursor.getString(2)?:"", cursor.getString(3) ?: "", cursor.getString(4) ?: ""))
            }
            cursor.close()
            dbHelper.close()
        }
    }

    private fun findStationName(editText: AutoCompleteTextView?) {
        stationModelList.clear()

        val keyword = editText?.text.toString()
        if (keyword != "") {
            createSubwayStationList(keyword)

            val adapter = StationAdapter(requireContext(),stationModelList)
            editText?.setAdapter(adapter)
        }
    }
}

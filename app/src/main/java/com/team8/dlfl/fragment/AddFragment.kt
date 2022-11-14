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
import com.team8.dlfl.model.Station


private const val TAG="AddFragment"
class AddFragment : Fragment() {

    private var binding: FragmentAddBinding? = null
    private var stationList = ArrayList<Station>()
    private var mAuth: FirebaseAuth? = null

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


        editTextDeparture?.setOnKeyListener { view, keyCode, event ->
            if(event.action ==  KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_ENTER){
                findStationName(editTextDeparture)
            }
            false
        }
        editTextMiddle?.setOnKeyListener { view, keyCode, event ->
            if(event.action ==  KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_ENTER){
                findStationName(editTextMiddle)
            }
            false
        }
        editTextArrival?.setOnKeyListener { view, keyCode, event ->
            if(event.action ==  KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_ENTER){
                findStationName(editTextArrival)
            }
            false
        }


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

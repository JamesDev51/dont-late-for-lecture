package com.team8.dlfl.mark.viewmodel

import android.annotation.SuppressLint
import android.app.Application
import android.util.Log
import android.widget.AutoCompleteTextView
import androidx.lifecycle.AndroidViewModel
import com.team8.dlfl.db.DBHelper
import com.team8.dlfl.mark.adapter.StationAdapter
import com.team8.dlfl.mark.model.StationModel

private const val TAG = "SubwayViewModel"

class SubwayViewModel(application: Application) : AndroidViewModel(application) {

    @SuppressLint("StaticFieldLeak")
    private val context = getApplication<Application>().applicationContext
    private val stationModelList = ArrayList<StationModel>()

    lateinit var departure: StationModel
    lateinit var arrival: StationModel

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
    fun findStationName(editText: AutoCompleteTextView?,division:String) {
        stationModelList.clear()

        val keyword = editText?.text.toString()
        if (keyword != "") {
            createSubwayStationList(keyword)

            val adapter = StationAdapter(context,stationModelList)
            editText?.setOnItemClickListener { _, _, position, _ ->
                Log.d(TAG,"clicked departure: ${stationModelList[position]}")

                if(division == "departure"){
                    departure=stationModelList[position]
                }else{
                    arrival=stationModelList[position]
                }
            }
            editText?.setAdapter(adapter)
        }
    }
}
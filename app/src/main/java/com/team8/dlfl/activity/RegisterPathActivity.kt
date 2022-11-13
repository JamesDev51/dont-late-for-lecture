package com.team8.dlfl.activity

import android.os.Bundle
import android.util.Log
import android.widget.AutoCompleteTextView
import androidx.appcompat.app.AppCompatActivity
import com.team8.dlfl.StationNameRespDto
import com.team8.dlfl.adapter.StationAdapter
import com.team8.dlfl.databinding.ActivityRegisterPathBinding
import com.team8.dlfl.model.Station
import com.team8.dlfl.service.RetrofitService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RegisterPathActivity : AppCompatActivity(){

    private var binding: ActivityRegisterPathBinding?=null

    private var stationList = ArrayList<Station>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterPathBinding.inflate(layoutInflater)
        val editTextDeparture = binding?.editTextDeparture
        val editTextMiddle = binding?.editTextMiddle
        val editTextArrival = binding?.editTextArrival


        editTextDeparture?.setOnKeyListener { view, keyCode, keyEvent ->
            findStationName(editTextDeparture)
            false
        }
        editTextMiddle?.setOnKeyListener { view, keyCode, keyEvent ->
            findStationName(editTextMiddle)
            false
        }
        editTextArrival?.setOnKeyListener { view, keyCode, keyEvent ->
            findStationName(editTextArrival)
            false
        }

        setContentView(binding?.root)
    }

    private fun findStationName(editText: AutoCompleteTextView?) {
        println("[findStationName] api 합수 진입")
        val keyword = editText?.text.toString()

        println("[findStationName] keyword: $keyword")

        val retrofit = Retrofit.Builder().baseUrl("http://openapi.seoul.go.kr:8088/")
            .addConverterFactory(GsonConverterFactory.create()).build();
        val service = retrofit.create(RetrofitService::class.java);
        service.getStationNameList(keyword).enqueue(object: Callback<StationNameRespDto>{
            override fun onResponse(call: Call<StationNameRespDto>, response: Response<StationNameRespDto>) {
                val respDto = response.body();
                val stationListResp = respDto?.SearchInfoBySubwayNameService?.row

                stationList.clear()
                if (stationListResp != null) {
                    for (station in stationListResp) {
                        stationList.add(station)
                    }
                    val adapter = StationAdapter(this@RegisterPathActivity, stationList)
                    editText?.setAdapter(adapter)
                }
            }

            override fun onFailure(call: Call<StationNameRespDto>, t: Throwable) {
                Log.d("YMC", "onFailure 에러: " + t.message.toString());
            }
        })
    }
}
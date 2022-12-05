package com.team8.dlfl.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.team8.dlfl.databinding.ListMarksBinding
import com.team8.dlfl.databinding.ListStationStatusBinding
import com.team8.dlfl.dto.StationStatus
import com.team8.dlfl.model.MarkModel


class StationStatusListAdapter(val stationStatusList: LiveData<ArrayList<StationStatus>>)
    : RecyclerView.Adapter<StationStatusListAdapter.ViewHolder>(){


    class ViewHolder(val binding: ListStationStatusBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(stationStatus: StationStatus?) {
            stationStatus?.let {
                binding.txtRowNum.text = stationStatus.rowNum.toString()
                binding.txtUpdnLine.text=stationStatus.updnLine
                binding.txtMsg1.text=stationStatus.arvlMsg2
                binding.txtStatus.text= when(stationStatus.arvlCd){
                    "0"->"진입"
                    "1"->"도착"
                    "2"->"출발"
                    "3"->"전역 출발"
                    "4"->"전역진입"
                    "5"->"전역도착"
                    "99"->"운행중"
                    else->"상태 없음"
                }.toString()
                binding.txtTrainLineNm.text=stationStatus.trainLineNm
                }
        }
    }
    // 리스트 한칸 한칸이 viewhoder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ListStationStatusBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        viewHolder.bind(stationStatusList.value?.getOrNull(position))
    }

    override fun getItemCount() = stationStatusList.value?.size ?:0

}
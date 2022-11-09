package com.team8.dlfl

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.team8.dlfl.databinding.ListMarksBinding

class StationsAdapter (val stations: Array<Station>)
    : RecyclerView.Adapter<StationsAdapter.Holder>(){

        //리스트 한칸 한칸이 viewholder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ListMarksBinding.inflate(LayoutInflater.from(parent.context))//parent.context MarkActivity맞겠지
        // 마크 액티비티로부터 레이아웃인플레이터를 가져와서 그걸로 인플레이트 한다
        return Holder(binding)
    }
    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(stations[position])
    }

    // 이 view 안에 들어가는 아이템이 몇개냐
    override fun getItemCount() = stations.size

    class Holder(private val binding: ListMarksBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(station: Station){
            // binding.imageView.setImageResource(when( station.))  알림표시 invisible 넣기

            binding.staStart.text = station.startStation
            binding.staGoal.text = station.goalStation
            binding.staLayover.text = station.layoverStation

            // 토스트는 필요하면 넣기
        }
    }

    }
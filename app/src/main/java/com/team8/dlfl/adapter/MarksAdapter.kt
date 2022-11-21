package com.team8.dlfl.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.team8.dlfl.databinding.ListMarksBinding
import com.team8.dlfl.model.MarkModel

class MarksAdapter(private val marks: List<MarkModel>)
    : RecyclerView.Adapter<MarksAdapter.Holder>(){

    // 리스트 한칸 한칸이 viewhoder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ListMarksBinding.inflate(LayoutInflater.from(parent.context))

        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(marks[position])
    }

    override fun getItemCount() = marks.size

//    fun replaceList(newList: MutableList<Mark>) {   // 추가
//        marks = newList.toMutableList()
//        // 어댑터의 데이터가 변했다는 notify를 날림
//        notifyDataSetChanged()
//    }

    class Holder(private val binding: ListMarksBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(mark: MarkModel) {
            binding.txtArrival.text = mark.arrival.stationName
            binding.txtLnArrival.text = mark.arrival.lineNumber
            binding.txtDeparture.text = mark.departure.stationName
            binding.txtLnDeparture.text = mark.departure.lineNumber
        }

    }


}
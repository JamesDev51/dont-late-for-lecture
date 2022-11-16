package com.team8.dlfl.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.team8.dlfl.databinding.ListMarksBinding

class MarksAdapter(val marks: Array<Mark>)
    : RecyclerView.Adapter<MarksAdapter.Holder>(){
    //private var marks = mutableListOf<Mark>()//추가
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
        fun bind(mark: Mark) {
            binding.txtArrival.text = mark.arrival
            binding.txtLnArrival.text = mark.lineArrival
            binding.txtDeparture.text = mark.departure
            binding.txtLnDeparture.text = mark.lineDeparture
            binding.txtMiddle.text = mark.middle
            binding.txtLnMiddle.text = mark.lineMiddle


        }

    }


}
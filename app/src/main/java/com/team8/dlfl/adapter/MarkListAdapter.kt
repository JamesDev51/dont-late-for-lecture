package com.team8.dlfl.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.team8.dlfl.databinding.ListMarksBinding
import com.team8.dlfl.model.MarkModel

class MarkListAdapter(val marks: LiveData<ArrayList<MarkModel>>)
    : RecyclerView.Adapter<MarkListAdapter.ViewHolder>(){

    class ViewHolder(val binding: ListMarksBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(mark: MarkModel?) {
            mark?.let {
                binding.txtArrival.text = mark.arrival.stationName
                binding.txtLnArrival.text = mark.arrival.lineNumber
                binding.txtDeparture.text = mark.departure.stationName
                binding.txtLnDeparture.text = mark.departure.lineNumber
            }
        }
    }
    // 리스트 한칸 한칸이 viewhoder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ListMarksBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bind(marks.value?.getOrNull(position))
    }

    override fun getItemCount() = marks.value?.size?:0

}


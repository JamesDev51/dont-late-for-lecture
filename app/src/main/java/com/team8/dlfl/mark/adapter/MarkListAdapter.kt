package com.team8.dlfl.mark.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.team8.dlfl.databinding.ListMarksBinding
import com.team8.dlfl.mark.model.MarkModel

private const val TAG = "MarkListAdapter"

//https://jaeyeong951.medium.com/recyclerview%EC%9D%98-onclicklistener-%EA%B5%AC%ED%98%84-c1c4bf16e90f
class MarkListAdapter(private val listener: OnItemClickListener, private val marks: LiveData<ArrayList<MarkModel>>)
    : RecyclerView.Adapter<MarkListAdapter.ViewHolder>(){

    interface OnItemClickListener {
        fun onItemClick(selectedStationName:String)
    }



    inner class ViewHolder(val binding: ListMarksBinding) : RecyclerView.ViewHolder(binding.root){
        //val message = MyFirebaseMessagingService().onMessageReceived()

        fun bind(mark: MarkModel?) {
            mark?.let {
                binding.txtArrival.text = mark.arrival.stationName
                binding.txtLnArrival.text = mark.arrival.lineNumber
                binding.txtDeparture.text = mark.departure.stationName
                binding.txtUpdnLine.text = mark.departure.lineNumber
                if (binding.txtUpdnLine.text == "02호선" || binding.txtLnArrival.text == "02호선" ){
                    binding.imageView.visibility = View.VISIBLE}

                binding.chkDel.setOnClickListener(null)

                    binding.root.setOnClickListener {
                        listener.onItemClick(mark.departure.stationName)
                    }

                binding.chkDel.setOnCheckedChangeListener { _, isChecked ->
                        mark.deleteFlag=isChecked
                        Log.d(TAG,mark.toString())
                    }
                }
        }
    }
    // 리스트 한칸 한칸이 viewhoder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ListMarksBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        val mark = marks.value?.getOrNull(position)
        viewHolder.bind(mark)
    }

    override fun getItemCount() = marks.value?.size?:0

}


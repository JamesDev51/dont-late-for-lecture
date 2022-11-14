package com.team8.dlfl.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Filter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.team8.dlfl.databinding.StationListBinding
import com.team8.dlfl.model.Station

class StationAdapter(context: Context,private var stationList: List<Station>) : ArrayAdapter<Station>(context,0,stationList){

    override fun getCount(): Int = stationList.size

    override fun getItem(position: Int): Station = stationList[position]

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun publishResults(charSequence: CharSequence?, filterResults: FilterResults) {
                @Suppress("UNCHECKED_CAST")
                stationList = filterResults.values as List<Station>
                notifyDataSetChanged()
            }

            override fun performFiltering(charSequence: CharSequence?): FilterResults {

                val results = FilterResults()
                results.values=stationList
                results.count=stationList.size
                return results
            }
        }
    }



    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val binding: StationListBinding
        var row = convertView

        if (row == null) {
            val inflater =
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            binding = StationListBinding.inflate(inflater, parent, false)
            row = binding.root
        } else {
            binding = StationListBinding.bind(row)
        }

        binding.textLineNumber.text = stationList[position].lineNumber
        binding.textStationName.text =stationList[position].stationName
        binding.textStationNameEng.text=stationList[position].stationNameEng
        binding.textExCode.text=stationList[position].externalCode

        return row
    }


}
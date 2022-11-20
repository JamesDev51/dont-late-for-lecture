package com.team8.dlfl.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Filter
import com.team8.dlfl.databinding.StationListBinding
import com.team8.dlfl.model.StationModel

class StationAdapter(context: Context,private var stationModelList: List<StationModel>) : ArrayAdapter<StationModel>(context,0,stationModelList){

    override fun getCount(): Int = stationModelList.size

    override fun getItem(position: Int): StationModel = stationModelList[position]

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun publishResults(charSequence: CharSequence?, filterResults: FilterResults) {
                @Suppress("UNCHECKED_CAST")
                stationModelList = filterResults.values as List<StationModel>
                notifyDataSetChanged()
            }

            override fun performFiltering(charSequence: CharSequence?): FilterResults {

                val results = FilterResults()
                results.values=stationModelList
                results.count=stationModelList.size
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

        binding.textLineNumber.text = stationModelList[position].lineNumber
        binding.textStationName.text =stationModelList[position].stationName
        binding.textStationNameEng.text=stationModelList[position].stationNameEng
        binding.textExCode.text=stationModelList[position].externalCode

        return row
    }


}
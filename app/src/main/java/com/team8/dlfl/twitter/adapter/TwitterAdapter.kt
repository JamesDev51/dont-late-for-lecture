package com.team8.dlfl.twitter.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.team8.dlfl.R
import com.team8.dlfl.databinding.ItemNoticeBinding
import com.team8.dlfl.twitter.Notice

class TwitterAdapter(val notices: LiveData<ArrayList<Notice>>): RecyclerView.Adapter<TwitterAdapter.ViewHolder>() {

    class ViewHolder(val binding: ItemNoticeBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(notice: Notice?) {
            notice?.let {
                binding.iconPeople.setImageResource(when(notice.people) {
                    "true" -> R.drawable.solidarity
                    "false" -> R.drawable.corporation
                    else -> {
                        R.drawable.twitter
                    }
                })
                binding.txtTitle.text = it.title
                binding.txtName.text = it.name
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemNoticeBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(notices.value?.getOrNull(position))
    }

    override fun getItemCount(): Int = notices.value?.size?: 0
}
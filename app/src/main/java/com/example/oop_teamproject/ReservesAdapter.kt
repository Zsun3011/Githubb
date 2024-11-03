package com.example.oop_teamproject

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.oop_teamproject.databinding.ListRevsBinding

class ReservesAdapter(val reserves: Array<Reserved>) : RecyclerView.Adapter<ReservesAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ListRevsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(reserves[position])
    }

    override fun getItemCount() = reserves.size

    class Holder(private val binding: ListRevsBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(reserved: Reserved) {
            binding.txtNumber.text = reserved.number.toString()
            binding.txtType.text = reserved.type
            binding.txtName1.text = reserved.name
            binding.txtCount.text = reserved.count.toString()
            binding.txtPrice1.text = reserved.price.toString()
            binding.txtCancel.text = reserved.cancel
        }
    }
}
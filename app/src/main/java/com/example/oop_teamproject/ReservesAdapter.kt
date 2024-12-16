package com.example.oop_teamproject

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.oop_teamproject.databinding.ListRevsBinding

class ReservesAdapter(private var reserves: MutableList<Reserved>) :
    RecyclerView.Adapter<ReservesAdapter.Holder>() {

    var onCancelClicked: ((Reserved) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ListRevsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val reserved = reserves[position]
        holder.bind(reserved)
        holder.binding.btnCancel.setOnClickListener {
            onCancelClicked?.invoke(reserved)
        }
    }

    override fun getItemCount() = reserves.size

    fun updateList(newList: List<Reserved>) {
        reserves = newList.toMutableList()
        notifyDataSetChanged()
    }

    class Holder(val binding: ListRevsBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(reserved: Reserved) {
            binding.txtType.text = reserved.type
            binding.txtName1.text = reserved.name
            binding.txtCount.text = reserved.quantity.toString()
            binding.txtPrice1.text = reserved.price.toString()
        }
    }
}

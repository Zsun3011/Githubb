package com.example.oop_teamproject

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.oop_teamproject.databinding.ListBooksBinding

class BooksAdapter(val books: Array<Book>) : RecyclerView.Adapter<BooksAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ListBooksBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(books[position])
    }

    override fun getItemCount() = books.size

    class Holder(private val binding: ListBooksBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(book: Book) {
            binding.txtName.text = book.name
            binding.txtPrice.text = book.price.toString()
        }
    }
}
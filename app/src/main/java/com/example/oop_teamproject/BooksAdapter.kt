package com.example.oop_teamproject

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.oop_teamproject.databinding.ListBooksBinding
import com.example.oop_teamproject.databinding.DialogBookDescriptionBinding

class BooksAdapter(
    private val books: Array<Book>, // 원본 데이터
    private val onItemClicked: (Book) -> Unit
) : RecyclerView.Adapter<BooksAdapter.Holder>() {

    private var filteredBooks: Array<Book> = books // 필터링된 데이터 초기화

    // 필터링 메서드: 검색된 책만 filteredBooks에 반영
    fun updateBooks(searchQuery: String) {
        filteredBooks = if (searchQuery.isEmpty()) {
            books // 검색어가 없으면 원본 데이터 그대로
        } else {
            books.filter {
                it.name.contains(searchQuery, ignoreCase = true)
            }.toTypedArray()
        }
        notifyDataSetChanged() // 데이터 갱신
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ListBooksBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding, onItemClicked)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(filteredBooks[position])
    }

    override fun getItemCount() = filteredBooks.size

    class Holder(
        private val binding: ListBooksBinding,
        private val onItemClicked: (Book) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        // 책 데이터를 바인딩하고 팝업창을 띄우는 로직 추가
        fun bind(book: Book) {
            binding.txtName.text = book.name
            binding.txtPrice.text = book.price.toString()

            binding.root.setOnClickListener {
                showPopup(book, binding.root.context, onItemClicked)
            }
        }

        private fun showPopup(book: Book, context: Context, onItemClicked: (Book) -> Unit) {
            val dialogBinding = DialogBookDescriptionBinding.inflate(LayoutInflater.from(context))

            val dialog = AlertDialog.Builder(context)
                .setView(dialogBinding.root)
                .create()

            dialogBinding.txtPopupBookName.text = book.name
            dialogBinding.txtPopupBookPrice.text = "${book.price}원"
            dialogBinding.txtPopupBookDescription.text = "${book.name}입니다"

            dialogBinding.btnToPayment.setOnClickListener {
                dialog.dismiss()
                onItemClicked(book)
            }

            dialog.show()
        }
    }
}

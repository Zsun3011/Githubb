package com.example.oop_teamproject

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.oop_teamproject.databinding.ListBooksBinding
import com.example.oop_teamproject.databinding.DialogBookDescriptionBinding

class BooksAdapter(
    private val books: Array<Book>,
    private val onItemClicked: (Book) -> Unit
) : RecyclerView.Adapter<BooksAdapter.Holder>() {

    //뷰홀더 생성
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ListBooksBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding, onItemClicked)
    }

    //데이터 바인딩
    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(books[position])
    }

    override fun getItemCount() = books.size

    class Holder(
        private val binding: ListBooksBinding,
        private val onItemClicked: (Book) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        // 책 데이터를 바인딩하고 팝업창을 띄우는 로직 추가
        fun bind(book: Book) {
            // 책 이름과 가격을 리스트 항목에 설정
            binding.txtName.text = book.name
            binding.txtPrice.text = book.price.toString()

            // 항목 클릭 시 팝업창 표시
            binding.root.setOnClickListener {
                showPopup(book, binding.root.context, onItemClicked)
            }
        }

        // 팝업창 생성 및 동적 데이터 설정
        private fun showPopup(book: Book, context: Context, onItemClicked: (Book) -> Unit) {
            // 팝업창 레이아웃 바인딩
            val dialogBinding = DialogBookDescriptionBinding.inflate(LayoutInflater.from(context))

            // 다이얼로그 생성
            val dialog = AlertDialog.Builder(context)
                .setView(dialogBinding.root)
                .create()

            // 팝업창 텍스트뷰에 책 정보 설정
            dialogBinding.txtPopupBookName.text = book.name
            dialogBinding.txtPopupBookPrice.text = "${book.price}원"
            dialogBinding.txtPopupBookDescription.text = book.description

            // '결제 페이지로' 버튼 클릭 시 동작
            dialogBinding.btnToPayment.setOnClickListener {
                dialog.dismiss() // 다이얼로그 닫기
                onItemClicked(book) // 클릭된 책 객체 전달
            }

            // 팝업창 표시
            dialog.show()
        }
    }
}
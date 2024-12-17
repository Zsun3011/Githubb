package com.example.oop_teamproject

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.oop_teamproject.databinding.FragmentBooksearchBinding
import com.google.firebase.database.*

class BooksearchFragment : Fragment() {
    private var binding: FragmentBooksearchBinding? = null
    private lateinit var database: DatabaseReference  // 여기서 선언

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBooksearchBinding.inflate(inflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Firebase Database 초기화
        database = FirebaseDatabase.getInstance().reference.child("books")

        // 기존 'list' 버튼 클릭 시 화면 전환 기능 유지
        binding?.btnlist?.setOnClickListener {
            findNavController().navigate(R.id.action_booksearchFragment_to_booklistFragment)
        }

        // 검색 기능 구현binding?.btnSearch?.setOnClickListener {
        //            val searchText = binding?.etSearch?.text.toString()
        //            if (searchText.isNotEmpty()) {
        //                searchBook(searchText)
        //            } else {
        //                Toast.makeText(context, "검색어를 입력해주세요.", Toast.LENGTH_SHORT).show()
        //            }
        //        }
        val searchEditText = view.findViewById<EditText>(R.id.et_search)
        binding?.subjectSearchButton?.setOnClickListener {
            val searchText = searchEditText.text.toString().trim()

            if (searchText.isEmpty()) {
                Toast.makeText(context, "검색어를 입력하세요.", Toast.LENGTH_SHORT).show()
            } else {
                searchBook(searchText)
            }
        }
    }

    private fun showBookListPopup(books: List<Book>) {
        val dialogBuilder = AlertDialog.Builder(requireContext())

        // 레이아웃을 인플레이트하여 RecyclerView와 연결
        val view = LayoutInflater.from(context).inflate(R.layout.popup_book_results, null)
        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerViewPopup)

        // RecyclerView 설정
        val adapter = BookPopupAdapter(books)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter

        // 팝업 다이얼로그 설정
        dialogBuilder.setView(view)
        dialogBuilder.setPositiveButton("확인", null)
        dialogBuilder.create().show()
    }

    private fun searchBook(searchText: String) {
        database.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val booksList = mutableListOf<Book>()
                var resultFound = false

                for (bookSnapshot in snapshot.children) {
                    val bookName = bookSnapshot.child("name").value?.toString()
                    val description = bookSnapshot.child("description").value?.toString()
                    val price = bookSnapshot.child("price").value?.toString()

                    if (bookName != null && bookName.contains(searchText, ignoreCase = true)) {
                        resultFound = true
                        val book = Book(bookName,price?.toInt() ?: 0,description ?: "정보 없음")
                        booksList.add(book)
                    }
                }

                if (resultFound) {
                    showBookListPopup(booksList)
                } else {
                    Toast.makeText(context, "검색 결과가 없습니다.", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, "데이터를 불러오는 중 오류 발생", Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}

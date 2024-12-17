package com.example.oop_teamproject

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.oop_teamproject.databinding.FragmentBooklistBinding
import com.example.oop_teamproject.repository.BooksRepository
import com.example.oop_teamproject.viewmodel.BooksViewModel

class BooklistFragment : Fragment() {

    private var binding: FragmentBooklistBinding? = null
    private lateinit var viewModel: BooksViewModel // ViewModel 선언

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBooklistBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = BooksViewModel(BooksRepository()) //ViewModel 초기화

        binding?.apply {
            recBooklists.layoutManager = LinearLayoutManager(requireContext())

            val bookIDs = listOf("booksID01", "booksID02", "booksID03", "booksID05") //Firebase에서 해당 booksID에 해당하는 애들만 bookIDs로 가져오기

            viewModel.books.observe(viewLifecycleOwner) { books ->
                recBooklists.adapter = BooksAdapter(books.toTypedArray()) { selectedBook -> //어댑터  연결
                    val bundle = Bundle().apply { //번들로 name, price 데이터 전달
                        putString("bookName", selectedBook.name)
                        putInt("bookPrice", selectedBook.price)
                    }
                    //bookreservfragment로 이동하면서 번들에 있는 요소들 전달
                    findNavController().navigate(R.id.action_booklistFragment_to_bookreservFragment, bundle)
                }
            }
            viewModel.fetchBooks(bookIDs) //fetchBooks 메서드 호출해서 책 데이터 불러오기
        }
    }

    override fun onDestroyView() { //메모리 누수 방지용. 바인딩 객체 해제
        super.onDestroyView()
        binding = null
    }
}
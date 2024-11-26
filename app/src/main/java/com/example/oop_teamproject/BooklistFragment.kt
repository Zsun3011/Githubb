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
        // Inflate the layout for this fragment
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
                    findNavController().navigate(R.id.action_booklistFragment_to_bookreservFragment, bundle)
                    //bookreservfragment로 이동하면서 번들에 있는 요소들 전달
                }
            }

            viewModel.fetchBooks(bookIDs)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}
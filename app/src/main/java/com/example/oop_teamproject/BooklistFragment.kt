package com.example.oop_teamproject

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.oop_teamproject.databinding.FragmentBooklistBinding
import com.example.oop_teamproject.repository.BooksRepository
import com.example.oop_teamproject.viewmodel.BooksViewModel

class BooklistFragment : Fragment() {

    private var binding: FragmentBooklistBinding? = null

    private val viewModel: BooksViewModel by viewModels {
        BooksViewModel.Companion.Factory(BooksRepository())
    }

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

        binding?.apply {
            recBooklists.layoutManager = LinearLayoutManager(requireContext())

            val bookIDs = listOf("booksID01", "booksID02", "booksID03", "booksID05")

            viewModel.books.observe(viewLifecycleOwner) { books ->
                recBooklists.adapter = BooksAdapter(books.toTypedArray()) { selectedBook ->
                    val bundle = Bundle().apply {
                        putString("bookName", selectedBook.name)
                        putInt("bookPrice", selectedBook.price)
                    }
                    findNavController().navigate(R.id.action_booklistFragment_to_bookreservFragment, bundle)
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
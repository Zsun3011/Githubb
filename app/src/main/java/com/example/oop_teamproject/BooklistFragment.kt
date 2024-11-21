package com.example.oop_teamproject

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.oop_teamproject.databinding.FragmentBooklistBinding
import com.example.oop_teamproject.repository.BooksRepository
import com.example.oop_teamproject.viewmodel.BooksViewModel

class BooklistFragment : Fragment() {

    private var _binding: FragmentBooklistBinding? = null
    private val binding get() = _binding!!

    // ViewModel 생성 (BooksViewModel의 companion object Factory 사용)
    private val viewModel: BooksViewModel by viewModels {
        BooksViewModel.Companion.Factory(BooksRepository())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /*
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        */
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentBooklistBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Recycler view 설정
        binding.recBooklists.layoutManager = LinearLayoutManager(requireContext())

        val bookIDs = listOf("booksID01", "booksID02", "booksID03", "booksID05")

        // ViewModel의 LiveData 관찰
        viewModel.books.observe(viewLifecycleOwner) { books ->
            // RecyclerView 어댑터 설정
            binding.recBooklists.adapter = BooksAdapter(books.toTypedArray())
        }

        // 책 데이터를 ViewModel을 통해 가져오기
        viewModel.fetchBooks(bookIDs)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    /*
    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            BooklistFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
    */
}
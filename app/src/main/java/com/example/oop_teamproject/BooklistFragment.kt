package com.example.oop_teamproject

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.oop_teamproject.databinding.FragmentBooklistBinding

class BooklistFragment : Fragment() {

    val books = arrayOf(
        Book("이름", "가격"), //첫줄 목록 (이 목록때문에 Any 필요함)
        Book("Head First C", 34000),
        Book("Kotlin in depth", 41000),
        Book("꿈꾸는 인공지능", 17000)
    )

    private var _binding: FragmentBooklistBinding? = null
    private val binding get() = _binding!!

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
        // RecyclerView 설정
        binding.recBooklists.layoutManager = LinearLayoutManager(requireContext())
        binding.recBooklists.adapter = BooksAdapter(books)
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
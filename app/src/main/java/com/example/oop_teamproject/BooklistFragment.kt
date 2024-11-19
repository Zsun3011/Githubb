package com.example.oop_teamproject

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.oop_teamproject.databinding.FragmentBooklistBinding
import com.google.firebase.database.* //firebase database 임포트

class BooklistFragment : Fragment() {

    private var _binding: FragmentBooklistBinding? = null
    private val binding get() = _binding!!
    private lateinit var database: DatabaseReference
    private val selectedBooks = mutableListOf<Book>()

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
        database = FirebaseDatabase.getInstance().getReference("books")
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Recycler view 설정
        binding.recBooklists.layoutManager = LinearLayoutManager(requireContext())

        // Firebase에서 특정 책 ID에 해당하는 데이터만 가져오기
        val bookIDs = listOf("booksID01", "booksID02", "booksID03", "booksID05")

        database.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                selectedBooks.clear()
                for (id in bookIDs) {
                    val bookSnapshot = snapshot.child(id)
                    val name = bookSnapshot.child("name").getValue(String::class.java) ?: "Unknown"
                    val price = bookSnapshot.child("price").getValue(Int::class.java) ?: 0
                    selectedBooks.add(Book(name, price))
                }
                // RecyclerView 어댑터 설정
                binding.recBooklists.adapter = BooksAdapter(selectedBooks.toTypedArray())
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("Firebase", "Failed to load books", error.toException())
            }
        })
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
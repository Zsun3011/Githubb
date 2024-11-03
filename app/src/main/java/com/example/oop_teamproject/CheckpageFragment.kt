package com.example.oop_teamproject

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.oop_teamproject.databinding.FragmentCheckpageBinding

class CheckpageFragment : Fragment() {

    val reserves = arrayOf(
        Reserved("번호", "분류", "이름", "수량", "가격", "선택"),
        Reserved(1,"제본", "전공서적1", 1, 10000, "취소"),
        Reserved(2,"제본", "전공서적2", 1, 15000, "취소"),
        Reserved(3,"제본", "전공서적3", 1, 20000, "취소"),
    )

    private var _binding: FragmentCheckpageBinding? = null
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
        _binding = FragmentCheckpageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // RecyclerView 설정
        binding.recCheckpage.layoutManager = LinearLayoutManager(requireContext())
        binding.recCheckpage.adapter = ReservesAdapter(reserves)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    /*
    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CheckpageFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
    */
}
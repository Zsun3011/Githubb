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
        Reserved("번호", "분류", "이름", "수량", "가격", "선택"), //첫줄 목록
        Reserved(1,"제본", "Head First C", 1, 34000, "취소"),
        Reserved(2,"제본", "Kotlin in depth", 1, 41000, "취소"),
        Reserved(3,"파일", "Kau.pdf", 1, 5000, "취소"),
        Reserved(4,"제본", "꿈꾸는 인공지능", 1, 17000, "취소")
    )

    private var binding: FragmentCheckpageBinding? = null

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
        binding = FragmentCheckpageBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // RecyclerView 설정
        binding?.apply {
            recCheckpage.layoutManager = LinearLayoutManager(requireContext())
            recCheckpage.adapter = ReservesAdapter(reserves)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
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
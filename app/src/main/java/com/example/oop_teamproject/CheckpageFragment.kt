package com.example.oop_teamproject

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.oop_teamproject.databinding.FragmentCheckpageBinding
import com.example.oop_teamproject.viewmodel.UsersViewmodel

class CheckpageFragment : Fragment() {

    private var binding: FragmentCheckpageBinding? = null
    private lateinit var viewModel: UsersViewmodel
    private lateinit var adapter: ReservesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCheckpageBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // ViewModel 초기화
        viewModel = ViewModelProvider(this).get(UsersViewmodel::class.java)

        // RecyclerView 설정
        adapter = ReservesAdapter(mutableListOf())
        binding?.recCheckpage?.layoutManager = LinearLayoutManager(requireContext())
        binding?.recCheckpage?.adapter = adapter

        // 데이터 관찰 및 업데이트
        viewModel.items.observe(viewLifecycleOwner) { items ->
            adapter.updateList(items) // 받아온 리스트를 어댑터에 전달
        }

        // 취소 버튼 클릭 이벤트 설정
        adapter.onCancelClicked = { reserved ->
            val userID = "userID01" // 임시로 userID01 사용
            val itemType = if (reserved.type == "제본") "books" else "files" // 타입 확인
            viewModel.removeUserItem(userID, reserved.itemKey, itemType)
        }

        // Firebase 데이터 가져오기 (임시로 userID01 사용)
        viewModel.fetchUserItems("userID01")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}

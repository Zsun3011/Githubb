package com.example.oop_teamproject

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.oop_teamproject.databinding.FragmentFilereservBinding // ViewBinding 임포트
import com.example.oop_teamproject.model.FileItem
import com.example.oop_teamproject.viewmodel.UsersViewmodel

class FilereservFragment : Fragment() {

    private var _binding: FragmentFilereservBinding? = null
    private val binding get() = _binding!!

    private val typeOptions = arrayOf("양면", "단면")
    private val directionOptions = arrayOf("가로", "세로")
    private val colorOptions = arrayOf("흑백", "컬러")

    private lateinit var usersViewModel: UsersViewmodel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFilereservBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        usersViewModel = ViewModelProvider(this).get(UsersViewmodel::class.java)

        // Spinner 설정
        setupSpinners()

        // 데이터 저장 버튼 클릭 리스너
        binding.saveButton.setOnClickListener {
            saveData()
        }

    }

    private fun setupSpinners() {
        val typeAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, typeOptions)
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.types.adapter = typeAdapter

        val directionAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, directionOptions)
        directionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.direction.adapter = directionAdapter

        val colorAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, colorOptions)
        colorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.colors.adapter = colorAdapter
    }

    private fun saveData() {
        val page = binding.editTextText.text.toString()
        val quantity = binding.editTextNumber3.text.toString().toIntOrNull() ?: 0
        val type = binding.types.selectedItem.toString()
        val direc = binding.direction.selectedItem.toString()
        val color = binding.colors.selectedItem.toString()

        // FileItem 객체 생성
        val fileItem = FileItem(
            color = color,
            direction = direc,
            page = page,
            quantity = quantity,
            type = type
        )


        // ViewModel을 통해 데이터 저장
        usersViewModel.saveFileItem(fileItem)
        Toast.makeText(requireContext(), "데이터가 저장되었습니다.", Toast.LENGTH_SHORT).show()

        findNavController().navigate(R.id.action_filereservFragment_to_paymentSystemFragment)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // 메모리 누수 방지를 위해 binding 해제
    }
}

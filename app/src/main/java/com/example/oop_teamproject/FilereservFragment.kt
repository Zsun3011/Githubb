package com.example.oop_teamproject

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.oop_teamproject.repository.UsersRepository
import com.example.oop_teamproject.viewmodel.UsersViewmodel // 올바른 경로로 임포트

class FilereservFragment : Fragment() {

    private val typeOptions = arrayOf("양면", "단면")
    private val directionOptions = arrayOf("가로", "세로")
    private val colorOptions = arrayOf("흑백", "컬러")

    private lateinit var editTextText: EditText
    private lateinit var editTextNumber3: EditText
    private lateinit var usersViewModel: UsersViewmodel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_filereserv, container, false)

        editTextText = view.findViewById(R.id.editTextText)
        editTextNumber3 = view.findViewById(R.id.editTextNumber3)

        val typeSpinner: Spinner = view.findViewById(R.id.types)
        val directionSpinner: Spinner = view.findViewById(R.id.direction)
        val colorSpinner: Spinner = view.findViewById(R.id.colors)

        val typeAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, typeOptions)
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        typeSpinner.adapter = typeAdapter

        val directionAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, directionOptions)
        directionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        directionSpinner.adapter = directionAdapter

        val colorAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, colorOptions)
        colorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        colorSpinner.adapter = colorAdapter

        // UsersRepository 인스턴스 생성
        val repository = UsersRepository()
        // ViewModel 초기화 (Factory 없이)
        usersViewModel = ViewModelProvider(this).get(UsersViewmodel::class.java)

        // 데이터 저장 버튼 클릭 리스너
        view.findViewById<Button>(R.id.saveButton).setOnClickListener {
            val page = editTextText.text.toString()
            val quantity = editTextNumber3.text.toString().toIntOrNull() ?: 0
            val type = typeSpinner.selectedItem.toString()
            val direc = directionSpinner.selectedItem.toString()
            val color = colorSpinner.selectedItem.toString()

            val fileItem = mapOf(
                "color" to color,
                "direc" to direc,
                "page" to page,
                "quantity" to quantity,
                "type" to type
            )

            // ViewModel을 통해 데이터 저장
            usersViewModel.saveFileItem(fileItem)
            Toast.makeText(requireContext(), "데이터가 저장되었습니다.", Toast.LENGTH_SHORT).show()

            // PaymentSystemFragment로 화면 전환
            parentFragmentManager.beginTransaction()
                .replace(R.id.main_container, PaymentSystemFragment()) // fragment_container의 정확한 ID를 사용해야 함.
                .addToBackStack(null)
                .commit()
        }

        return view
    }
}

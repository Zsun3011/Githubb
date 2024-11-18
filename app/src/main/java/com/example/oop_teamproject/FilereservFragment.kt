package com.example.oop_teamproject

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner


class FilereservFragment : Fragment() {

    // 선택지 배열 생성
    private val typeOptions = arrayOf("양면", "단면")
    private val directionOptions = arrayOf("가로", "세로")
    private val colorOptions = arrayOf("흑백", "컬러")

    private lateinit var editTextNumber2: EditText
    private lateinit var editTextNumber3: EditText


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Fragment의 레이아웃을 인플레이트
        val view = inflater.inflate(R.layout.fragment_filereserv, container, false)

        //EditText 초기화
        editTextNumber2 = view.findViewById(R.id.editTextNumber2)
        editTextNumber3 = view.findViewById(R.id.editTextNumber3)

        // Spinner 초기화
        val typeSpinner: Spinner = view.findViewById(R.id.types)
        val directionSpinner: Spinner = view.findViewById(R.id.direction)
        val colorSpinner: Spinner = view.findViewById(R.id.colors)

        // ArrayAdapter 생성 및 설정
        val typeAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, typeOptions)
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        typeSpinner.adapter = typeAdapter

        val directionAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, directionOptions)
        directionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        directionSpinner.adapter = directionAdapter

        val colorAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, colorOptions)
        colorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        colorSpinner.adapter = colorAdapter

        return view
    }

}
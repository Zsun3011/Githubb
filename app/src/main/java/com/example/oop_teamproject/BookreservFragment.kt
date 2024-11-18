package com.example.oop_teamproject

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner


class BookreservFragment : Fragment() {

    private lateinit var editTextNumber: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Fragment의 레이아웃을 인플레이트
        val view = inflater.inflate(R.layout.fragment_bookreserv, container, false)

        //EditText 초기화
        editTextNumber = view.findViewById(R.id.editTextNumber)


        return view
    }
}
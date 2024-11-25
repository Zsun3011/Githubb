package com.example.oop_teamproject

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import androidx.navigation.fragment.findNavController
import com.example.oop_teamproject.databinding.FragmentBookreservBinding


class BookreservFragment : Fragment() {

    private lateinit var editTextNumber: EditText

    var binding : FragmentBookreservBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Fragment의 레이아웃을 인플레이트
        val view = inflater.inflate(R.layout.fragment_bookreserv, container, false)

        //EditText 초기화
        editTextNumber = view.findViewById(R.id.editTextNumber)

        binding = FragmentBookreservBinding.inflate(inflater)

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.gotopayment?.setOnClickListener{
            findNavController().navigate(R.id.action_bookreservFragment_to_paymentSystemFragment)
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}
package com.example.oop_teamproject

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.oop_teamproject.databinding.FragmentPaymentSystemBinding
import com.example.oop_teamproject.model.FileItem
import com.example.oop_teamproject.viewmodel.UsersViewmodel

class PaymentSystemFragment : Fragment() {
    private lateinit var usersViewModel: UsersViewmodel
    private lateinit var textView: TextView // 인쇄 정보를 표시할 TextView

    private var binding: FragmentPaymentSystemBinding? = null
    private lateinit var page: String
    private var quantity: Int = 0
    private lateinit var type: String
    private lateinit var direction: String
    private lateinit var color: String
    private lateinit var name: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPaymentSystemBinding.inflate(inflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // ViewModel 초기화
        usersViewModel = ViewModelProvider(requireActivity()).get(UsersViewmodel::class.java)
        textView = view.findViewById(R.id.dataset) // TextView ID에 맞게 수정

        // Bundle에서 인쇄 설정 정보 가져오기
        arguments?.let {
            page = it.getString("page") ?: ""
            quantity = it.getInt("quantity")
            type = it.getString("type") ?: ""
            direction = it.getString("direction") ?: ""
            color = it.getString("color") ?: ""
            name = it.getString("name") ?: ""

            // 문자열 리소스를 사용하여 데이터 표시
            textView.text = getString(R.string.print_info_format, page, quantity, type, direction, color, name)
        }

        // 버튼 클릭 리스너
        binding?.gotohome?.setOnClickListener {
            saveData() // Firebase에 데이터 저장
            findNavController().navigate(R.id.action_paymentSystemFragment_to_booksearchFragment)
        }
    }

    private fun saveData() {
        // FileItem 생성
        val fileItem = FileItem(
            page = page,
            quantity = quantity,
            type = type,
            direction = direction,
            color = color,
            name = name
        )

        // ViewModel을 통해 데이터 저장
        usersViewModel.saveFileItem(fileItem)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}

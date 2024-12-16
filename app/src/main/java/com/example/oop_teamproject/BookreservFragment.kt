package com.example.oop_teamproject

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.oop_teamproject.databinding.FragmentBookreservBinding

class BookreservFragment : Fragment() {

    private var binding: FragmentBookreservBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Binding 초기화
        binding = FragmentBookreservBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 번들에서 도서 정보 가져오기
        val bookName = arguments?.getString("bookName") ?: "Unknown"
        val bookPrice = arguments?.getInt("bookPrice") ?: 0

        // 화면에 도서 정보 표시
        binding?.apply {
            textView9.text = "도서명: $bookName"

            // 예약 버튼 클릭 시 이벤트 처리
            gotopayment.setOnClickListener {
                val quantityText = editTextNumber.text.toString()

                // 수량 유효성 검사
                if (quantityText.isEmpty() || quantityText.toIntOrNull() == null || quantityText.toInt() <= 0) {
                    Toast.makeText(requireContext(), "올바른 수량을 입력해주세요.", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                val quantity = quantityText.toInt()

                // 결제 화면으로 데이터 전달
                val bookReservationBundle = Bundle().apply {
                    putString("source", "bookReservation")
                    putString("bookName", bookName)
                    putInt("bookPrice", bookPrice)
                    putInt("quantity", quantity)
                }
                findNavController().navigate(R.id.action_bookreservFragment_to_paymentSystemFragment, bookReservationBundle)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}
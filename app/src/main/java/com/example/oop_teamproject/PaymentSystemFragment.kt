package com.example.oop_teamproject

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.oop_teamproject.databinding.FragmentBookreservBinding
import com.example.oop_teamproject.databinding.FragmentPaymentSystemBinding
import com.example.oop_teamproject.model.FileItem
import com.example.oop_teamproject.viewmodel.UsersViewmodel
class PaymentSystemFragment : Fragment() {
    private lateinit var usersViewModel: UsersViewmodel
    private lateinit var textView: TextView // 인쇄 정보를 표시할 TextView

    var binding : FragmentPaymentSystemBinding? = null

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

        // 마지막으로 저장된 파일 정보 가져오기
        val lastFile = usersViewModel.getLastSavedFileItem()
        if (lastFile != null) {
            displayFileInfo(lastFile) // 파일 정보 표시
        } else {
            textView.text = "저장된 인쇄 정보가 없습니다." // 데이터가 없을 경우 메시지 표시
        }

        binding?.gotohome?.setOnClickListener{
            findNavController().navigate(R.id.action_paymentSystemFragment_to_booksearchFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private fun displayFileInfo(fileItem: FileItem) {
        val info = """
            색상: ${fileItem.color}
            방향: ${fileItem.direction}
            페이지: ${fileItem.page}
            수량: ${fileItem.quantity}
            유형: ${fileItem.type}
        """.trimIndent()
        textView.text = info // TextView에 표시
    }
}

package com.example.oop_teamproject

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.oop_teamproject.databinding.FragmentFilereservBinding // ViewBinding 임포트
import com.example.oop_teamproject.model.FileItem
import com.example.oop_teamproject.viewmodel.UsersViewmodel
import com.example.oop_teamproject.viewmodel.FilesViewmodel

class FilereservFragment : Fragment() {

    private var _binding: FragmentFilereservBinding? = null
    private val binding get() = _binding!!

    private val typeOptions = arrayOf("양면", "단면")
    private val directionOptions = arrayOf("가로", "세로")
    private val colorOptions = arrayOf("흑백", "컬러")

    private lateinit var usersViewModel: UsersViewmodel
    private lateinit var filesViewModel: FilesViewmodel

    //뷰 생성
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFilereservBinding.inflate(inflater, container, false)
        return binding.root
    }

    //뷰 초기화
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //뷰모델 인스턴스 초기화
        usersViewModel = ViewModelProvider(this).get(UsersViewmodel::class.java)
        filesViewModel = ViewModelProvider(this).get(FilesViewmodel::class.java)

        // Spinner 설정
        setupSpinners()

        // 버튼 클릭 리스너 설정
        binding.saveButton.setOnClickListener { saveData() }
        binding.fileupload.setOnClickListener { uploadFile() }

    }

    // Spinners 초기화
    private fun setupSpinners() {
        setupSpinner(binding.types, typeOptions)
        setupSpinner(binding.direction, directionOptions)
        setupSpinner(binding.colors, colorOptions)
    }

    //스피너 설정
    private fun setupSpinner(spinner: Spinner, options: Array<String>) {
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, options)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
    }

    //인쇄 설정 데이터 저장
    private fun saveData() {

        // 입력 값 가져오기
        val page = binding.filepage.text.toString()
        val quantity = binding.filequantity.text.toString().toIntOrNull()

        // 입력 유효성 검사
        if (page.isEmpty() || quantity == null || quantity <= 0) {
            Toast.makeText(requireContext(), "인쇄설정을 선택해주세요", Toast.LENGTH_SHORT).show()
            return // 유효하지 않다면 함수 종료
        }

        // FileItem 생성
        val fileItem = FileItem(
            page = page,
            quantity = quantity,
            type = binding.types.selectedItem.toString(),
            direction = binding.direction.selectedItem.toString(),
            color = binding.colors.selectedItem.toString(),
            name = binding.filename.text.toString()
        )

        // ViewModel을 통해 데이터 저장
        usersViewModel.saveFileItem(fileItem)

        // 네비게이션
        findNavController().navigate(R.id.action_filereservFragment_to_paymentSystemFragment)
    }

    //파일 업로드
    private fun uploadFile() {
        filesViewModel.fetchFiles()
        filesViewModel.fileNames.observe(viewLifecycleOwner) { fileNames ->
            if (fileNames.isNotEmpty()) {
                showFileSelectionDialog(fileNames)
            }
        }
    }

    //파일 선택
    private fun showFileSelectionDialog(fileNames: List<String>) {
        AlertDialog.Builder(requireContext()).apply {
            setTitle("파일 선택")
            setItems(fileNames.toTypedArray()) { _, which ->
                // 선택한 파일 이름을 TextView에 설정
                binding.filename.setText(fileNames[which])
            }
            setNegativeButton("취소", null)
            show()
        }
    }
}

package com.example.oop_teamproject

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.OpenableColumns
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.ValueCallback
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.oop_teamproject.databinding.FragmentFilereservBinding // ViewBinding 임포트
import com.example.oop_teamproject.viewmodel.UsersViewmodel
import com.example.oop_teamproject.viewmodel.PaymentViewmodel
import java.io.File

class FilereservFragment : Fragment() {

    private var _binding: FragmentFilereservBinding? = null
    private val binding get() = _binding!!

    private val typeOptions = arrayOf("양면", "단면")
    private val directionOptions = arrayOf("가로", "세로")
    private val colorOptions = arrayOf("흑백", "컬러")

    private lateinit var usersViewModel: UsersViewmodel
    private lateinit var filesViewModel: PaymentViewmodel

    private lateinit var filePickerLauncher: ActivityResultLauncher<Intent>
    private var mFilePathCallback: ValueCallback<Array<Uri>?>? = null
    private var mUploadMessage: ValueCallback<Uri?>? = null
    private var mCameraPhotoPath: String? = null
    private var TempimageFile: File? = null

    // 뷰 생성
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFilereservBinding.inflate(inflater, container, false)
        return binding.root
    }

    // 뷰 초기화
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 뷰모델 인스턴스 초기화
        usersViewModel = ViewModelProvider(this).get(UsersViewmodel::class.java)
        filesViewModel = ViewModelProvider(this).get(PaymentViewmodel::class.java)

        // 파일 선택기 초기화
        filePickerLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                handleFileResult(result.data)
            } else {
                mFilePathCallback?.onReceiveValue(null)
                mUploadMessage?.onReceiveValue(null)
                TempimageFile?.delete()
            }
        }

        // Spinner 설정
        setupSpinners()

        // 버튼 클릭 리스너 설정
        binding.saveButton.setOnClickListener { saveData() }
        binding.fileupload.setOnClickListener { openFilePicker() }
    }

    // Spinners 초기화
    private fun setupSpinners() {
        setupSpinner(binding.types, typeOptions)
        setupSpinner(binding.direction, directionOptions)
        setupSpinner(binding.colors, colorOptions)
    }

    // 스피너 설정
    private fun setupSpinner(spinner: Spinner, options: Array<String>) {
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, options)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
    }

    private fun saveData() {
        // 입력 값 가져오기
        val page = binding.filepage.text.toString()
        val quantity = binding.filequantity.text.toString().toIntOrNull()
        val pagename = binding.filename.text.toString()

        // 입력 유효성 검사
        if (  page.isEmpty() || quantity == null || quantity <= 0) {
            Toast.makeText(requireContext(), "인쇄설정을 선택해주세요", Toast.LENGTH_SHORT).show()
            return // 유효하지 않다면 함수 종료
        }
        if(pagename.isEmpty()){
            Toast.makeText(requireContext(), "파일을 선택해주세요", Toast.LENGTH_SHORT).show()
            return // 유효하지 않다면 함수 종료
        }

        // Bundle에 인쇄 설정 정보 추가.
        val bookReservationBundle = Bundle().apply {
            putString("source", "fileReservation")
            putString("page", page)
            putInt("quantity", quantity)
            putString("type", binding.types.selectedItem.toString())
            putString("direction", binding.direction.selectedItem.toString())
            putString("color", binding.colors.selectedItem.toString())
            putString("name", binding.filename.text.toString())
        }

        // 네비게이션
        findNavController().navigate(
            R.id.action_filereservFragment_to_paymentSystemFragment,
            bookReservationBundle
        )
    }

    private fun handleFileResult(data: Intent?) {
        var results: Array<Uri>? = null

        try {
            if (data == null) {
                if (mCameraPhotoPath != null) {
                    results = arrayOf(Uri.parse(mCameraPhotoPath))
                }
            } else {
                val dataString = data.dataString
                if (dataString != null) {
                    // 단일 파일 선택 처리
                    results = arrayOf(Uri.parse(dataString))
                    // 파일 이름을 TextView에 설정
                    val fileName = getFileName(results[0])
                    binding.filename.text = fileName
                }
            }
        } catch (e: Exception) {
            Log.e("FilereservFragment", "File select error", e)
        }

        // 결과가 null이 아니면 콜백에 Uri 배열 전달
        if (mFilePathCallback != null) {
            mFilePathCallback?.onReceiveValue(results)
            mFilePathCallback = null
        } else if (mUploadMessage != null) {
            mUploadMessage?.onReceiveValue(results?.get(0))
            mUploadMessage = null
        }
    }

    // 파일 이름을 가져오는 메서드
    private fun getFileName(uri: Uri): String {
        var fileName = ""
        val cursor = requireContext().contentResolver.query(uri, null, null, null, null)
        cursor?.use {
            val nameIndex = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
            if (it.moveToFirst()) {
                fileName = it.getString(nameIndex)
            }
        }
        return fileName
    }

    // 파일 선택을 시작하는 메서드
    private fun openFilePicker() {
        val intent = Intent(Intent.ACTION_GET_CONTENT).apply {
            type = "*/*" // 모든 파일 형식 선택
            addCategory(Intent.CATEGORY_OPENABLE)
        }
        filePickerLauncher.launch(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // 메모리 누수 방지를 위해 바인딩 해제
    }
}

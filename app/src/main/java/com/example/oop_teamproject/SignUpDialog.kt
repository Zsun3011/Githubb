// SignUpDialog.kt
package com.example.oop_teamproject

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.oop_teamproject.databinding.FragmentSignUpDialogBinding
import com.example.oop_teamproject.model.User
import com.example.oop_teamproject.viewmodel.UsersViewmodel

class SignUpDialog : DialogFragment() {
    private var _binding: FragmentSignUpDialogBinding? = null
    private val binding get() = _binding!!
    private lateinit var usersViewModel: UsersViewmodel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignUpDialogBinding.inflate(inflater, container, false)
        usersViewModel = ViewModelProvider(this).get(UsersViewmodel::class.java)

        binding.buttonRegister.setOnClickListener {
            val uid = usersViewModel.generateUid()
            val username = binding.editTextUsername.text.toString()
            val password = binding.editTextPassword.text.toString()
            val name = binding.editTextName.text.toString()
            val major = binding.editTextMajor.text.toString()
            val studentNumber = binding.editTextStudentNumber.text.toString()
            val phoneNumber = binding.editTextPhoneNumber.text.toString()
            val email = binding.editTextEmail.text.toString()

            if (username.isNotEmpty() && password.isNotEmpty()) {
                val user = User(uid, username, password, name, major, studentNumber, phoneNumber, email)
                usersViewModel.signUp(user)
            } else {
                Toast.makeText(context, "모든 항목을 입력해주세요.", Toast.LENGTH_SHORT).show()
            }
        }

        usersViewModel.signUpResult.observe(viewLifecycleOwner) { success ->
            if (success) {
                Toast.makeText(context, "회원가입 성공", Toast.LENGTH_SHORT).show()
                dismiss()  // 다이얼로그 닫기
            } else {
                Toast.makeText(context, "회원가입 실패", Toast.LENGTH_SHORT).show()
            }
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

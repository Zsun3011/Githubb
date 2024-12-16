package com.example.oop_teamproject

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.oop_teamproject.databinding.FragmentLoginBinding
import com.example.oop_teamproject.viewmodel.UsersViewmodel

class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!


    private lateinit var usersViewModel: UsersViewmodel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        usersViewModel = ViewModelProvider(this).get(UsersViewmodel::class.java)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 로그인 버튼 클릭 이벤트
        binding.buttonLogin.setOnClickListener {
            val username = binding.inputId.text.toString()
            val password = binding.inputPassword.text.toString()

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(requireContext(), "아이디와 비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show()
            } else {
                usersViewModel.login(username, password)
            }
        }

        // 회원가입 버튼 클릭 이벤트
        binding.buttonSignUp.setOnClickListener {
            val signUpDialog = SignUpDialog()
            signUpDialog.show(requireActivity().supportFragmentManager, "SignUpDialog")
        }

        // 로그인 결과 관찰
        usersViewModel.loginResult.observe(viewLifecycleOwner) { success ->
            if (success) {
                Toast.makeText(requireContext(), "로그인 성공", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_loginFragment_to_booksearchFragment)
            } else {
                Toast.makeText(requireContext(), "로그인 실패", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // 메모리 누수 방지
    }
}
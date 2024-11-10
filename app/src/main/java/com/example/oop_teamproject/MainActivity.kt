package com.example.oop_teamproject

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import android.view.View //추가 import, View.Gone으로 main의 버튼 사라지게 만듬.
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.view.inputmethod.EditorInfo //추가. EditorInfo에서 오류 발생 막으려면 필요.
import com.example.oop_teamproject.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //Password를 입력하고 엔터키를 누르면 BookSearchFragment로 이동하는 코드세트!
        binding.inputPassword.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                binding.testButton.visibility = View.GONE
                binding.testButton2.visibility = View.GONE
                binding.kauText.visibility = View.GONE
                binding.reservationText.visibility = View.GONE
                binding.inputId.visibility = View.GONE
                binding.inputPassword.visibility = View.GONE
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, BookSearchFragment())
                    .addToBackStack(null)
                    .commit()
                true
            } else {
                false
            }
        }

        //이 아래의 코드는 테스트용 gogo! 버튼 세트를 위한 코드임.
        //안녕~~~~~~~~~

        binding .testButton.setOnClickListener { //View.GONE 기능은 버튼 눌러 화면 전환 했을 때, 이전 화면 지우기.
            binding.testButton.visibility = View.GONE
            binding.testButton2.visibility = View.GONE
            binding.kauText.visibility = View.GONE
            binding.reservationText.visibility = View.GONE
            binding.inputId.visibility = View.GONE
            binding.inputPassword.visibility = View.GONE
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, BooklistFragment())
                .addToBackStack(null) // 뒤로 가기 지원?
                .commit()
        }

        binding.testButton2.setOnClickListener {
            binding.testButton.visibility = View.GONE
            binding.testButton2.visibility = View.GONE
            binding.kauText.visibility = View.GONE
            binding.reservationText.visibility = View.GONE
            binding.inputId.visibility = View.GONE
            binding.inputPassword.visibility = View.GONE
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, CheckpageFragment())
                .addToBackStack(null) // 뒤로 가기 지원?
                .commit()
        }
    }
}
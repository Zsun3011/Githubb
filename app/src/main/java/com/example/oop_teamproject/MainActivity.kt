package com.example.oop_teamproject

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import android.view.View //추가 import, View.Gone을 활용한 가시성 제어에 활용
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.view.inputmethod.EditorInfo //추가. EditorInfo에서 오류 발생 막으려면 필요.
import androidx.fragment.app.replace
import com.example.oop_teamproject.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        hideBottomNavigation() // 초기에는 네비게이션 바를 숨기고 로그인 화면만 표시
        setBottomNavigationView()

        // 툴바 설정
        setSupportActionBar(binding.toolbar)
        // 기본 제목 비활성화
        supportActionBar?.setDisplayShowTitleEnabled(false)
        // 툴바 제목 설정
        binding.toolbarTitle.text = ""

        /*
        아래 코드는 일단 보류
        if (savedInstanceState == null) {
            binding.bottomNavigationView.selectedItemId = R.id.BooksearchFragment
        }
        */

        // 패스워드 입력 시 엔터를 누르면 BooksearchFragment로 이동 및 네비게이션 바 보이기
        binding.inputPassword.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                // 로그인 성공 시 BooksearchFragment로 이동하고 네비게이션 바 표시
                supportFragmentManager.beginTransaction()
                    .replace(R.id.main_container, BooksearchFragment()).commit()
                hideMainElements()
                showBottomNavigation()
                binding.toolbarTitle.text = "검색"
                true
            } else {
                false
            }
        }
    }

    private fun setBottomNavigationView() {
        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.BooksearchFragment -> {
                    hideMainElements()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_container, BooksearchFragment()).commit()
                    binding.toolbarTitle.text = "검색" //툴바 제목 업데이트
                    true
                }

                R.id.BookreservFragmnet -> {
                    hideMainElements()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_container, BookreservFragment()).commit()
                    binding.toolbarTitle.text = "도서 제본 예약"
                    true
                }

                R.id.FilereservFragment -> {
                    hideMainElements()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_container, FilereservFragment()).commit()
                    binding.toolbarTitle.text = "파일 복사 예약"
                    true
                }

                R.id.CheckpageFragment -> {
                    hideMainElements()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_container, CheckpageFragment()).commit()
                    binding.toolbarTitle.text = "조회 페이지"
                    true
                }

                else -> false
            }
        }
    }

    // 메인 화면 요소 가시성 제어
    private fun showMainElements() { //필요할까? 필요없다 생각되면 이 부분 지워도 될듯.
        binding.titleLayout.visibility = View.VISIBLE
        binding.inputLayout.visibility = View.VISIBLE
    }

    private fun hideMainElements() { //프래그먼트 이동 시 메인화면의 구성요소들이 사라져야 함.
        binding.titleLayout.visibility = View.GONE
        binding.inputLayout.visibility = View.GONE
    }

    // 네비게이션 바 가시성 제어
    private fun showBottomNavigation() {
        binding.bottomNavigationView.visibility = View.VISIBLE
    }

    private fun hideBottomNavigation() {
        binding.bottomNavigationView.visibility = View.GONE
    }
}

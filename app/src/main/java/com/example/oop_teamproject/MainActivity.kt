package com.example.oop_teamproject

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import android.view.View //추가 import, View.Gone으로 main의 버튼 사라지게 만듬.
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

        setBottomNavigationView()

        if (savedInstanceState == null) {
            binding.bottomNavigationView.selectedItemId = R.id.BooksearchFragment
        }
    }

    fun setBottomNavigationView() {
        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.BooksearchFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_container, BooksearchFragment()).commit()
                    true
                }

                R.id.BookreservFragmnet -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_container, BookreservFragment()).commit()
                    true
                }

                R.id.FilereservFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_container, FilereservFragment()).commit()
                    true
                }

                R.id.CheckpageFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_container, CheckpageFragment()).commit()
                    true
                }

                else -> false
            }
        }
    }
}
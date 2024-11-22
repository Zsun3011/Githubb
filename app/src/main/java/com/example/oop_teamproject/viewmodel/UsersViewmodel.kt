package com.example.oop_teamproject.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.oop_teamproject.repository.UsersRepository
import kotlinx.coroutines.launch

class UsersViewmodel : ViewModel() {

    private val repository = UsersRepository() // Repository 인스턴스 생성

    private val _files = MutableLiveData<List<Map<String, Any>>>()
    val files: LiveData<List<Map<String, Any>>> get() = _files

    // 파일 정보를 Firebase에 저장
    fun saveFileItem(fileItem: Map<String, Any>) {
        viewModelScope.launch {
            try {
                repository.saveFileItem(fileItem)
            } catch (e: Exception) {
                // 에러 처리
            }
        }
    }
}

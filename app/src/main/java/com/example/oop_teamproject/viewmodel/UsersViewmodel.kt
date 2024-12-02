package com.example.oop_teamproject.viewmodel

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.oop_teamproject.repository.UsersRepository
import com.example.oop_teamproject.model.FileItem // FileItem 데이터 클래스 임포트
import com.example.oop_teamproject.model.User
import kotlinx.coroutines.launch
import java.util.UUID

class UsersViewmodel : ViewModel() {

    private val repository = UsersRepository() // Repository 인스턴스 생성

    private val _files = MutableLiveData<List<FileItem>>() // FileItem 리스트를 LiveData로
    val files: LiveData<List<FileItem>> get() = _files

    private val _signUpResult = MutableLiveData<Boolean>()
    val signUpResult: LiveData<Boolean> get() = _signUpResult

    private val _loginResult = MutableLiveData<Boolean>()
    val loginResult: LiveData<Boolean> get() = _loginResult

    // 회원가입
    fun signUp(user: User) {
        viewModelScope.launch {
            val result = repository.registerUser(user)
            _signUpResult.postValue(result)
        }
    }

    fun login(username: String, password: String) {
        viewModelScope.launch {
            val result = repository.loginUser(username, password)
            _loginResult.postValue(result)
        }
    }

    // UID 생성 함수
    fun generateUid(): String {
        return UUID.randomUUID().toString()
    }
    // 파일 정보를 Firebase에 저장
    fun saveFileItem(fileItem: FileItem) {
        viewModelScope.launch {
            try {
                repository.saveFileItem(fileItem.toMap()) // 저장하기 전에 Map으로 변환
                updateFileList(fileItem) // 파일 리스트 업데이트
            } catch (e: Exception) {
                // 에러 처리
            }
        }
    }

    private fun updateFileList(fileItem: FileItem) {
        // 현재 파일 리스트를 가져와서 새로운 아이템 추가
        val currentList = _files.value ?: emptyList()
        _files.value = currentList + fileItem
    }

    // FileItem을 Map으로 변환하는 확장 함수
    private fun FileItem.toMap(): Map<String, Any> = mapOf(
            "color" to color,
            "direction" to direction,
            "page" to page,
            "quantity" to quantity,
            "type" to type,
            "name" to name
    )

}

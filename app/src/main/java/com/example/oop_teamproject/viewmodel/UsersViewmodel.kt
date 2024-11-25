package com.example.oop_teamproject.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.oop_teamproject.repository.UsersRepository
import com.example.oop_teamproject.model.FileItem // FileItem 데이터 클래스 임포트
import kotlinx.coroutines.launch

class UsersViewmodel : ViewModel() {

    private val repository = UsersRepository() // Repository 인스턴스 생성

    private val _files = MutableLiveData<List<FileItem>>() // FileItem 리스트를 LiveData로
    val files: LiveData<List<FileItem>> get() = _files

    private var lastSavedFileItem: FileItem? = null // 마지막으로 저장된 FileItem

    // 파일 정보를 Firebase에 저장
    fun saveFileItem(fileItem: FileItem) {
        viewModelScope.launch {
            try {
                repository.saveFileItem(fileItem.toMap()) // 저장하기 전에 Map으로 변환
                lastSavedFileItem = fileItem // 마지막으로 저장된 파일 아이템을 업데이트
                updateFileList(fileItem) // 파일 리스트 업데이트
            } catch (e: Exception) {
                // 에러 처리
            }
        }
    }
    // 마지막으로 저장된 FileItem을 반환하는 메서드
    fun getLastSavedFileItem(): FileItem? {
        return lastSavedFileItem
    }

    private fun updateFileList(fileItem: FileItem) {
        // 현재 파일 리스트를 가져와서 새로운 아이템 추가
        val currentList = _files.value ?: emptyList()
        _files.value = currentList + fileItem
    }

    // FileItem을 Map으로 변환하는 확장 함수
    private fun FileItem.toMap(): Map<String, Any> {
        return mapOf(
            "color" to color,
            "direction" to direction,
            "page" to page,
            "quantity" to quantity,
            "type" to type
        )
    }
}

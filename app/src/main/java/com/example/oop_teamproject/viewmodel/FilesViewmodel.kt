package com.example.oop_teamproject.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.oop_teamproject.repository.FilesRepository

class FilesViewmodel : ViewModel() { // 대문자 V
    private val repository = FilesRepository()
    private val _fileNames = MutableLiveData<List<String>>()
    val fileNames: LiveData<List<String>> get() = _fileNames

    // 파일 목록을 가져오는 메서드
    fun fetchFiles() {
        repository.getFiles { files ->
            _fileNames.postValue(files)
        }
    }
}

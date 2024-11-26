package com.example.oop_teamproject.repository

import com.google.firebase.Firebase
import com.google.firebase.database.database

class FilesRepository {
    // 파이어 베이스 참조
    private val ref = Firebase.database.getReference("files")

    // 파일 목록을 가져오는 메서드
    fun getFiles(callback: (List<String>) -> Unit) {
        ref.get().addOnSuccessListener { snapshot ->
            val fileNames = snapshot.children.map { it.child("name").getValue(String::class.java) ?: "" }
            callback(fileNames)
        }.addOnFailureListener {
            callback(emptyList()) // 실패 시 빈 리스트 반환
        }
    }
}
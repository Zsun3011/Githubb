package com.example.oop_teamproject.repository

import com.example.oop_teamproject.model.User
import com.google.firebase.database.*
import kotlinx.coroutines.tasks.await

class UsersRepository {
    private val db = FirebaseDatabase.getInstance().getReference("users")
    private val ref = FirebaseDatabase.getInstance().getReference("users/userID01/items/files")

    // 사용자 파일 정보를 Firebase에 저장
    suspend fun saveFileItem(fileItem: Map<String, Any>) {
        // 현재 저장된 항목 개수 가져오기
        val snapshot = ref.get().await()
        val count = snapshot.childrenCount

        // 새로운 키 생성 (itemsID01, itemsID02, ...)
        val newKey = "itemsID${String.format("%02d", count + 1)}"

        // 새로운 항목 저장
        ref.child(newKey).setValue(fileItem).await()
    }

    // 모든 사용자 파일 정보를 가져옴
    suspend fun getFiles(): List<Map<String, Any>> {
        val snapshot = ref.get().await()
        val files = mutableListOf<Map<String, Any>>()

        for (child in snapshot.children) {
            val item = child.value as Map<String, Any>
            files.add(item)
        }
        return files
    }

    // 회원가입 함수
    suspend fun registerUser(user: User): Boolean {
        return try {
            db.child(user.username).setValue(user).await()
            true
        } catch (e: Exception) {
            false
        }
    }

    // 로그인 함수
    suspend fun loginUser(username: String, password: String): Boolean {
        return try {
            val snapshot = db.child(username).get().await()
            if (snapshot.exists()) {
                val storedPassword = snapshot.child("password").getValue(String::class.java)
                return storedPassword == password // 로그인 성공
            }
            false  // 로그인 실패
        } catch (e: Exception) {
            false
        }
    }
}

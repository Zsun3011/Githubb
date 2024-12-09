package com.example.oop_teamproject.repository

import com.google.firebase.database.*
import kotlinx.coroutines.tasks.await

class UsersRepository {

    private val ref = FirebaseDatabase.getInstance().getReference("users/userID01/items")

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
}

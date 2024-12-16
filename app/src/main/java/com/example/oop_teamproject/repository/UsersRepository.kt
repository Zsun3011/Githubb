package com.example.oop_teamproject.repository

import com.google.firebase.database.*
import kotlinx.coroutines.tasks.await
import com.example.oop_teamproject.Reserved

class UsersRepository {

    private val ref = FirebaseDatabase.getInstance().getReference("users/userID01/items/files")
    private val database = FirebaseDatabase.getInstance()

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

    suspend fun fetchUserItems(userID: String): List<Reserved> {
        val result = mutableListOf<Reserved>()
        val userItemsRef = FirebaseDatabase.getInstance().getReference("users/$userID/items")

        val snapshot = userItemsRef.get().await()
        snapshot.children.forEach { type ->
            val typeName = when (type.key) {
                "books" -> "제본"
                "files" -> "파일"
                else -> "기타"
            }
            type.children.forEach { item ->
                val data = item.value as? Map<*, *>
                if (data != null) {
                    result.add(
                        Reserved(
                            itemKey = item.key ?: "",
                            type = typeName,
                            name = data["name"] as? String ?: "이름 없음",
                            quantity = (data["quantity"] as? Long)?.toInt() ?: 0,
                            price = (data["price"] as? Long)?.toInt() ?: 0
                        )
                    )
                }
            }
        }
        return result
    }

    suspend fun deleteUserItem(userID: String, itemKey: String, itemType: String) {
        val itemPath = "users/$userID/items/$itemType/$itemKey"
        database.getReference(itemPath).removeValue().await()
    }
}

package com.example.oop_teamproject.repository

import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
class PaymentRepository {
    private val databaseReference = FirebaseDatabase.getInstance().getReference("payment")

    // payment 노드의 키 가져오기
    fun fetchPaymentKeys(onSuccess: (List<String>) -> Unit, onFailure: (DatabaseError) -> Unit) {
        databaseReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val keys = mutableListOf<String>()
                for (childSnapshot in snapshot.children) {
                    keys.add(childSnapshot.key ?: "") // 자식 노드의 키를 가져옴
                }
                onSuccess(keys)
            }

            override fun onCancelled(error: DatabaseError) {
                onFailure(error)
            }
        })
    }

    // 선택된 키에 대한 카드 값 가져오기
    fun fetchCardValue(selectedKey: String, onSuccess: (Long) -> Unit, onFailure: (DatabaseError) -> Unit) {
        databaseReference.child(selectedKey).child("cardValue").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val value = snapshot.getValue(Long::class.java) ?: 0L // 카드 값 가져오기
                onSuccess(value)
            }

            override fun onCancelled(error: DatabaseError) {
                onFailure(error)
            }
        })
    }


    // 카드 값 업데이트
    fun updateCardValue(selectedKey: String, newCardValue: Long, onSuccess: () -> Unit, onFailure: (DatabaseError) -> Unit) {
        databaseReference.child(selectedKey).child("cardValue").setValue(newCardValue)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { error -> onFailure(DatabaseError.fromException(error)) }
    }
}

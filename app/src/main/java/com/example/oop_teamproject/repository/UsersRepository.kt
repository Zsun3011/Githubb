package com.example.oop_teamproject.repository

import com.google.firebase.Firebase
import com.google.firebase.database.database

class UsersRepository {
    // 파이어 베이스 참조
    private val ref = Firebase.database.getReference("users")
}
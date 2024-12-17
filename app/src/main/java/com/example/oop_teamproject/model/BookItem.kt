package com.example.oop_teamproject.model

data class BookItem(
    val name: String?,
    val price: Int,
    val quantity: Int,
    val date: String,         // 입력받은 날짜
    val time: String         // 입력받은 시간
)

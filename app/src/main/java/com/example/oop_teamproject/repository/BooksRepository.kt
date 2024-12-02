package com.example.oop_teamproject.repository

import com.example.oop_teamproject.Book
import com.google.firebase.database.*
import kotlinx.coroutines.tasks.await

class BooksRepository {

    private val ref = FirebaseDatabase.getInstance().getReference("books") //firebase에서 books 데이터 가져오기

    suspend fun getBooksByIds(bookIDs: List<String>): List<Book> {
        val snapshot = ref.get().await()
        val books = mutableListOf<Book>()

        for (id in bookIDs) { //프래그먼트의 bookIDs 참조
            val bookSnapshot = snapshot.child(id) //해당 책 ID에 해당하는 데이터 가져오기
            val name = bookSnapshot.child("name").getValue(String::class.java) ?: "Unknown"
            val price = bookSnapshot.child("price").getValue(Int::class.java) ?: 0
            val description = bookSnapshot.child("description").getValue(String::class.java) ?: "설명이 없습니다."
            books.add(Book(name, price, description)) //가져온 name과 price로 Book 객체 만들어 리스트 추가
        }
        return books
    }
}
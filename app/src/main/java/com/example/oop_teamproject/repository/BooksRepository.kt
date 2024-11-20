package com.example.oop_teamproject.repository

import com.example.oop_teamproject.Book
import com.google.firebase.database.*
import kotlinx.coroutines.tasks.await

class BooksRepository {

    private val ref = FirebaseDatabase.getInstance().getReference("books")

    suspend fun getBooksByIds(bookIDs: List<String>): List<Book> {
        val snapshot = ref.get().await()
        val books = mutableListOf<Book>()

        for (id in bookIDs) {
            val bookSnapshot = snapshot.child(id)
            val name = bookSnapshot.child("name").getValue(String::class.java) ?: "Unknown"
            val price = bookSnapshot.child("price").getValue(Int::class.java) ?: 0
            books.add(Book(name, price))
        }
        return books
    }
}
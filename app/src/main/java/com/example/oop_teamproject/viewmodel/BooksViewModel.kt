package com.example.oop_teamproject.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.oop_teamproject.Book
import com.example.oop_teamproject.repository.BooksRepository
import kotlinx.coroutines.launch

class BooksViewModel(private val repository: BooksRepository) : ViewModel() {

    private val _books = MutableLiveData<List<Book>>()
    val books: LiveData<List<Book>> get() = _books

    fun fetchBooks(bookIDs: List<String>) {
        viewModelScope.launch { //viewModelScope -> 코루틴 시작
            try {
                val bookList = repository.getBooksByIds(bookIDs) //repository로 책 데이터 가져오기
                _books.value = bookList
            } catch (e: Exception) {
                _books.value = emptyList()
            }
        }
    }

    companion object {
        class Factory(private val repository: BooksRepository) : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                if (modelClass.isAssignableFrom(BooksViewModel::class.java)) {
                    @Suppress("UNCHECKED_CAST")
                    return BooksViewModel(repository) as T
                }
                throw IllegalArgumentException("Unknown ViewModel class")
            }
        }
    }
}
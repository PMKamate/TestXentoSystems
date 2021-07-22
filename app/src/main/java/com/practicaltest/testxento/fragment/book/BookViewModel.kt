package com.practicaltest.testxento.fragment.book

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.practicaltest.testxento.data.repository.BookRepository

class BookViewModel @ViewModelInject constructor(
    private val repository: BookRepository
) : ViewModel() {
    val bookDataSource = repository.getBooks("harry potter")
}

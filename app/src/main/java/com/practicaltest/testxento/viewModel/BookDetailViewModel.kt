package com.practicaltest.testxento.viewModel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.practicaltest.testxento.data.entities.Book
import com.practicaltest.testxento.data.repository.BookRepository
import com.practicaltest.testxento.utils.Resource

class BookDetailViewModel @ViewModelInject constructor(
    private val repository: BookRepository
) : ViewModel() {

    private val _id = MutableLiveData<String>()

    private val _book = _id.switchMap { id ->
        repository.getBookDetails(id)
    }
    val book: LiveData<Resource<Book>> = _book


    fun start(id: String) {
        _id.value = id
    }

}

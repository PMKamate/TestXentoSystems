package com.practicaltest.testxento.data.remote

import javax.inject.Inject

class BookRemoteDataSource @Inject constructor(
    private val bookService: BookService
) : BaseDataSource() {
    suspend fun getAllBook(q: String) = getResult { bookService.getAllBook(q) }
}
package com.practicaltest.testxento.data.remote

import com.practicaltest.testxento.data.entities.Book
import com.practicaltest.testxento.model.book.BookItem
import com.practicaltest.testxento.model.book.DetailBookItem
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface BookService {
    @GET("books/v1/volumes")
    suspend fun getAllBook(
        @Query("q") q: String): Response<BookItem>

    @GET("books/v1/volumes/{id}")
    suspend fun getBookDetails(
        @Path("id") id: String): Response<DetailBookItem>

}
package com.practicaltest.testxento.data.repository

import android.util.Log
import com.practicaltest.testxento.data.entities.Book
import com.practicaltest.testxento.data.local.BookDao
import com.practicaltest.testxento.data.remote.BookRemoteDataSource
import com.practicaltest.testxento.model.book.Item
import com.practicaltest.testxento.utils.performGetOperation
import javax.inject.Inject

class BookRepository @Inject constructor(
    private val bookRemoteDataSource: BookRemoteDataSource,
    private val localBookDataSource: BookDao
) {

    fun getBooks(q: String) = performGetOperation(
        databaseQuery = { localBookDataSource.getAllBook() },
        networkCall = { bookRemoteDataSource.getAllBook(q) },
        saveCallResult = { localBookDataSource.insertAll(getBookDBList(it.items)) }
    )

    fun getBookDBList(itemResponse: List<Item>?): List<Book> {
        Log.d("Test: ","Booksize: "+itemResponse?.size)
        val bookList = ArrayList<Book>()
        itemResponse?.forEach { item ->
            val model = item.volumeInfo
            bookList.add(
                Book(
                    0,
                    model?.title,
                    model?.description,
                    model?.authors?.get(0),
                    model?.publisher,
                    model?.publishedDate,
                    model?.averageRating.toString(),
                    model?.ratingsCount.toString(),
                    model?.imageLinks?.thumbnail.toString(),
                    model?.infoLink.toString()
                )
            )
        }
        return bookList

    }

}
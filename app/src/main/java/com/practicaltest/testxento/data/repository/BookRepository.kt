package com.practicaltest.testxento.data.repository

import com.practicaltest.testxento.data.entities.Book
import com.practicaltest.testxento.data.local.BookDao
import com.practicaltest.testxento.data.remote.BookRemoteDataSource
import com.practicaltest.testxento.model.book.DetailBookItem
import com.practicaltest.testxento.model.book.Item
import com.practicaltest.testxento.utils.performGetOperation
import javax.inject.Inject

class BookRepository @Inject constructor(
    private val bookRemoteDataSource: BookRemoteDataSource,
    private val localBookDataSource: BookDao
) {
    fun getBookDetails(id: String) = performGetOperation(
        databaseQuery = { localBookDataSource.getBook(id) },
        networkCall = { bookRemoteDataSource.getBookDetails(id) },
        saveCallResult = { localBookDataSource.insert(getBookDetailsList(it)) }
    )

    fun getBooks(q: String) = performGetOperation(
        databaseQuery = { localBookDataSource.getAllBook() },
        networkCall = { bookRemoteDataSource.getAllBook(q) },
        saveCallResult = { localBookDataSource.insertAll(getBookDBList(it.items)) }
    )

    fun getBookDBList(itemResponse: List<Item>?): List<Book> {
        val bookList = ArrayList<Book>()
        itemResponse?.forEach { item ->
            val model = item.volumeInfo
            bookList.add(
                Book(
                    0,
                    item.id,
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

    fun getBookDetailsList(detailBookItem: DetailBookItem): Book {
        val model = detailBookItem.volumeInfo

        return Book(
            0,
            detailBookItem.id,
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

    }

}
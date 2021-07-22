package com.practicaltest.testxento.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.practicaltest.testxento.data.entities.Book

@Dao
interface BookDao {

    @Query("SELECT * FROM bookItem")
    fun getAllBook() : LiveData<List<Book>>

    @Query("SELECT * FROM bookItem WHERE bookId = :bookId")
    fun getBook(bookId: String): LiveData<Book>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(books: List<Book>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(book: Book)


}
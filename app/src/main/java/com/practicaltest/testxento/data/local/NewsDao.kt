package com.practicaltest.testxento.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.practicaltest.testxento.data.entities.News

@Dao
interface NewsDao {

    @Query("SELECT * FROM newsItem")
    fun getAllNews() : LiveData<List<News>>

    @Query("SELECT * FROM newsItem WHERE id = :id")
    fun getNews(id: Int): LiveData<News>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(books: List<News>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(news: News)


}
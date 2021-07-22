package com.practicaltest.testxento.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bookItem")
data class Book(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val bookId: String?,
    val title: String?,
    val description: String?,
    val authors:String?,
    val publisher: String?,
    val publishedDate: String?,
    val averageRating: String?,
    val ratingsCount: String?,
    val thumbnail: String?,
    val infoLink: String?,
)
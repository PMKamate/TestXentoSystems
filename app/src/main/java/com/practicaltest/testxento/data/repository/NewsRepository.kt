package com.practicaltest.testxento.data.repository

import com.practicaltest.testxento.data.entities.News
import com.practicaltest.testxento.data.local.NewsDao
import com.practicaltest.testxento.data.remote.NewsRemoteDataSource
import com.practicaltest.testxento.model.news.Article
import com.practicaltest.testxento.utils.performGetOperation
import javax.inject.Inject

class NewsRepository @Inject constructor(
    private val newsRemoteDataSource: NewsRemoteDataSource,
    private val localNewsDataSource: NewsDao
) {

    fun getNews() = performGetOperation(
        databaseQuery = { localNewsDataSource.getAllNews() },
        networkCall = { newsRemoteDataSource.getAllNews() },
        saveCallResult = { localNewsDataSource.insertAll(getNewsList(it.article)) }
    )

    fun getNewsList(itemResponse: List<Article>?): List<News> {
        val newsList = ArrayList<News>()
        itemResponse?.forEach { model ->
            newsList.add(
                News(
                    0,
                    model.title,
                    model.description,
                    model.url,
                    model.urlToImage,
                    model.publishedAt.toString(),
                    model.author
                )
            )
        }
        return newsList

    }

}
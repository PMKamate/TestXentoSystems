package com.practicaltest.testxento.data.remote

import com.practicaltest.testxento.utils.AppConstant
import com.practicaltest.testxento.utils.Utils
import javax.inject.Inject

class NewsRemoteDataSource @Inject constructor(
    private val newsService: NewsService
): BaseDataSource() {
    val country: String = Utils.getCountry()

    suspend fun getAllNews() = getResult { newsService.getAllNews(country,AppConstant.NEWS_API_KEY) }
}
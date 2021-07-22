package com.practicaltest.testxento.data.remote

import com.practicaltest.testxento.utils.AppConstant
import com.practicaltest.testxento.utils.Utils
import javax.inject.Inject

class NewsRemoteDataSource @Inject constructor(
    private val newsService: NewsService
): BaseDataSource() {

    suspend fun getAllNews() = getResult { newsService.getAllNews(Utils.getCountry(),AppConstant.NEWS_API_KEY) }
}
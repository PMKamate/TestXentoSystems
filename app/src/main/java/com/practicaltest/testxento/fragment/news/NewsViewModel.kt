package com.practicaltest.testxento.fragment.news

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.practicaltest.testxento.data.repository.NewsRepository

//@HiltViewModel
class NewsViewModel @ViewModelInject constructor(
    private val repository: NewsRepository
) : ViewModel() {

    val newsDataSource = repository.getNews()
}

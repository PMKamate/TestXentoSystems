package com.practicaltest.testxento.di

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.practicaltest.testxento.data.local.AppDatabase
import com.practicaltest.testxento.data.local.BookDao
import com.practicaltest.testxento.data.local.NewsDao
import com.practicaltest.testxento.data.remote.BookRemoteDataSource
import com.practicaltest.testxento.data.remote.BookService
import com.practicaltest.testxento.data.remote.NewsRemoteDataSource
import com.practicaltest.testxento.data.remote.NewsService
import com.practicaltest.testxento.data.repository.BookRepository
import com.practicaltest.testxento.data.repository.NewsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Singleton
    @Provides
    @Named("retrofit_1")
    fun provideRetrofit(gson: Gson) : Retrofit = Retrofit.Builder()
        .baseUrl("https://www.googleapis.com/")
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    @Singleton
    @Provides
    @Named("retrofit_2")
    fun provideRetrofit2(gson: Gson) : Retrofit = Retrofit.Builder()
        .baseUrl("https://newsapi.org/v2/")
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    @Provides
    fun provideGson(): Gson = GsonBuilder().create()

    @Provides
    fun provideBookService(@Named("retrofit_1")retrofit: Retrofit): BookService = retrofit.create(BookService::class.java)

    @Singleton
    @Provides
    fun provideBookRemoteDataSource(bookService: BookService) = BookRemoteDataSource(bookService)

    @Provides
    fun provideNewsService(@Named("retrofit_2")retrofit2: Retrofit): NewsService = retrofit2.create(NewsService::class.java)

    @Singleton
    @Provides
    fun provideNewsRemoteDataSource(newsService: NewsService) = NewsRemoteDataSource(newsService)

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext appContext: Context) = AppDatabase.getDatabase(appContext)

    @Singleton
    @Provides
    fun provideBookDao(db: AppDatabase) = db.bookDao()

    @Singleton
    @Provides
    fun provideNewsDao(db: AppDatabase) = db.newsDao()

    @Singleton
    @Provides
    fun provideBookRepository(remoteDataSource: BookRemoteDataSource,
                          localDataSource: BookDao) =
        BookRepository(remoteDataSource, localDataSource)


    @Singleton
    @Provides
    fun provideNewsRepository(remoteDataSource: NewsRemoteDataSource,
                          localDataSource: NewsDao) =
        NewsRepository(remoteDataSource, localDataSource)
}
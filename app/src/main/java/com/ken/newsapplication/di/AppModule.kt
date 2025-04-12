package com.ken.newsapplication.di

import android.app.Application
import androidx.room.Room
import com.ken.newsapplication.data.local.NewsDao
import com.ken.newsapplication.data.local.NewsDatabase
import com.ken.newsapplication.data.local.NewsTypeConverter
import com.ken.newsapplication.data.manager.LocalUserManagerImpl
import com.ken.newsapplication.data.remote.NewsAPI
import com.ken.newsapplication.data.repository.NewsRepositoryImpl
import com.ken.newsapplication.domain.manager.LocalUserManager
import com.ken.newsapplication.domain.repository.NewsRepository
import com.ken.newsapplication.domain.usecases.appentry.AppEntryUseCases
import com.ken.newsapplication.domain.usecases.appentry.ReadAppEntry
import com.ken.newsapplication.domain.usecases.appentry.SaveAppEntry
import com.ken.newsapplication.domain.usecases.news.GetNews
import com.ken.newsapplication.domain.usecases.news.NewsUseCases
import com.ken.newsapplication.domain.usecases.news.SearchNews
import com.ken.newsapplication.domain.usecases.news.articlesoperation.DeleteArticle
import com.ken.newsapplication.domain.usecases.news.articlesoperation.InsertArticle
import com.ken.newsapplication.domain.usecases.news.articlesoperation.SelectArticle
import com.ken.newsapplication.domain.usecases.news.articlesoperation.SelectSingleArticle
import com.ken.newsapplication.util.Constants
import com.ken.newsapplication.util.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideLocalUserManager(
        application: Application,
    ): LocalUserManager = LocalUserManagerImpl(application)

    @Provides
    @Singleton
    fun provideAppEntryUseCases(localUserManager: LocalUserManager) = AppEntryUseCases(
        readAppEntry = ReadAppEntry(localUserManager = localUserManager),
        saveAppEntry = SaveAppEntry(localUserManager = localUserManager),
    )

    @Provides
    @Singleton
    fun provideNewsAPI(): NewsAPI {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NewsAPI::class.java)
    }

    @Provides
    @Singleton
    fun provideNewsRepository(
        newsAPI: NewsAPI,
        newsDao: NewsDao,
    ): NewsRepository = NewsRepositoryImpl(newsAPI, newsDao)


    @Provides
    @Singleton
    fun provideNewsUseCases(
        newsRepository: NewsRepository,
        newsDao: NewsDao,
    ): NewsUseCases {
        return NewsUseCases(
            getNews = GetNews(newsRepository = newsRepository),
            searchNews = SearchNews(newsRepository = newsRepository),
            insertArticle = InsertArticle(newsRepository = newsRepository),
            deleteArticle = DeleteArticle(newsRepository = newsRepository),
            selectArticle = SelectArticle(newsRepository = newsRepository),
            selectedSingleArticle = SelectSingleArticle(newsRepository = newsRepository)
        )
    }


    @Provides
    @Singleton
    fun provideNewsDatabase(
        application: Application,
    ): NewsDatabase {
        return Room.databaseBuilder(
            context = application,
            klass = NewsDatabase::class.java,
            name = Constants.DATABASE_NAME,
        ).addTypeConverter(NewsTypeConverter())
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideNewsDao(
        newsDatabase: NewsDatabase,
    ) = newsDatabase.newsDao

}
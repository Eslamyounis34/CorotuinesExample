package com.younis.newapp.model.com.example.corotuinesexample.di

import android.app.Application
import android.content.Context
import com.example.corotuinesexample.remote.NewsApi
import com.example.corotuinesexample.room.NewsDao
import com.example.corotuinesexample.room.NewsDataBase
import com.example.corotuinesexample.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    val base_url = Constants.BASE_URL

    @Singleton
    @Provides
    fun getRetrofitInstance(): NewsApi {
        return Retrofit.Builder()
            .baseUrl(base_url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NewsApi::class.java)
    }


    @Singleton
    @Provides
    fun getAppDB(context: Application): NewsDataBase{
        return NewsDataBase.getAppDataBase(context)
    }

    @Singleton
    @Provides
    fun getDao(appDB:NewsDataBase): NewsDao {
        return appDB.articleDao()
    }

}
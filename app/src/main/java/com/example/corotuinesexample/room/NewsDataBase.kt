package com.example.corotuinesexample.room

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.younis.newapp.model.Article

@Database(entities = [Article::class], version = 1)
@TypeConverters(Converters::class)
abstract class NewsDataBase : RoomDatabase() {
    abstract fun articleDao(): NewsDao

    companion object {
        @Volatile
        private var INSTANCE: NewsDataBase? = null


        fun getAppDataBase(application: Application): NewsDataBase? {
         //   synchronized(this)
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder<NewsDataBase>(
                    application.applicationContext, NewsDataBase::class.java, "AppDB"
                )
                    .build()
            }
            return INSTANCE
        }

    }
}
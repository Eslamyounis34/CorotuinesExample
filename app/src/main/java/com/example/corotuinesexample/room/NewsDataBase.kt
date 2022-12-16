package com.example.corotuinesexample.room

import android.app.Application
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.younis.newapp.model.Article

@Database(entities = [Article::class], version = 3 , exportSchema = false)
@TypeConverters(Converters::class)
abstract class NewsDataBase : RoomDatabase() {
    abstract fun articleDao(): NewsDao

    companion object {
        @Volatile
        private var INSTANCE: NewsDataBase? = null


        fun getAppDataBase(context: Context): NewsDataBase {
         //   synchronized(this)
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder<NewsDataBase>(
                    context.applicationContext, NewsDataBase::class.java, "AppDB"
                )
                    .build()
            }
            return INSTANCE!!
        }

    }
}
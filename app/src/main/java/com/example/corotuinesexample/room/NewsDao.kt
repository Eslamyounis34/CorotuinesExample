package com.example.corotuinesexample.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.younis.newapp.model.Article

@Dao
interface NewsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArticle(article: Article)

    @Query("select * from articles")
    fun articlesList(): LiveData<List<Article>>

    @Delete
    suspend fun deleteArticle(article: Article)

    @Query("SELECT EXISTS (SELECT 1 FROM articles WHERE url = :url)")
    suspend fun isExisted(url: String): Boolean
}
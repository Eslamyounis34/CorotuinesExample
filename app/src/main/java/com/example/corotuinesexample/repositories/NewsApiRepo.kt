package com.younis.newapp.model.com.example.corotuinesexample.repositories

import androidx.lifecycle.LiveData
import com.example.corotuinesexample.remote.NewsApi
import com.example.corotuinesexample.room.NewsDao
import com.example.corotuinesexample.util.Constants
import com.younis.newapp.model.Article
import javax.inject.Inject

class NewsApiRepo @Inject constructor(private val api: NewsApi ,private val db: NewsDao) {

    suspend fun getBreakingNews() =
        api.getBreakingNews("us", "business", Constants.API_KEY)


    fun getFavoriteNews(): LiveData<List<Article>> =
        db.articlesList()

    suspend fun insertArticle(article: Article){
        db.insertArticle(article)
    }

    suspend fun getSearchResult(query: String) =
        api.getSearchResult(Constants.API_KEY, query, "popularity")


    suspend fun checkExistBefore(url: String): Boolean {
        return db.isExisted(url)
    }
}
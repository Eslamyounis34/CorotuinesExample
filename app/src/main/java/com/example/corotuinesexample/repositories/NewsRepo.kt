package com.example.corotuinesexample.repositories

import androidx.lifecycle.LiveData
import com.example.corotuinesexample.remote.RetroInstance
import com.example.corotuinesexample.room.NewsDao
import com.younis.newapp.model.Article
import com.example.corotuinesexample.util.Constants

class NewsRepo(private val db: NewsDao) {

//     fun getFavoriteNews(): LiveData<List<Article>> =
//        db.articlesList()

//
//    suspend fun getBreakingNews() =
//        RetroInstance.api.getBreakingNews("us", "business", Constants.API_KEY)
//
//    suspend fun getSearchResult(query:String)=
//        RetroInstance.api.getSearchResult(Constants.API_KEY,query,"popularity")

    suspend fun insertArticle(article: Article) {

        db.insertArticle(article)
    }

    suspend fun checkExistBefore(url: String): Boolean {
        return db.isExisted(url)
    }


}
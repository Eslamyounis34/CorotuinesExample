package com.example.corotuinesexample.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.corotuinesexample.repositories.NewsRepo
import com.example.corotuinesexample.room.NewsDataBase
import com.example.corotuinesexample.util.Resource
import com.younis.newapp.model.Article
import com.younis.newapp.model.NewsResponse
import kotlinx.coroutines.*
import retrofit2.Response

class NewsViewModel(application: Application) : AndroidViewModel(application) {
    val _response = MutableLiveData<NewsResponse>()
    var _existance = MutableLiveData<Boolean>()
   // var breakingNews: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    private val newsRepo: NewsRepo
    lateinit var favoriteArticlesList: LiveData<List<Article>>
    val articleList: LiveData<NewsResponse>
        get() = _response

    init {
        val favDao = NewsDataBase.getAppDataBase(application)!!.articleDao()
        newsRepo = NewsRepo(favDao)
        getArticlesList()
        getFavoriteNews()
    }

    private fun getArticlesList() = viewModelScope.launch {
        newsRepo.getBreakingNews().let { response ->
            if (response.isSuccessful) {
                _response.postValue(response.body())
            } else {
                Log.e("Show Error", response.message().toString())
            }
        }
    }

    private fun getFavoriteNews(): Job = viewModelScope.launch {
        favoriteArticlesList = newsRepo.getFavoriteNews()
    }

    fun insertArticle(article: Article): Job = viewModelScope.launch {
        newsRepo.insertArticle(article)
    }


    fun isExisted(url: String): MutableLiveData<Boolean> {
        viewModelScope.launch {
            _existance.postValue(newsRepo.checkExistBefore(url))
        }
        return _existance
    }


}
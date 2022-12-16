package com.younis.newapp.model.com.example.corotuinesexample.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.corotuinesexample.room.NewsDataBase
import com.younis.newapp.model.Article
import com.younis.newapp.model.NewsResponse
import com.younis.newapp.model.com.example.corotuinesexample.repositories.NewsApiRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class BreakingNewsViewModel
@Inject constructor
    (private val repo: NewsApiRepo) : ViewModel() {
    val _response = MutableLiveData<NewsResponse>()
    var _existance = MutableLiveData<Boolean>()



    fun loadFavNews(): LiveData<List<Article>> {
        val list = repo.getFavoriteNews()
        return list
    }

    fun getBreakingNews(): LiveData<NewsResponse> {
        viewModelScope.launch(Dispatchers.IO) {
            val response = repo.getBreakingNews()

            if (response.isSuccessful) {
                _response.postValue(response.body())
            } else {
                Log.e("TestError", response.message().toString())
            }
        }
        return _response
    }

    fun insertArticle(article: Article) {
        viewModelScope.launch {
            repo.insertArticle(article)
        }
    }

    fun isExisted(url: String): MutableLiveData<Boolean> {
        viewModelScope.launch {
            _existance.postValue(repo.checkExistBefore(url))
        }
        return _existance
    }
}
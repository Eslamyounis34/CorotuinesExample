package com.example.corotuinesexample.ui.fragments

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.corotuinesexample.R
import com.example.corotuinesexample.databinding.FragmentSavedNewsBinding
import com.example.corotuinesexample.ui.adapters.NewsRecyclerView
import com.example.corotuinesexample.viewmodels.NewsViewModel
import com.younis.newapp.model.Article
import com.younis.newapp.model.OnArticleListner

class SavedNewsFragment : Fragment(), OnArticleListner {

    lateinit var viewModel: NewsViewModel
    lateinit var newsAdapter: NewsRecyclerView
    lateinit var binding: FragmentSavedNewsBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSavedNewsBinding.inflate(layoutInflater)
        val view = binding.root
        viewModel = ViewModelProvider(this).get(NewsViewModel::class.java)

        if (isNetworkAvailable()) {
            getSavedNews()
        } else {
            getSavedNews()
            Toast.makeText(context, "No Network", Toast.LENGTH_SHORT).show()
        }
        return view
    }

    override fun onclick(article: Article) {
        val action =
            SavedNewsFragmentDirections.actionSavedNewsFragment2ToSelectedArticleFragment(article)
        findNavController()
            .navigate(action)
    }

    private fun isNetworkAvailable(): Boolean {
        val connectivityManager = context?.getSystemService(Context.CONNECTIVITY_SERVICE)
        return if (connectivityManager is ConnectivityManager) {
            val networkInfo: NetworkInfo? = connectivityManager.activeNetworkInfo
            networkInfo?.isConnected ?: false
        } else false
    }

    private fun getSavedNews() {
        viewModel.favoriteArticlesList.observe(viewLifecycleOwner, Observer {
            binding.favoritesRecycler.apply {
                adapter = NewsRecyclerView(it)
                newsAdapter = adapter as NewsRecyclerView
                newsAdapter.setOnItemClickListener(this@SavedNewsFragment)
            }
        })
    }
}
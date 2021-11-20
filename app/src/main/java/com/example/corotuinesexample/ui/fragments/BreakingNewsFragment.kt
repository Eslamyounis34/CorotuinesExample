package com.example.corotuinesexample.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.corotuinesexample.R
import com.example.corotuinesexample.databinding.FragmentBreakingNewsBinding
import com.example.corotuinesexample.ui.adapters.NewsRecyclerView
import com.example.corotuinesexample.viewmodels.NewsViewModel
import com.younis.newapp.model.Article
import com.younis.newapp.model.OnArticleListner


class BreakingNewsFragment : Fragment(), OnArticleListner {

    lateinit var viewModel: NewsViewModel
    lateinit var newsAdapter: NewsRecyclerView
    lateinit var binding: FragmentBreakingNewsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        binding = FragmentBreakingNewsBinding.inflate(layoutInflater)
        val view = binding.root

        viewModel = ViewModelProvider(this).get(NewsViewModel::class.java)
        viewModel.articleList.observe(viewLifecycleOwner, Observer {
            binding.newsRecycler.apply {
                adapter = NewsRecyclerView(it.articles)
                newsAdapter = adapter as NewsRecyclerView
                newsAdapter.setOnItemClickListener(this@BreakingNewsFragment)
            }
        })
        return view
    }

    override fun onclick(article: Article) {
        val action =
            BreakingNewsFragmentDirections.actionBreakingNewsFragment2ToSelectedArticleFragment(
                article
            )
        findNavController()
            .navigate(action)
    }
}
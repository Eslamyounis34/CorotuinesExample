package com.example.corotuinesexample.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.example.corotuinesexample.R
import com.example.corotuinesexample.databinding.FragmentSavedNewsBinding
import com.example.corotuinesexample.databinding.FragmentSelectedArticleBinding
import com.example.corotuinesexample.viewmodels.NewsViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.younis.newapp.model.Article


class SelectedArticleFragment : Fragment() {

    val args: SelectedArticleFragmentArgs by navArgs()

    lateinit var article: Article
    lateinit var viewModel: NewsViewModel
    lateinit var binding: FragmentSelectedArticleBinding
    var found = false
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentSelectedArticleBinding.inflate(layoutInflater)
        val view = binding.root
        val article = args.article

        viewModel = ViewModelProvider(this).get(NewsViewModel::class.java)
        binding.webview
            .apply {
                settings.javaScriptEnabled = true
                webViewClient = WebViewClient()
                if (article != null) {
                    loadUrl(article.url.toString())
                }
            }
        viewModel.isExisted(article?.url.toString()).observe(viewLifecycleOwner, Observer {
            if (it == true) {
                found = true
                binding.insertButton.setImageResource(R.drawable.is_fav_icon)
            } else {
                found = false
                binding.insertButton.setImageResource(R.drawable.not_fav_icon)
            }
            Log.e("CheckExistance", found.toString())
        })

        binding.insertButton.setOnClickListener {
            if (found) {
                Toast.makeText(context, "Already Existed", Toast.LENGTH_LONG).show()
            } else {
                viewModel.insertArticle(article!!)
                binding.insertButton.setImageResource(R.drawable.is_fav_icon)
                found = true
                Toast.makeText(context, "Inserted", Toast.LENGTH_LONG).show()
            }
            Log.e("CheckExistance", found.toString())
        }
        return view
    }
}
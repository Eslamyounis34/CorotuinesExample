package com.example.corotuinesexample.ui.fragments

import android.content.Context
import android.graphics.Color
import android.net.ConnectivityManager
import android.net.NetworkInfo
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
import cn.pedant.SweetAlert.SweetAlertDialog
import com.example.corotuinesexample.R
import com.example.corotuinesexample.databinding.FragmentSavedNewsBinding
import com.example.corotuinesexample.databinding.FragmentSelectedArticleBinding
import com.example.corotuinesexample.viewmodels.NewsViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.younis.newapp.model.Article


class SelectedArticleFragment : Fragment() {

    val args: SelectedArticleFragmentArgs by navArgs()
    lateinit var errorSweetAlert: SweetAlertDialog
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
        if (isNetworkAvailable()) {
            val article = args.article

            viewModel = ViewModelProvider(this).get(NewsViewModel::class.java)

            checkFavorits(article!!.url.toString())
            loadArticleUrl(article!!.url.toString())
            binding.insertButton.setOnClickListener {
                insertArticle(article!!)
            }

        } else {
            setupErrorSweetAlert()
        }
        return view
    }

    private fun isNetworkAvailable(): Boolean {
        val connectivityManager = context?.getSystemService(Context.CONNECTIVITY_SERVICE)
        return if (connectivityManager is ConnectivityManager) {
            val networkInfo: NetworkInfo? = connectivityManager.activeNetworkInfo
            networkInfo?.isConnected ?: false
        } else false
    }

    private fun setupErrorSweetAlert() {
        errorSweetAlert = SweetAlertDialog(context, SweetAlertDialog.BUTTON_CONFIRM)

        errorSweetAlert.getProgressHelper().setBarColor(Color.parseColor("#000000"))
        errorSweetAlert.setTitleText("No Network Found")
        errorSweetAlert.setCancelable(false)
        errorSweetAlert.show();
        errorSweetAlert.setConfirmButton("Ok", SweetAlertDialog.OnSweetClickListener {
            errorSweetAlert.dismissWithAnimation()
        })
    }

    private fun insertArticle(selectedArticle: Article) {
        if (found) {
            Toast.makeText(context, "Already Existed", Toast.LENGTH_LONG).show()
        } else {
            viewModel.insertArticle(selectedArticle!!)
            binding.insertButton.setImageResource(R.drawable.is_fav_icon)
            found = true
            Toast.makeText(context, "Inserted", Toast.LENGTH_LONG).show()
        }
        Log.e("CheckExistance", found.toString())
    }

    private fun checkFavorits(articleUrl:String) {
        viewModel.isExisted(articleUrl).observe(viewLifecycleOwner, Observer {
            if (it == true) {
                found = true
                binding.insertButton.setImageResource(R.drawable.is_fav_icon)
            } else {
                found = false
                binding.insertButton.setImageResource(R.drawable.not_fav_icon)
            }
            Log.e("CheckExistance", found.toString())
        })
    }

    private fun loadArticleUrl(articleUrl: String) {
        binding.webview
            .apply {
                settings.javaScriptEnabled = true
                webViewClient = WebViewClient()
                loadUrl(articleUrl)
            }
    }
}
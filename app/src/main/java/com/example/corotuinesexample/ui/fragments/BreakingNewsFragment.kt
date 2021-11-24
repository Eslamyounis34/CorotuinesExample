package com.example.corotuinesexample.ui.fragments

import android.content.Context
import android.graphics.Color
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
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import cn.pedant.SweetAlert.SweetAlertDialog
import com.example.corotuinesexample.R
import com.example.corotuinesexample.databinding.FragmentBreakingNewsBinding
import com.example.corotuinesexample.ui.adapters.NewsRecyclerView
import com.example.corotuinesexample.viewmodels.NewsViewModel
import com.younis.newapp.model.Article
import com.younis.newapp.model.OnArticleListner
import kotlinx.android.synthetic.main.fragment_breaking_news.*


class BreakingNewsFragment : Fragment(), OnArticleListner {

    lateinit var viewModel: NewsViewModel
    lateinit var newsAdapter: NewsRecyclerView
    lateinit var binding: FragmentBreakingNewsBinding
    lateinit var sweetAlert: SweetAlertDialog
    lateinit var errorSweetAlert: SweetAlertDialog


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentBreakingNewsBinding.inflate(layoutInflater)
        val view = binding.root

        binding.swipetoRefresh.setOnRefreshListener {
            if (isNetworkAvailable()) {
                binding.nobreakingnewsdata.visibility = View.GONE
                binding.newsRecycler.visibility = View.VISIBLE
                Toast.makeText(context, "Updated", Toast.LENGTH_SHORT).show()
                getBreakingNews()
                binding.swipetoRefresh.isRefreshing = false
            } else {
                binding.nobreakingnewsdata.visibility = View.VISIBLE
                binding.newsRecycler.visibility = View.GONE
                Toast.makeText(context, "No Data", Toast.LENGTH_SHORT).show()
                binding.swipetoRefresh.isRefreshing = false
                setupErrorSweetAlert()
            }
        }
        sweetAlert = SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE)
        viewModel = ViewModelProvider(this).get(NewsViewModel::class.java)

        if (isNetworkAvailable()) {
            binding.nobreakingnewsdata.visibility = View.GONE
            binding.newsRecycler.visibility = View.VISIBLE

            getBreakingNews()
        } else {
            binding.nobreakingnewsdata.visibility = View.VISIBLE
            binding.newsRecycler.visibility = View.GONE
            setupErrorSweetAlert()
        }


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

    private fun isNetworkAvailable(): Boolean {
        val connectivityManager = context?.getSystemService(Context.CONNECTIVITY_SERVICE)
        return if (connectivityManager is ConnectivityManager) {
            val networkInfo: NetworkInfo? = connectivityManager.activeNetworkInfo
            networkInfo?.isConnected ?: false
        } else false
    }

    private fun setupSweetAlert(title: String) {

        sweetAlert.getProgressHelper().setBarColor(Color.parseColor("#000000"))
        sweetAlert.setTitleText(title)
        sweetAlert.setCancelable(false)
        sweetAlert.show()

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

    private fun getBreakingNews() {
        setupSweetAlert("loading")
        viewModel.getArticlesList().observe(viewLifecycleOwner, Observer {
            binding.newsRecycler.apply {
                adapter = NewsRecyclerView(it.articles)
                newsAdapter = adapter as NewsRecyclerView
                sweetAlert.dismiss()
                newsAdapter.setOnItemClickListener(this@BreakingNewsFragment)
            }
        })
    }
}
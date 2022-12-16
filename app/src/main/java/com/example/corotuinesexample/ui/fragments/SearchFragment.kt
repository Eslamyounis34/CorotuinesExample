package com.younis.newapp.model.com.example.corotuinesexample.ui.fragments

import android.content.Context
import android.graphics.Color
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import cn.pedant.SweetAlert.SweetAlertDialog
import com.example.corotuinesexample.databinding.FragmentSearchBinding
import com.example.corotuinesexample.ui.adapters.NewsRecyclerView
import com.example.corotuinesexample.viewmodels.NewsViewModel
import com.younis.newapp.model.Article
import com.younis.newapp.model.OnArticleListner


class SearchFragment : Fragment(), OnArticleListner {

    lateinit var binding: FragmentSearchBinding
//    lateinit var viewModel: NewsViewModel
//    lateinit var newsAdapter: NewsRecyclerView
//    lateinit var sweetAlert: SweetAlertDialog
//    lateinit var errorSweetAlert: SweetAlertDialog


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSearchBinding.inflate(layoutInflater)
        val view = binding.root

//        if (isNetworkAvailable()) {
//
//            viewModel = ViewModelProvider(this).get(NewsViewModel::class.java)
//
//            binding.searcharticleimagebutton.setOnClickListener {
//                setupSweetAlert()
//                if (isNetworkAvailable()) {
//                    getSearchResult()
//
//                } else {
//                    sweetAlert.dismiss()
//                    binding.nodatafound.text = "No Network Available"
//                    binding.nodatafound.visibility = View.VISIBLE
//                    binding.searcharticlesrecyclerview.visibility = View.INVISIBLE
//                }
//            }
//        } else {
//            setupErrorSweetAlert()
//        }

        return view
    }

    override fun onclick(article: Article) {
        val action =
            SearchFragmentDirections.actionSearchFragmentToSelectedArticleFragment(
                article
            )
        findNavController()
            .navigate(action)
    }

//    private fun isNetworkAvailable(): Boolean {
//        val connectivityManager = context?.getSystemService(Context.CONNECTIVITY_SERVICE)
//        return if (connectivityManager is ConnectivityManager) {
//            val networkInfo: NetworkInfo? = connectivityManager.activeNetworkInfo
//            networkInfo?.isConnected ?: false
//        } else false
//    }
//
//    private fun setupSweetAlert() {
//        sweetAlert = SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE)
//
//        sweetAlert.getProgressHelper().setBarColor(Color.parseColor("#000000"));
//        sweetAlert.setTitleText("Loading");
//        sweetAlert.setCancelable(false);
//        sweetAlert.show();
//    }
//
//    private fun setupErrorSweetAlert() {
//        errorSweetAlert = SweetAlertDialog(context, SweetAlertDialog.BUTTON_CONFIRM)
//
//        errorSweetAlert.getProgressHelper().setBarColor(Color.parseColor("#000000"))
//        errorSweetAlert.setTitleText("No Network Found")
//        errorSweetAlert.setCancelable(false)
//        errorSweetAlert.show();
//        errorSweetAlert.setConfirmButton("Ok", SweetAlertDialog.OnSweetClickListener {
//            errorSweetAlert.dismissWithAnimation()
//        })
//    }
//
//    private fun getSearchResult(){
//        val query = binding.searchnewsedittext.text.toString()
//
//        viewModel.searchResult(query).observe(viewLifecycleOwner, Observer {
//            if (it.articles.isEmpty()) {
//                sweetAlert.dismiss()
//                binding.nodatafound.visibility = View.VISIBLE
//                binding.searcharticlesrecyclerview.visibility = View.INVISIBLE
//                binding.nodatafound.text = "No Data Found"
//
//            } else {
//                binding.nodatafound.visibility = View.INVISIBLE
//                binding.searcharticlesrecyclerview.visibility = View.VISIBLE
//                binding.searcharticlesrecyclerview.apply {
//                    adapter = NewsRecyclerView(it.articles)
//                    newsAdapter = adapter as NewsRecyclerView
//                    sweetAlert.dismiss()
//                    newsAdapter.setOnItemClickListener(this@SearchFragment)
//                }
//            }
//        })
//    }
}
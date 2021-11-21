package com.example.corotuinesexample.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.example.corotuinesexample.R
import com.example.corotuinesexample.databinding.NewsrecycleritemrowBinding
import com.squareup.picasso.Picasso
import com.younis.newapp.model.Article
import com.younis.newapp.model.OnArticleListner

class NewsRecyclerView(private val articles: List<Article>) :
    RecyclerView.Adapter<NewsRecyclerView.ViewHolder>() {

    class ViewHolder(var binding: NewsrecycleritemrowBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }

    private var onItemClickListener: OnArticleListner? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            NewsrecycleritemrowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        var v =
            LayoutInflater.from(parent.context).inflate(R.layout.newsrecycleritemrow, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.newstitle.text = articles[position].title
        Picasso.get().load(articles[position].urlToImage).into(holder.binding.newsimage)
        holder.binding.newrelativelayout.setOnClickListener {
            onItemClickListener?.onclick(articles[position]!!)
        }
    }

    override fun getItemCount() = articles.size

    fun setOnItemClickListener(listener: OnArticleListner) {
        onItemClickListener = listener
    }


}

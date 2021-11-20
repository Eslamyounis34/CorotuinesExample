package com.example.corotuinesexample.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.corotuinesexample.R
import com.squareup.picasso.Picasso
import com.younis.newapp.model.Article
import com.younis.newapp.model.OnArticleListner

class NewsRecyclerView(private val articles: List<Article>) :
    RecyclerView.Adapter<NewsRecyclerView.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var articleImage: ImageView = itemView.findViewById(R.id.newsimage)
        var articleTitle: TextView = itemView.findViewById(R.id.newstitle)

    }

    private var onItemClickListener: OnArticleListner? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var v =
            LayoutInflater.from(parent.context).inflate(R.layout.newsrecycleritemrow, parent, false)
        return NewsRecyclerView.ViewHolder(v)
    }

 //   val article = getItem(position)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.apply {
            holder.articleTitle.text = articles[position].title
            Picasso.get().load(articles[position].urlToImage).into(holder.articleImage)

            setOnClickListener {
                onItemClickListener?.onclick(articles[position]!!)
            }
        }
    }

    override fun getItemCount() = articles.size

    fun setOnItemClickListener(listener: OnArticleListner) {
        onItemClickListener = listener
    }


}

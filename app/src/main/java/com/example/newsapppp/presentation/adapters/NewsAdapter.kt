package com.example.newsapppp.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsapppp.databinding.ItemLayoutBinding
import com.example.newsapppp.presentation.model.Article
import com.example.newsapppp.presentation.utils.DateFormat.dateFormat
import kotlinx.android.synthetic.main.item_layout.view.*

class NewsAdapter: ListAdapter<Article, NewsAdapter.ArticleViewHolder>(NewsItemDiffCallback()) {
    var count = 0

    class ArticleViewHolder(val binding: ItemLayoutBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
//        Log.d("NewsAdapter", "onCreateViewHolder, count: ${++count}")
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemLayoutBinding.inflate(inflater, parent, false)

        return ArticleViewHolder(binding)
    }

    private var onItemClickListener: ((Article) -> Unit)? = null

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article = getItem(position)

        holder.itemView.apply {
            Glide.with(this)
                .load(article.urlToImage)
                .into(imArticleImage)
            tvTitle.text = article.title
            author.text = article.author
            tvDescription.text = article.description
            tvPublishedAt.text = dateFormat(article.publishedAt)

            setOnClickListener {
                onItemClickListener?.invoke(article)
            }
        }
    }

    fun setOnItemClickListener(listener: (Article) -> Unit) {
        onItemClickListener = listener
    }
}

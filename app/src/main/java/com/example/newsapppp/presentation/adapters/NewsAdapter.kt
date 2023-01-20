package com.example.newsapppp.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.example.newsapppp.databinding.ItemLayoutBinding
import com.example.newsapppp.presentation.model.Article
import com.example.newsapppp.core.DateFormat.dateFormat
import kotlinx.android.synthetic.main.item_layout.view.*

class NewsAdapter: ListAdapter<Article, NewsAdapter.ArticleViewHolder>(NewsItemDiffCallback()) {

    class ArticleViewHolder(val binding: ItemLayoutBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemLayoutBinding.inflate(inflater, parent, false)
        return ArticleViewHolder(binding)
    }

    private var onItemClickListener: ((Article) -> Unit)? = null
    private var onFavoriteIconClicked: ((Article) -> Unit)? = null


    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article = getItem(position)

        holder.itemView.apply {
            imArticleImage.load(article.urlToImage) {
                crossfade(true)
                crossfade(1000)
                transformations(RoundedCornersTransformation(30f))
            }
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

    fun setOnFavoriteClickListener(listener: (Article) -> Unit) {
        onFavoriteIconClicked = listener
    }
}

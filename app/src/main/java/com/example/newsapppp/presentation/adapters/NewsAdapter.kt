package com.example.newsapppp.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.example.newsapppp.core.DateFormat.dateFormat
import com.example.newsapppp.databinding.ItemLayoutBinding
import com.example.newsapppp.presentation.model.Article

/**
 * Adapter class responsible for showing a list of news articles.
 * It extends the ListAdapter class with Article as the data type and ArticleViewHolder.
 */
class NewsAdapter : ListAdapter<Article, NewsAdapter.ArticleViewHolder>(NewsItemDiffCallback()) {

    /**
     * ViewHolder class that holds the UI elements for an individual news article.
     */
    class ArticleViewHolder(val binding: ItemLayoutBinding) : RecyclerView.ViewHolder(binding.root)

    /**
     * Creates a new ArticleViewHolder by inflating the item layout.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemLayoutBinding.inflate(inflater, parent, false)
        return ArticleViewHolder(binding)
    }

    private var onItemClickListener: ((Article) -> Unit)? = null

    /**
     * Binds an individual news article to the ArticleViewHolder and sets a click listener on it.
     */
    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article = getItem(position)

        holder.binding.apply {
            imArticleImage.load(article.urlToImage) {
                crossfade(true)
                crossfade(1000)
                transformations(RoundedCornersTransformation(30f))
            }
            tvTitle.text = article.title
            author.text = article.author
            tvDescription.text = article.description
            tvPublishedAt.text = dateFormat(article.publishedAt)
            holder.itemView.setOnClickListener {
                onItemClickListener?.invoke(article)
            }
        }
    }

    /**
     * Sets an click listener on an individual news article.
     */
    fun setOnItemClickListener(listener: (Article) -> Unit) {
        onItemClickListener = listener
    }
}

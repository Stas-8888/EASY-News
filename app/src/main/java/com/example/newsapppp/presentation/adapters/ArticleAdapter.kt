package com.example.newsapppp.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.example.newsapppp.common.Constants.DURATION_300
import com.example.newsapppp.common.Constants.RADIUS_30
import com.example.newsapppp.databinding.ItemLayoutBinding
import com.example.newsapppp.common.extensions.getReformatDate
import com.example.newsapppp.presentation.model.Article

/**
 * Adapter class responsible for showing a list of news articles.
 * It extends the ListAdapter class with Article as the data type and ArticleViewHolder.
 */
class ArticleAdapter : ListAdapter<Article, ArticleAdapter.ArticleViewHolder>(ArticleDiffCallback()) {

    private var onItemClickListener: ((Article) -> Unit)? = null

    /**
     * ViewHolder class that holds the UI elements for an individual news article.
     */
    inner class ArticleViewHolder(val binding: ItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(article: Article) = with(binding) {
            imArticleImage.load(article.urlToImage) {
                crossfade(true)
                crossfade(DURATION_300)
                transformations(RoundedCornersTransformation(RADIUS_30))
            }
            tvTitle.text = article.title
            author.text = article.author
            tvDescription.text = article.description
            tvPublishedAt.text = getReformatDate(article.publishedAt)
            itemCard.setOnClickListener {
                onItemClickListener?.invoke(article)
            }
        }
    }

    /**
     * Creates a new ArticleViewHolder by inflating the item layout.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemLayoutBinding.inflate(inflater, parent, false)
        return ArticleViewHolder(binding)
    }

    /**
     * Binds an individual news article to the ArticleViewHolder and sets a click listener on it.
     */
    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    /**
     * Sets an click listener on an individual news article.
     */
    fun setOnItemClickListener(listener: (Article) -> Unit) {
        onItemClickListener = listener
    }
}

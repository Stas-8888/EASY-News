package com.example.newsapppp.presentation.adapters

import androidx.recyclerview.widget.DiffUtil
import com.example.newsapppp.presentation.model.Article

/**
 * A DiffUtil.ItemCallback for comparing Article objects in a RecyclerView.
 */
class NewsItemDiffCallback : DiffUtil.ItemCallback<Article>() {

    /**
     * Check whether two articles have the same identity.
     * @param oldItem The old Article object.
     * @param newItem The new Article object.
     * @return True if the articles have the same URL, false otherwise.
     */
    override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
        return oldItem.url == newItem.url
    }

    /**
     * Check whether two articles have the same contents.
     * @param oldItem The old Article object.
     * @param newItem The new Article object.
     * @return True if the articles are equal, false otherwise.
     */
    override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
        return oldItem == newItem
    }
}

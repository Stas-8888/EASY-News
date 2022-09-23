package com.example.newsapppp.presentation.adapters

import androidx.recyclerview.widget.DiffUtil
import com.example.newsapppp.presentation.model.Article

class NewsItemDiffCallback : DiffUtil.ItemCallback<Article>() {

    override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
        return oldItem.url == newItem.url
    }

    override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
        return oldItem == newItem
    }
}

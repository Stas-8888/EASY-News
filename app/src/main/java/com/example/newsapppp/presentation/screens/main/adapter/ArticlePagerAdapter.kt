package com.example.newsapppp.presentation.screens.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.example.newsapppp.R
import com.example.newsapppp.common.Constants.DURATION_300
import com.example.newsapppp.common.Constants.RADIUS_30
import com.example.newsapppp.databinding.ItemLayoutBinding
import com.example.newsapppp.presentation.adapters.ArticleDiffCallback
import com.example.newsapppp.common.extensions.getReformatDate
import com.example.newsapppp.presentation.model.Article

/**
 * A PagingDataAdapter for displaying a list of Article objects in a ViewPager2.
 */
class ArticlePagerAdapter :
    PagingDataAdapter<Article, ArticlePagerAdapter.ArticleViewHolder>(ArticleDiffCallback()) {

    /**
     * ViewHolder for an Article item in the ViewPager2.
     * @param binding The view binding for the item layout.
     */
    class ArticleViewHolder(val binding: ItemLayoutBinding) : RecyclerView.ViewHolder(binding.root)

    /**
     * Inflate the layout for an Article item and return its ViewHolder.
     * @param parent The ViewGroup that the ViewHolder will be attached to.
     * @param viewType The type of the view.
     * @return The ArticleViewHolder for the inflated item layout.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemLayoutBinding.inflate(inflater, parent, false)
        return ArticleViewHolder(binding)
    }

    private var onItemClickListener: ((Article) -> Unit)? = null

    /**
     * Bind an Article to a ViewHolder and set up click listener.
     * @param holder The ArticleViewHolder to bind the Article to.
     * @param position The position of the Article in the list.
     */
    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article = getItem(position)

        holder.itemView.apply {
            with(holder.binding) {
                val fadeIn = AnimationUtils.loadAnimation(context, R.anim.fade_in)
                tvPublishedAt.startAnimation(fadeIn)
                imArticleImage.load(article?.urlToImage) {
                    crossfade(true)
                    crossfade(DURATION_300)
                    transformations(RoundedCornersTransformation(RADIUS_30))
                }
                tvTitle.text = article?.title
                author.text = article?.author
                tvDescription.text = article?.description
                tvPublishedAt.text = getReformatDate(article?.publishedAt)
                setOnClickListener {
                    if (article != null) {
                        onItemClickListener?.invoke(article)
                    }
                }
            }
        }
    }

    /**
     * Set a click listener for each Article item in the ViewPager2.
     * @param listener The lambda function to call when an item is clicked.
     */
    fun setOnItemClickListener(listener: (Article) -> Unit) {
        onItemClickListener = listener
    }
}

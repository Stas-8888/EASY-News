package com.example.newsapppp.presentation.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * (0..Int.MAX_VALUE).random()
 * Class that represents a CurrencyPresentationModel in the presentation layer.
 */
@Parcelize
data class Article(
    val author: String?,
    val content: String?,
    val description: String?,
    val publishedAt: String?,
    val title: String?,
    val url: String,
    val urlToImage: String?
) : Parcelable

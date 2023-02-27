package com.example.newsapppp.core

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object DateFormat {
    fun dateFormat(dateNews: String?): String? {
        val isDate: String?
        val dateFormat = SimpleDateFormat("dd MMMM, yyyy", Locale(getCountry()))
        isDate = try {
            val date = dateNews?.let {
                SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale(getCountry())).parse(it)
            }
            date?.let { dateFormat.format(it) }
        } catch (e: ParseException) {
            e.printStackTrace()
            dateNews
        }
        return isDate
    }

    private fun getCountry(): String {
        val locale = Locale.getDefault()
        val strCountry = locale.country
        return strCountry.lowercase(Locale.ROOT)
    }
}

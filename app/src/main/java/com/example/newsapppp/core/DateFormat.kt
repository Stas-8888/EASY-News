package com.example.newsapppp.core

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object DateFormat{

    fun dateFormat(dateNews: String?): String? {
        val isDate: String?
        val dateFormat = SimpleDateFormat("dd MMMM, yyyy", Locale(getCountry()))
        isDate = try {
            val date = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").parse(dateNews)
            dateFormat.format(date)
        } catch (e: ParseException) {
            e.printStackTrace()
            dateNews
        }
        return isDate
    }

    private fun getCountry(): String {
        val locale = Locale.getDefault()
        val strCountry = locale.country
        return strCountry.toLowerCase()
    }
}

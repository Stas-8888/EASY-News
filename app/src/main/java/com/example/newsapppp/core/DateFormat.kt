package com.example.newsapppp.core

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object DateFormat {

    /**
     * Converts a date string in the format "yyyy-MM-dd'T'HH:mm:ss'Z'" to the
     * format "dd MMMM, yyyy". If the conversion fails, the original date string
     * is returned.
     *
     * @param dateNews The date string to convert.
     * @return The converted date string, or the original date string if the conversion fails.
     */
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

    /**
     * Returns country code of the current device locale in lowercase.
     */
    private fun getCountry(): String {
        val locale = Locale.getDefault()
        val strCountry = locale.country
        return strCountry.lowercase(Locale.ROOT)
    }
}

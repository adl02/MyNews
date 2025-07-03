package com.howtokaise.mynews.domain.data.room

import android.text.format.DateUtils
import java.text.SimpleDateFormat
import java.util.*

fun formatDateTime(isoDate : String):String{
    return try {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'",Locale.getDefault())
        inputFormat.timeZone = TimeZone.getTimeZone("UTC")
        val time = inputFormat.parse(isoDate)?.time ?: return ""
        val now = System.currentTimeMillis()

        DateUtils.getRelativeTimeSpanString(
            time,
            now,
            DateUtils.MINUTE_IN_MILLIS
        ).toString()
    }catch (e : Exception){
        " "
    }
}

fun limitWords(text : String, wordLimit : Int = 2): String {
     return text.split(" ")
         .take(wordLimit)
         .joinToString(" ")
}
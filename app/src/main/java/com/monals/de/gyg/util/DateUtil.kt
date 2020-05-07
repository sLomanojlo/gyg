package com.monals.de.gyg.util

import android.os.Build
import androidx.annotation.RequiresApi
import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.util.*

private const val GYG_TIME_PATTERN = "EEEE, MMMM dd, YYYY"
private const val ISO_OFFSET_PATTERN = "yyyy-MM-dd'T'HH:mm:ss"
 const val INVALID_DATE = "INVALID_DATE"

/**Util function for converting dates for different [Build.VERSION].*/
fun convertDate(created: String): String {

    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        parseDatePostO(created)
    } else {
        parseDatePreO(created)
    }
}

fun parseDatePreO(created: String): String {
    return try {
        val parser = SimpleDateFormat(ISO_OFFSET_PATTERN, Locale.getDefault())
        val formatter = SimpleDateFormat(GYG_TIME_PATTERN, Locale.getDefault())
        formatter.format(parser.parse(created)!!)
    } catch (e: ParseException) {
        INVALID_DATE
    }
}


@RequiresApi(Build.VERSION_CODES.O)
fun parseDatePostO(created: String): String {
    return try {
        val date = LocalDateTime.parse(created, DateTimeFormatter.ISO_OFFSET_DATE_TIME)
        val formatter = DateTimeFormatter.ofPattern(GYG_TIME_PATTERN)
        return formatter.format(date)
    } catch (e: DateTimeParseException) {
        INVALID_DATE
    }
}




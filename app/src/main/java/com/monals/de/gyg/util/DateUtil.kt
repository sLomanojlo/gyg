package com.monals.de.gyg.util

import android.os.Build
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

private const val GYG_TIME_PATTERN = "EEEE, MMMM dd, YYYY"
private const val ISO_OFFSET_PATTERN = "yyyy-MM-dd'T'HH:mm:ss"

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
fun convertDate(created: String) : String {

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val date = LocalDateTime.parse(created, DateTimeFormatter.ISO_OFFSET_DATE_TIME)
        val formatter = DateTimeFormatter.ofPattern(GYG_TIME_PATTERN)
        return formatter.format(date)
    } else {
        val parser = SimpleDateFormat(ISO_OFFSET_PATTERN, Locale.getDefault())
        val formatter = SimpleDateFormat (GYG_TIME_PATTERN, Locale.getDefault())
        return  formatter.format(parser.parse(created))
    }
}
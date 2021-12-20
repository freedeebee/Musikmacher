package de.freedeebee.musikmacher.util

import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

fun convertLongToDateString(systemTime: Long): String {
    return SimpleDateFormat("EEE dd.MM.yyyy", Locale.GERMAN)
        .format(systemTime).toString()
}

fun durationToMinutes(startMillis: Long, endMillis: Long): String {
    return "${TimeUnit.MILLISECONDS.toMinutes(endMillis - startMillis)}min(s)"
}
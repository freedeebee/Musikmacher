package de.freedeebee.musikmacher.util

import java.text.SimpleDateFormat
import java.util.*

fun convertLongToDateString(systemTime: Long): String {
    return SimpleDateFormat("EEE dd.MM.yyyy' : 'HH:mm", Locale.GERMAN)
        .format(systemTime).toString()
}
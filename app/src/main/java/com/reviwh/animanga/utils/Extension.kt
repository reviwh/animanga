package com.reviwh.animanga.utils

import android.content.Context
import com.reviwh.animanga.R
import java.text.DecimalFormat
import java.util.Calendar


fun Calendar.getSeason(context: Context): String {
    return when (this[Calendar.MONTH]) {
        in 3..5 -> context.getString(R.string.spring, this[Calendar.YEAR])
        in 6..8 -> context.getString(R.string.summer, this[Calendar.YEAR])
        in 9..11 -> context.getString(R.string.fall, this[Calendar.YEAR])
        else -> context.getString(R.string.winter, this[Calendar.YEAR])
    }
}

fun Int.getFormattedInt(format: String = "#,###"): String {
    val dec = DecimalFormat(format)
    return dec.format(this)
}
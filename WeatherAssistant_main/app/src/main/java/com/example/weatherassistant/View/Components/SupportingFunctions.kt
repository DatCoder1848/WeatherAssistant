package com.example.weatherassistant.View.Components

import android.content.Context
import java.time.DayOfWeek
import java.time.LocalDate

fun parseResIdFromTitle(context: Context, title: String, prefix: String? = null, suffix: String? = null, oldSeparatedChar: Char? = null, newSeparatedChar: Char? = null ): Int {
    val name = prefix + if (oldSeparatedChar != null && newSeparatedChar != null) title.replace(oldSeparatedChar,newSeparatedChar) else title + suffix
    return context.resources.getIdentifier(name, "drawable", context.packageName)
}

fun WhatTheDay(date: LocalDate): String {
    val today = LocalDate.now()
    return when(date){
        today -> "Hôm nay"
        today.plusDays(1) -> "Ngày mai"
        today.minusDays(1) -> "Hôm qua"
        else -> ""
    }
}

fun getWeekDate(date: LocalDate) = when(date.dayOfWeek){
        DayOfWeek.MONDAY -> "Thứ Hai"
        DayOfWeek.TUESDAY -> "Thứ Ba"
        DayOfWeek.WEDNESDAY -> "Thứ Tư"
        DayOfWeek.THURSDAY -> "Thứ Năm"
        DayOfWeek.FRIDAY -> "Thứ Sáu"
        DayOfWeek.SATURDAY -> "Thứ Bảy"
        DayOfWeek.SUNDAY -> "Chủ nhật"
    }

fun getDayDetail(date: LocalDate) = "ngày " + date.dayOfMonth + ", tháng " + date.monthValue +  ", năm " + date.year

fun evaluateUVLevel(uvIndex: Double) = when{
    uvIndex < 3 -> " Thấp"
    uvIndex < 6 -> " Trung bình"
    uvIndex < 8 -> " Cao"
    uvIndex < 11 -> " Rất cao"
    else -> " Nguy hiểm"
}
package com.example.weatherassistant.View.Components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weatherassistant.R
import java.time.DayOfWeek
import java.time.LocalDate

@Composable
fun MainInfo(condition: String, date: LocalDate, temp: Double, maxTemp: Double, minTemp: Double){
    val context = LocalContext.current

    val mainIconResId = parseResIdFromTitle(context = context, title = condition, prefix = "main_icon_", oldSeparatedChar = '-', newSeparatedChar = '_')

    Row(
        modifier = Modifier.fillMaxWidth()
            .height(IntrinsicSize.Min)
            .padding(horizontal = 10.dp)
            .padding(top = 20.dp)
    ){
        // Main Icon and condition:
        Column(
            modifier = Modifier.fillMaxWidth(0.4f).fillMaxHeight(),
            verticalArrangement = Arrangement.Center
        ){
            Icon(
                painter = painterResource(mainIconResId),
                contentDescription = "Main Icon",
                modifier = Modifier.fillMaxSize(0.8f)
            )
            Text(
                text = if (condition.isEmpty()) "Loading Status" else condition.replace('-', ' '),
                fontSize = 20.sp,
                modifier = Modifier.background(color = Color(0x88FFFFFF))
            )
        }

        // Current main information:
        Column(
            modifier = Modifier.weight(1f).background(Color(0x22FFFFFF)),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = WhatTheDay(date),
                fontSize = 30.sp,
                color = Color(0xDD000000),
                fontFamily = FontFamily(Font(R.font.robotomono_bold))
            )
            Text(
                text = "$temp°C",
                fontSize = 80.sp,
                color = Color.Black,
                fontFamily = FontFamily(Font(R.font.robotomono_bold)),
                lineHeight = 81.sp,
                maxLines = 2,
                softWrap = true,
                overflow = TextOverflow.Clip,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth(0.9f) // chiếm 90% chiều ngang của column
                    .align(Alignment.CenterHorizontally)
            )

            Text(
                text = "Cao nhất: " + "${maxTemp}" + "°C",
                fontSize = 15.sp,
                fontFamily = FontFamily(Font(R.font.robotomono_semibolditalic))
            )
            Text(
                text = "Thấp nhất: " + "${minTemp}" + "°C",
                fontSize = 15.sp,
                fontFamily = FontFamily(Font(R.font.robotomono_semibolditalic))
            )
        }
    }

}

@Composable
fun DateContainer(date: LocalDate){
    Column(
        modifier = Modifier.fillMaxWidth()
            .padding(top = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = getWeekDate(date),
            fontSize = 25.sp,
            color = Color.Black,
            fontFamily = FontFamily(Font(R.font.robotomono_bold))
        )
        Text(
            text = getDayDetail(date),
            fontSize = 15.sp,
            fontFamily = FontFamily(Font(R.font.robotomono_medium))
        )
    }
}


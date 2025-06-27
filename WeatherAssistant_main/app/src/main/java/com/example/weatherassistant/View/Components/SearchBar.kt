package com.example.weatherassistant.View.Components

import android.R.attr.width
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SearchBar(modifier: Modifier = Modifier, location: String, onLocationChange: (String) -> Unit ){
    OutlinedTextField(
        value = location,
        onValueChange = onLocationChange,
        placeholder = {
            Row(){
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = null,
                    modifier = Modifier.padding(end = 10.dp)
                )
                Text(
                    text = "Input your lacation",
                    fontStyle = FontStyle.Italic,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        modifier = modifier
            .padding(horizontal = 20.dp)
            .padding(top = 20.dp),
        shape = RoundedCornerShape(percent = 20),
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = Color(0x88FFFFFF).copy(alpha = 0.8f),
            unfocusedContainerColor = Color(0x88FFFFFF).copy(alpha = 0.8f),
            focusedTextColor = Color.Black,
            unfocusedTextColor = Color.Black
        )
    )
}

@Composable
fun LocationButton(modifier: Modifier = Modifier, location: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 10.dp)
            // Click lambda function here
            .clickable(onClick = onClick),
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Filled.LocationOn,
            contentDescription = null,
            modifier = Modifier.size(50.dp).padding(end = 10.dp),
            tint = Color.Red
        )
        Text(
            text = if (location.isEmpty()) "No location set" else location,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            fontSize = 25.sp,
            modifier = Modifier.background(color = Color(0xAAEEEEEE))
        )
    }
}

@Composable
fun BackgroundContainer(title: String, modifier: Modifier = Modifier, components: @Composable () -> Unit){
    val context = LocalContext.current

    val bgResId = parseResIdFromTitle(context = context, title = title, prefix = "bg_", oldSeparatedChar = '-', newSeparatedChar = '_')

    Box(
        modifier = Modifier.fillMaxSize()
    ){
        Image(
            painter = painterResource(bgResId),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.matchParentSize()
        )
        Column(
            modifier = modifier
                .fillMaxSize()
        ) {
            components
        }
    }
}
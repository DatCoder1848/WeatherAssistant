package com.example.weather_assistant;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.weather_assistant.fragment.WeatherNowFragment;

public class MainActivity extends AppCompatActivity {
    ViewPager2 viewPager;
    WeatherPagerAdapter adapter;

    WeatherNowFragment fragment1;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = findViewById(R.id.viewPager);
        adapter = new WeatherPagerAdapter(this);
        viewPager.setAdapter(adapter);
    }


}
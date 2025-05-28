package com.example.weather_assistant;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.weather_assistant.fragment.WeatherNowFragment;

public class WeatherPagerAdapter extends FragmentStateAdapter
{
    WeatherPagerAdapter (FragmentActivity activity)
    {
        super(activity);
    }

    WeatherNowFragment fragment1 = new WeatherNowFragment();
    @NonNull
    @Override
    public Fragment createFragment (int position)
    {
        switch (position)
        {
            case 0:
                return fragment1;
            default:
                return fragment1;
        }
    }

    @Override
    public int getItemCount()
    {
        return 1;
    }
}

package com.example.weather_assistant.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import okhttp3.*;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.weather_assistant.R;
import com.example.weather_assistant.common.Location;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URLEncoder;
import java.sql.Time;


public class WeatherNowFragment extends Fragment
{
    public WeatherNowFragment() {}

    RelativeLayout rootContainer;
    EditText edtLocation;
    Button btnCurrentLocation;
    ImageView ivMainIcon;
    TextView tvError, tvDayTitle, tvWeatherStatus, tvTemp, tvTempMax, tvTempMin, tvWeekDay, tvDate;
    TextView tvHumidity, tvWind, tvPressure, tvSunrise, tvSunset, tvUv;

    String sampleLocations [][] = {
            {"An Giang", "10.7000,105.1167"},
            {"Ba Ria Vung Tau", "10.5833,107.2500"},
            {"Bac Giang", "21.3333,106.3333"},
            {"Bac Kan", "22.1667,105.8333"},
            {"Bac Lieu", "9.2833,105.7167"},
            {"Bac Ninh", "21.0833,106.1667"},
            {"Ben Tre", "10.2333,106.3833"},
            {"Binh Dinh", "14.1667,109.0000"},
            {"Binh Duong", "11.1667,106.6667"},
            {"Binh Phuoc", "11.7500,106.9167"},
            {"Binh Thuan", "10.9333,108.1000"},
            {"Ca Mau", "9.1833,105.1500"},
            {"Can Tho", "10.0333,105.7833"},
            {"Cao Bang", "22.6667,106.2500"},
            {"Da Nang", "16.0667,108.2167"},
            {"Dak Lak", "12.6667,108.0333"},
            {"Dak Nong", "12.0000,107.6667"},
            {"Dien Bien", "21.3833,103.0167"},
            {"Dong Nai", "10.9500,106.8167"},
            {"Dong Thap", "10.4500,105.6333"},
            {"Gia Lai", "13.9833,108.0000"},
            {"Ha Giang", "22.8333,104.9833"},
            {"Ha Nam", "20.5833,105.9167"},
            {"Ha Noi", "21.0333,105.8500"},
            {"Ha Tinh", "18.3333,105.9000"},
            {"Hai Duong", "20.9333,106.3167"},
            {"Hai Phong", "20.8500,106.6833"},
            {"Hau Giang", "9.7833,105.4667"},
            {"Ho Chi Minh", "10.7667,106.6667"},
            {"Hoa Binh", "20.8167,105.3333"},
            {"Hung Yen", "20.6667,106.0667"},
            {"Khanh Hoa", "12.2500,109.1833"},
            {"Kien Giang", "10.0167,105.0833"},
            {"Kon Tum", "14.3500,108.0000"},
            {"Lai Chau", "22.3833,103.4500"},
            {"Lam Dong", "11.9500,108.4333"},
            {"Lang Son", "21.8333,106.7667"},
            {"Lao Cai", "22.4833,103.9500"},
            {"Long An", "10.5333,106.4000"},
            {"Nam Dinh", "20.4167,106.1667"},
            {"Nghe An", "19.3333,105.6667"},
            {"Ninh Binh", "20.2500,105.9833"},
            {"Ninh Thuan", "11.5667,108.9833"},
            {"Phu Tho", "21.3167,105.2167"},
            {"Phu Yen", "13.0833,109.3000"},
            {"Quang Binh", "17.4667,106.6167"},
            {"Quang Nam", "15.5333,108.4833"},
            {"Quang Ngai", "15.1167,108.8000"},
            {"Quang Ninh", "21.1167,107.3000"},
            {"Quang Tri", "16.7500,107.2000"},
            {"Soc Trang", "9.6000,105.9667"},
            {"Son La", "21.3167,103.9000"},
            {"Tay Ninh", "11.3167,106.1000"},
            {"Thai Binh", "20.4500,106.3333"},
            {"Thai Nguyen", "21.5833,105.8333"},
            {"Thanh Hoa", "19.8000,105.7667"},
            {"Thua Thien Hue", "16.4667,107.5833"},
            {"Tien Giang", "10.3667,106.3333"},
            {"Tra Vinh", "9.9333,106.3500"},
            {"Tuyen Quang", "21.8167,105.2167"},
            {"Vinh Long", "10.2500,105.9667"},
            {"Vinh Phuc", "21.3000,105.6000"},
            {"Yen Bai", "21.7000,104.8667"},
            {"Nga", "74.383891,108.554455"},
            {"Mount Snow", "42.9578,-72.9200"},
            {"LangTang", "28.2116,85.5560"}
    };


    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.weather_now, container, false);

        // Truy cap den cac thanh phan tren xml:
        rootContainer = rootView.findViewById(R.id.wthr_now_root_container);
        edtLocation = rootView.findViewById(R.id.edt_location);
        tvError = rootView.findViewById(R.id.tv_error_text);
        btnCurrentLocation = rootView.findViewById(R.id.btn_current_location);
        ivMainIcon = rootView.findViewById(R.id.iv_main_icon);
        tvWeatherStatus = rootView.findViewById(R.id.tv_weather_status);
        tvDayTitle = rootView.findViewById(R.id.tv_day_title);
        tvTemp = rootView.findViewById(R.id.tv_temp);
        tvTempMax = rootView.findViewById(R.id.tv_temp_max);
        tvTempMin = rootView.findViewById(R.id.tv_temp_min);
        tvWeekDay = rootView.findViewById(R.id.tv_week_day);
        tvDate = rootView.findViewById(R.id.tv_date);

        tvHumidity = rootView.findViewById(R.id.tv_humidity);
        tvWind = rootView.findViewById(R.id.tv_wind);
        tvPressure = rootView.findViewById(R.id.tv_pressure);
        tvSunrise = rootView.findViewById(R.id.tv_sunrise);
        tvSunset = rootView.findViewById(R.id.tv_sunset);
        tvUv = rootView.findViewById(R.id.tv_uv);

        // Lay dia chi nguoi dung:
        edtLocation.setOnEditorActionListener((v, actionId, event) -> {
            String coordinate = searchCoordinate(edtLocation.getText().toString().trim());
            if (coordinate == null)
            {
                tvError.setText("Không tìm thấy tọa độ của khu vực bạn tìm kiếm, vui lòng nhập lại !");
                tvError.setVisibility(View.VISIBLE);
            }
            else
            {
                tvError.setVisibility(View.GONE);
                fetchWeather(coordinate);
            }
           return true;
        });

        return rootView;
    }

    private String searchCoordinate (String location)
    {
        for (int nameLocation = 0; nameLocation < 66; nameLocation++)
        {
            if (sampleLocations[nameLocation][0].toLowerCase().equals(location.toLowerCase()))
                return sampleLocations[nameLocation][1];
        }
        return null;
    }
    private void fetchWeather (String coordinate)
    {
        // Tao client:
        OkHttpClient client = new OkHttpClient();
        // Tao request:
            // Truoc khi tao request can co url
                // Truoc khi tao url, can chuan hoa ma hoa coordinate:
        String encodedCoor = "";
        try
        {
            encodedCoor = URLEncoder.encode(coordinate, "UTF-8");
        }
        catch (Exception e)
        {
            e.printStackTrace();
            requireActivity().runOnUiThread(() -> {
                tvError.setText("Mã hóa tọa độ lỗi !");
                tvError.setVisibility(View.VISIBLE);
            });
        }
            // Tao url:
        String url = "https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/" + encodedCoor + "?unitGroup=metric&key=JXNAM6RCU3LPJJYCB9SWRBC2L";
        Request request = new Request.Builder().url(url).build();
        // Gui Request:
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
                requireActivity().runOnUiThread(() -> {
                    tvError.setText("Lỗi kết nối mạng !");
                    tvError.setVisibility(View.VISIBLE);
                });
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                // Chuyen data thanh String:
                String responseBody = response.body() != null ? response.body().string() : "";
                // Kiem tra Response:
                if(response.isSuccessful())
                {
                    // Catch loi neu khong truy xuat duoc data:
                    try
                    {
                        // Chuyen response tro thanh json object:
                        JSONObject json = new JSONObject(responseBody);

                        // Lay thoi tiet hien tai:
                        JSONObject currentWeather = json.getJSONObject("currentConditions");
                        JSONArray days = json.getJSONArray("days");
                        JSONObject today = days.getJSONObject(0);

                        // Lay du lieu:
                        String currentLocation = json.getString("timezone");
                        String status = currentWeather.getString("icon").replace('-', '_');
                        String icon = "main_icon_" + status;
                        String background = "bg_" + status;
                        Double temp = currentWeather.getDouble("temp");
                        Double tempmax = today.getDouble("tempmax");
                        Double tempmin = today.getDouble("tempmin");

                        Double humidity = currentWeather.getDouble("humidity");
                        Double windspeed = currentWeather.getDouble("windspeed");
                        Integer pressure = currentWeather.getInt("pressure");
                        String sunrise = currentWeather.getString("sunrise");
                        String sunset = currentWeather.getString("sunset");
                        Integer uvindex = currentWeather.getInt("uvindex");

                        // Cap nhat du lieu:
                        requireActivity().runOnUiThread(() -> {
                            btnCurrentLocation.setText(currentLocation);
                            // Xu li main icon:
                            int resId = getResources().getIdentifier(icon, "drawable", requireContext().getPackageName());
                            ivMainIcon.setImageResource(resId);
                            // Xu li background:
                            resId = getResources().getIdentifier(background, "drawable", requireContext().getPackageName());
                            rootContainer.setBackgroundResource(resId);
                            tvWeatherStatus.setText(status);
                            tvTemp.setText(String.format("%.1f"+"°C", temp));
                            tvTempMax.setText(String.format("%.1f"+"°C", tempmax));
                            tvTempMin.setText(String.format("%.1f"+"°C", tempmin));
                            tvHumidity.setText(String.format("%.1f"+"%%", humidity));
                            tvWind.setText(String.format("%.1f"+"km/h", windspeed));
                            tvPressure.setText(String.format("%d"+" hPa", pressure));
                            tvSunrise.setText(sunrise);
                            tvSunset.setText(sunset);
                            tvUv.setText(String.format("%d", uvindex));
                        });
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                        requireActivity().runOnUiThread(() -> {
                            tvError.setText("Lỗi truy xuất data !");
                            tvError.setVisibility(View.VISIBLE);
                        });
                    }
                }
                else {
                    String errorText = responseBody != null ? responseBody : "Lỗi không xác định !";
                    tvError.setText("Lỗi API " + response.code() + ": " + errorText);
                    tvError.setVisibility(View.VISIBLE);
                }
            }
        });
    }
}

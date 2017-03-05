package com.example.gtr.sevenweather;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.gtr.sevenweather.bean.Forecast;
import com.example.gtr.sevenweather.bean.Weather;
import com.example.gtr.sevenweather.service.AutoUpdateService;
import com.example.gtr.sevenweather.util.HttpUtil;
import com.example.gtr.sevenweather.util.Utility;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class WeatherActivity extends AppCompatActivity {

    private ScrollView weatherLayout;
    private TextView titleCity;
//    private TextView titleUpdateTime;
    private TextView degreeText;
    private TextView weatherInfoText;
    private LinearLayout forecastLayout;
    private TextView aqiText;
    private TextView pm25Text;
    private TextView comfortText;
    private TextView carWashText;
    private TextView sportText;
    private ImageView bingPicImg;
    public SwipeRefreshLayout swipeRefresh;
    public DrawerLayout drawerLayout;
    private Button btnNav;
    private String weatherId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //让背景栏和状态栏融合到一起
        if (Build.VERSION.SDK_INT >= 21){
            View decorView = getWindow().getDecorView(); //获取当前活动的DecorView
            /*
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE Level 16
            保持整个View稳定, 常跟bar 悬浮, 隐藏共用, 使View不会因为SystemUI的变化而做layout

            View.SYSTEM_UI_FLAG_FULLSCREEN Level 16
            状态栏隐藏

            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN Level 16
            状态栏上浮于Activity

            View.SYSTEM_UI_FLAG_HIDE_NAVIGATION Level 14
            隐藏导航栏,
            */
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE); //表示活动的布局会显示在状态栏上面
            getWindow().setStatusBarColor(Color.TRANSPARENT); //将状态栏设置成透明色
        }

        setContentView(R.layout.activity_weather);
        //初始化各控件
        bingPicImg = (ImageView) findViewById(R.id.bing_pic_img);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        swipeRefresh = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        weatherLayout = (ScrollView) findViewById(R.id.weather_layout);
        titleCity = (TextView) findViewById(R.id.title_city);
        btnNav = (Button) findViewById(R.id.btn_nav);
//        titleUpdateTime = (TextView) findViewById(R.id.title_update_time);
        degreeText = (TextView) findViewById(R.id.degree_text);
        weatherInfoText = (TextView) findViewById(R.id.weather_info_text);
        forecastLayout = (LinearLayout) findViewById(R.id.forecast_layout);
        aqiText = (TextView) findViewById(R.id.aqi_text);
        pm25Text = (TextView) findViewById(R.id.pm25_text);
        comfortText = (TextView) findViewById(R.id.comfort_text);
        carWashText = (TextView) findViewById(R.id.car_wash_text);
        sportText = (TextView) findViewById(R.id.sport_text);

        swipeRefresh.setColorSchemeResources(R.color.colorPrimary);
        swipeRefresh.setProgressViewOffset(true, 100, 250);//设置下拉出现小圆圈是否是缩放出现，出现的位置，最大的下拉位置

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String weatherString = prefs.getString("weather", null);
        if (weatherString != null){
            //有缓存时直接解析天气数据
            Weather weather = Utility.handleWeatherResponse(weatherString);
            weatherId = weather.basic.getId();
            showWeatherInfo(weather);
        }else {
            //无缓存时去服务器查询天气
            weatherLayout.setVisibility(View.INVISIBLE);
            weatherId = getIntent().getStringExtra("weather_id");
            requestWeather(weatherId);
        }

        btnNav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                requestWeather(weatherId);
            }
        });

        String bingPic = prefs.getString("bing_pic", null);
        if (bingPic != null){
            Glide.with(this).load(bingPic).into(bingPicImg);
        }else {
            loadBingPic();
        }
    }

    /*
    * 根据天气id请求城市天气信息
    * */
    public void requestWeather(String weatherId) {
        String weatherUrl = "http://guolin.tech/api/weather?cityid="+weatherId+"&key=a08cb26b5680406fa821ec02dfae4287";
        HttpUtil.sendOkHttpRequest(weatherUrl, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefresh.setRefreshing(false);
                        Toast.makeText(WeatherActivity.this,"获取天气信息失败",Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseText = response.body().string();
                final Weather weather = Utility.handleWeatherResponse(responseText);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (weather!=null && "ok".equals(weather.status)){
                            SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(WeatherActivity.this).edit();
                            editor.putString("weather",responseText);
                            editor.apply();
                            showWeatherInfo(weather);
                            Intent intent = new Intent(WeatherActivity.this, AutoUpdateService.class);
                            startService(intent);
                        }else {
                            Toast.makeText(WeatherActivity.this, "获取天气信息失败", Toast.LENGTH_SHORT).show();
                        }
                        swipeRefresh.setRefreshing(false);
                    }
                });
            }
        });
    }

    /*
    * 处理并展示Weather实体类中的数据
    * */
    private void showWeatherInfo(Weather weather) {
        String cityName = weather.basic.getCity();
//        String updateTime = weather.basic.getUpdate().getLoc().split(" ")[1];
        String degree = weather.now.getTmp() + "℃";
        String weatherInfo = weather.now.getCond().getTxt();
        List<Forecast> forecastList = weather.forecastList;

        titleCity.setText(cityName);
//        titleUpdateTime.setText(updateTime);
        degreeText.setText(degree);
        weatherInfoText.setText(weatherInfo);
        forecastLayout.removeAllViews();
        for (Forecast forecast : forecastList) {
            View view = LayoutInflater.from(this).inflate(R.layout.forecast_item, forecastLayout, false);
            TextView dateText = (TextView) view.findViewById(R.id.date_text);
            TextView infoText = (TextView) view.findViewById(R.id.info_text);
            TextView minText = (TextView) view.findViewById(R.id.min_text);
            TextView maxText = (TextView) view.findViewById(R.id.max_text);
            dateText.setText(forecast.getDate());
            infoText.setText(forecast.getCond().getTxt_d());
            minText.setText(forecast.getTmp().getMin());
            maxText.setText(forecast.getTmp().getMax());
            forecastLayout.addView(view);
        }

        if (weather.aqi != null){
            aqiText.setText(weather.aqi.getCity().getAqi());
            pm25Text.setText(weather.aqi.getCity().getPm25());
        }
        String comfort = "舒适度："+weather.suggestion.getComf().getTxt();
        String carWash = "洗车指数："+weather.suggestion.getCw().getTxt();
        String sport = "运动建议："+weather.suggestion.getSport().getTxt();
        comfortText.setText(comfort);
        carWashText.setText(carWash);
        sportText.setText(sport);
        weatherLayout.setVisibility(View.VISIBLE);
    }

    private void loadBingPic() {
        String requestBingPic = "http://guolin.tech/api/bing_pic";
        HttpUtil.sendOkHttpRequest(requestBingPic, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String bingPic = response.body().string();
                SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(WeatherActivity.this).edit();
                editor.putString("bing_pic", bingPic);
                editor.apply();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Glide.with(WeatherActivity.this).load(bingPic).into(bingPicImg);
                    }
                });
            }
        });
    }

}

package com.example.gtr.sevenweather.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by GTR on 2017/3/3.
 */

public class Weather {

    public String status;
    public Basic basic;
    public AQI aqi;
    public Now now;
    public Suggestion suggestion;

    @SerializedName("daily_forecast")
    public List<Forecast> forecastList;  //由于daily_forecast中包含的是一个数组，因此这里使用List集合
}

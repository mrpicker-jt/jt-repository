package com.gproject.android.response.vo;

/**
 * Created by 姜腾 on 2017/6/5.
 */

public class Dorm {
    public int brightness;
    public int temperature;
    public int humidity;
    public boolean personStatus;
    public double powerCount;
    public boolean fanStatus;
    public boolean ledStatus;
    public boolean humidifierStatus;
    public AirConditioner airConditioner=new AirConditioner();
}

package com.tianqi.demo.app.domain.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "forecast")
@XmlAccessorType(XmlAccessType.FIELD)
public class Forecast{

    @XmlElement(name = "weather")
    private List<Weather> weather;

    public List<Weather> getWeather() {
        return this.weather;
    }

    public void setWeather(List<Weather> weather) {
        this.weather = weather;
    }

    @Override
    public String toString() {
        return "Forecast{" +
                "weather=" + weather +
                '}';
    }
}
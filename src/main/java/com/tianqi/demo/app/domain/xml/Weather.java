package com.tianqi.demo.app.domain.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "weather")
@XmlAccessorType(XmlAccessType.FIELD)
public class Weather{

    @XmlElement(name = "date")
    private String	date;

    @XmlElement(name = "high")
    private String	high;

    @XmlElement(name = "low")
    private String	low;

    @XmlElement(name = "day")
    private Day	day;

    @XmlElement(name = "night")
    private Night	night;

    public String getDate() {
        return this.date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getHigh() {
        return this.high;
    }

    public void setHigh(String high) {
        this.high = high;
    }

    public String getLow() {
        return this.low;
    }

    public void setLow(String low) {
        this.low = low;
    }

    public Day getDay() {
        return this.day;
    }

    public void setDay(Day day) {
        this.day = day;
    }

    public Night getNight() {
        return this.night;
    }

    public void setNight(Night night) {
        this.night = night;
    }

    @Override
    public String toString() {
        return "Weather{" +
                "date='" + date + '\'' +
                ", high='" + high + '\'' +
                ", low='" + low + '\'' +
                ", day=" + day +
                ", night=" + night +
                '}';
    }
}
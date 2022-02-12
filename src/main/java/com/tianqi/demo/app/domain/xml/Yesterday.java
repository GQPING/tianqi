package com.tianqi.demo.app.domain.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "yesterday")
@XmlAccessorType(XmlAccessType.FIELD)
public class Yesterday{

    @XmlElement(name = "date_1")
    private String	date1;

    @XmlElement(name = "high_1")
    private String	high1;

    @XmlElement(name = "low_1")
    private String	low1;

    @XmlElement(name = "day_1")
    private Day1	day1;

    @XmlElement(name = "night_1")
    private Night1	night1;

    public String getDate1() {
        return this.date1;
    }

    public void setDate1(String date1) {
        this.date1 = date1;
    }

    public String getHigh1() {
        return this.high1;
    }

    public void setHigh1(String high1) {
        this.high1 = high1;
    }

    public String getLow1() {
        return this.low1;
    }

    public void setLow1(String low1) {
        this.low1 = low1;
    }

    public Day1 getDay1() {
        return this.day1;
    }

    public void setDay1(Day1 day1) {
        this.day1 = day1;
    }

    public Night1 getNight1() {
        return this.night1;
    }

    public void setNight1(Night1 night1) {
        this.night1 = night1;
    }

    @Override
    public String toString() {
        return "Yesterday{" +
                "date1='" + date1 + '\'' +
                ", high1='" + high1 + '\'' +
                ", low1='" + low1 + '\'' +
                ", day1=" + day1 +
                ", night1=" + night1 +
                '}';
    }
}
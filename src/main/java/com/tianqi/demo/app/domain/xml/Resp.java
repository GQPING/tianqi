package com.tianqi.demo.app.domain.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "resp")
@XmlAccessorType(XmlAccessType.FIELD)
public class Resp{

    @XmlElement(name = "city")
    private String	city;

    @XmlElement(name = "updatetime")
    private String	updatetime;

    @XmlElement(name = "wendu")
    private String	wendu;

    @XmlElement(name = "fengli")
    private String	fengli;

    @XmlElement(name = "shidu")
    private String	shidu;

    @XmlElement(name = "fengxiang")
    private String	fengxiang;

    @XmlElement(name = "sunrise_1")
    private String	sunrise1;

    @XmlElement(name = "sunset_1")
    private String	sunset1;

    @XmlElement(name = "sunrise_2")
    private String	sunrise2;

    @XmlElement(name = "sunset_2")
    private String	sunset2;

    @XmlElement(name = "yesterday")
    private Yesterday	yesterday;

    @XmlElement(name = "forecast")
    private Forecast	forecast;

    @XmlElement(name = "zhishus")
    private Zhishus	zhishus;

    public String getCity() {
        return this.city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getUpdatetime() {
        return this.updatetime;
    }

    public void setUpdatetime(String updatetime) {
        this.updatetime = updatetime;
    }

    public String getWendu() {
        return this.wendu;
    }

    public void setWendu(String wendu) {
        this.wendu = wendu;
    }

    public String getFengli() {
        return this.fengli;
    }

    public void setFengli(String fengli) {
        this.fengli = fengli;
    }

    public String getShidu() {
        return this.shidu;
    }

    public void setShidu(String shidu) {
        this.shidu = shidu;
    }

    public String getFengxiang() {
        return this.fengxiang;
    }

    public void setFengxiang(String fengxiang) {
        this.fengxiang = fengxiang;
    }

    public String getSunrise1() {
        return this.sunrise1;
    }

    public void setSunrise1(String sunrise1) {
        this.sunrise1 = sunrise1;
    }

    public String getSunset1() {
        return this.sunset1;
    }

    public void setSunset1(String sunset1) {
        this.sunset1 = sunset1;
    }

    public Yesterday getYesterday() {
        return this.yesterday;
    }

    public void setYesterday(Yesterday yesterday) {
        this.yesterday = yesterday;
    }

    public Forecast getForecast() {
        return this.forecast;
    }

    public void setForecast(Forecast forecast) {
        this.forecast = forecast;
    }

    public Zhishus getZhishus() {
        return this.zhishus;
    }

    public void setZhishus(Zhishus zhishus) {
        this.zhishus = zhishus;
    }

    public String getSunrise2() {
        return sunrise2;
    }

    public void setSunrise2(String sunrise2) {
        this.sunrise2 = sunrise2;
    }

    public String getSunset2() {
        return sunset2;
    }

    public void setSunset2(String sunset2) {
        this.sunset2 = sunset2;
    }

    @Override
    public String toString() {
        return "Resp{" +
                "city='" + city + '\'' +
                ", updatetime='" + updatetime + '\'' +
                ", wendu='" + wendu + '\'' +
                ", fengli='" + fengli + '\'' +
                ", shidu='" + shidu + '\'' +
                ", fengxiang='" + fengxiang + '\'' +
                ", sunrise1='" + sunrise1 + '\'' +
                ", sunset1='" + sunset1 + '\'' +
                ", sunrise2='" + sunrise2 + '\'' +
                ", sunset2='" + sunset2 + '\'' +
                ", yesterday=" + yesterday +
                ", forecast=" + forecast +
                ", zhishus=" + zhishus +
                '}';
    }
}
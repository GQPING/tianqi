package com.tianqi.demo.app.domain.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "day")
@XmlAccessorType(XmlAccessType.FIELD)
public class Day{

    @XmlElement(name = "type")
    private String	type;

    @XmlElement(name = "fengxiang")
    private String	fengxiang;

    @XmlElement(name = "fengli")
    private String	fengli;

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFengxiang() {
        return this.fengxiang;
    }

    public void setFengxiang(String fengxiang) {
        this.fengxiang = fengxiang;
    }

    public String getFengli() {
        return this.fengli;
    }

    public void setFengli(String fengli) {
        this.fengli = fengli;
    }

    @Override
    public String toString() {
        return "Day{" +
                "type='" + type + '\'' +
                ", fengxiang='" + fengxiang + '\'' +
                ", fengli='" + fengli + '\'' +
                '}';
    }
}
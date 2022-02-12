package com.tianqi.demo.app.domain.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "zhishu")
@XmlAccessorType(XmlAccessType.FIELD)
public class Zhishu{

    @XmlElement(name = "name")
    private String	name;

    @XmlElement(name = "value")
    private String	value;

    @XmlElement(name = "detail")
    private String	detail;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDetail() {
        return this.detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    @Override
    public String toString() {
        return "Zhishu{" +
                "name='" + name + '\'' +
                ", value='" + value + '\'' +
                ", detail='" + detail + '\'' +
                '}';
    }
}
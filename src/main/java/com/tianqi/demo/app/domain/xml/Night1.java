package com.tianqi.demo.app.domain.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "night_1")
@XmlAccessorType(XmlAccessType.FIELD)
public class Night1{

    @XmlElement(name = "type_1")
    private String	type1;

    @XmlElement(name = "fx_1")
    private String	fx1;

    @XmlElement(name = "fl_1")
    private String	fl1;

    public String getType1() {
        return this.type1;
    }

    public void setType1(String type1) {
        this.type1 = type1;
    }

    public String getFx1() {
        return this.fx1;
    }

    public void setFx1(String fx1) {
        this.fx1 = fx1;
    }

    public String getFl1() {
        return this.fl1;
    }

    public void setFl1(String fl1) {
        this.fl1 = fl1;
    }

    @Override
    public String toString() {
        return "Night1{" +
                "type1='" + type1 + '\'' +
                ", fx1='" + fx1 + '\'' +
                ", fl1='" + fl1 + '\'' +
                '}';
    }
}
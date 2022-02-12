package com.tianqi.demo.app.domain.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "zhishus")
@XmlAccessorType(XmlAccessType.FIELD)
public class Zhishus{

    @XmlElement(name = "zhishu")
    private List<Zhishu> zhishu;

    public List<Zhishu> getZhishu() {
        return this.zhishu;
    }

    public void setZhishu(List<Zhishu> zhishu) {
        this.zhishu = zhishu;
    }

    @Override
    public String toString() {
        return "Zhishus{" +
                "zhishu=" + zhishu +
                '}';
    }
}
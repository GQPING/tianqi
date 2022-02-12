package com.tianqi.demo.app.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@XmlRootElement(name = "c")
@XmlAccessorType(XmlAccessType.FIELD)
public class CityList {
    /**
     * @XmlRootElement申明Xml的根元素
     * @XmlAttribute申明Xml根元素中的属性名
     * @XmlAccessorType申明访问的类型是字段
     * @XmlElement表示字段映射的Xml中的元素
     */
    @XmlAttribute(name = "c1")
    private String rootId;

    @XmlElement(name = "d")
    private List<City> cityLst;
}
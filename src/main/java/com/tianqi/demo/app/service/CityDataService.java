package com.tianqi.demo.app.service;

import com.tianqi.demo.app.domain.City;

import java.util.List;

/**
 * 测完读取本地资源
 * @author cxw
 */
public interface CityDataService {
    /**
     * 获取城市
     * @return
     * @throws Exception
     */
    List<City> getCityList() throws Exception;
}
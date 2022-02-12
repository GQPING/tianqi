package com.tianqi.demo.app.service.impl;

import com.tianqi.demo.app.domain.City;
import com.tianqi.demo.app.domain.CityList;
import com.tianqi.demo.app.service.CityDataService;
import com.tianqi.demo.utils.UtilFile;
import com.tianqi.demo.utils.UtilXml;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * 测完读取本地资源
 * @author cxw
 */
@Service
public class CityDataServiceImpl implements CityDataService {
    /**
     * 获取城市
     * @return
     * @throws Exception
     */
    @Override
    public List<City> getCityList() throws Exception {
        //返回数据
        CityList result = new CityList();

        //读取XML内容
        String content = UtilFile.read("xml/city.xml");
        if(StringUtils.isNotEmpty(content)) {
            //XML转为JAVA对象
            result = (CityList) UtilXml.xmlStrToObject(CityList.class, content);
        }

        return result.getCityLst();
    }
}
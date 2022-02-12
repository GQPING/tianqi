package com.tianqi.demo.app.controller;

import com.alibaba.fastjson.JSONObject;
import com.tianqi.demo.app.service.CityDataService;
import com.tianqi.demo.utils.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author cxw
 * @date 2022/2/10
 */
@Slf4j
@RestController
@Api(tags = {"天气接口测试"})
public class TestController {

    @Autowired
    private CityDataService cityDataService;

    @GetMapping("getJson1")
    @ApiOperation(value = "获取Json天气数据")
    private PfResponse getJson1(@ApiParam("省份") @RequestParam(defaultValue = "河南") String province,
                                @ApiParam("城市") @RequestParam(defaultValue = "郑州") String city,
                                @ApiParam("县区") @RequestParam(defaultValue = "中原") String country) {
        try {
            Map<String, String> params = new LinkedHashMap<>();
            params.put("source", "pc");
            params.put("province", province);
            params.put("city", city);
            params.put("country", country);
            params.put("weather_type", "observe");
            String jsonString = HttpUtils.sendGet("https://wis.qq.com/weather/common", params);
            JSONObject jsonObject = JSONObject.parseObject(jsonString);
            return PfResponse.success(jsonObject.get("data"));
        } catch (Exception e) {
            e.printStackTrace();
            return PfResponse.error("异常：" + e.getMessage());
        }
    }

    @GetMapping("getJson2")
    @ApiOperation(value = "获取Json天气数据")
    private PfResponse getJson2(@ApiParam("省份") @RequestParam(defaultValue = "河南") String province,
                                @ApiParam("城市") @RequestParam(defaultValue = "郑州") String city,
                                @ApiParam("县区") @RequestParam(defaultValue = "中原") String country) {
        try {
            Map<String, String> params = new LinkedHashMap<>();
            params.put("source", "pc");
            params.put("province", province);
            params.put("city", city);
            params.put("country", country);
            params.put("weather_type", "observe");
            HttpResponse response = HttpUtil.doGet("https://wis.qq.com", "/weather/common",
                    "get", null, params);
            if (response.getStatusLine().getStatusCode() == 200) {
                //将获取的结果转换为json字符串形式
                String jsonString = EntityUtils.toString(response.getEntity());
                JSONObject jsonObject = JSONObject.parseObject(jsonString);
                return PfResponse.success(jsonObject.get("data"));
            } else {
                return PfResponse.error("访问失败" + response.getStatusLine().getStatusCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return PfResponse.error("异常：" + e.getMessage());
        }
    }

    @GetMapping("getJson3")
    @ApiOperation(value = "获取Json天气数据")
    private PfResponse getJson3(@ApiParam("省份") @RequestParam(defaultValue = "河南") String province,
                                @ApiParam("城市") @RequestParam(defaultValue = "郑州") String city,
                                @ApiParam("县区") @RequestParam(defaultValue = "中原") String country) {
        try {
            // 客户端
            RestTemplate client = new RestTemplate();

//            //第一种
//            String url = "https://wis.qq.com/weather/common/{source}/{province}/{city}/{country}/{weather_type}";
//            Map<String, String> params = new HashMap<>();
//            params.put("source", "pc");
//            params.put("province", province);
//            params.put("city", city);
//            params.put("country", country);
//            params.put("weather_type", "observe");
//            //将获取的结果转换为json字符串形式
//            String jsonString = client.getForObject(url, String.class, params);

            //第2种
            String url = "https://wis.qq.com/weather/common?source=pc&province=河南&city=郑州&country=中原&weather_type=observe";
//            //第3种
//            String url = "https://wis.qq.com/weather/common?source=pc&province="
//                    + URLEncoder.encode(province , "UTF-8")
//                    + "&city=" + URLEncoder.encode(city, "UTF-8")
//                    + "&country=" + URLEncoder.encode(country, "UTF-8") + "&weather_type=observe";

            String jsonString = client.getForObject(url, String.class);
            JSONObject jsonObject = JSONObject.parseObject(jsonString);
            return PfResponse.success(jsonObject.get("data"));
        } catch (Exception e) {
            e.printStackTrace();
            return PfResponse.error("异常：" + e.getMessage());
        }
    }

    @GetMapping("getJson4")
    @ApiOperation(value = "获取Json天气数据")
    private PfResponse getJson4(@ApiParam("省份") @RequestParam(defaultValue = "河南") String province,
                                @ApiParam("城市") @RequestParam(defaultValue = "郑州") String city,
                                @ApiParam("县区") @RequestParam(defaultValue = "中原") String country) {
        try {
            //第一种
            String url = "https://wis.qq.com/weather/common";
            Map<String, String> params = new HashMap<>();
            params.put("source", "pc");
            params.put("province", province);
            params.put("city", city);
            params.put("country", country);
            params.put("weather_type", "observe");
            //将获取的结果转换为json字符串形式
            String jsonString = UtilHttps.doGet(url, params, String.class);
            JSONObject jsonObject = JSONObject.parseObject(jsonString);
            return PfResponse.success(jsonObject.get("data"));
        } catch (Exception e) {
            e.printStackTrace();
            return PfResponse.error("异常：" + e.getMessage());
        }
    }

    @GetMapping("readXml")
    @ApiOperation(value = "读取本地Xml数据")
    private PfResponse readXml() {
        try {
            return PfResponse.success(cityDataService.getCityList());
        } catch (Exception e) {
            e.printStackTrace();
            return PfResponse.error("异常：" + e.getMessage());
        }
    }

    @GetMapping("getXml1")
    @ApiOperation(value = "获取Xml天气数据")
    private PfResponse getXml1(@ApiParam("城市名称") @RequestParam(defaultValue = "兰考县") String city) throws Exception {
        Map<String, String> params = new LinkedHashMap<>();
        params.put("city", city);
        Map<String,String> headers = new LinkedHashMap<>();
        headers.put("Accept-Encoding","gzip,deflate;");
        headers.put("Accept-Language","zh-CN,zh;q=0.9,en;q=0.8");
        headers.put("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9");
        HttpResponse response = HttpUtil.doGet("http://wthrcdn.etouch.cn", "/WeatherApi","get", headers, params);
        if (response.getStatusLine().getStatusCode() == 200) {
            //将获取的结果转换为json字符串形式
            String jsonString = EntityUtils.toString(response.getEntity());
            try {
                Document document = DocumentHelper.parseText(jsonString);
                Element rootElement = document.getRootElement();
                Map<String, String> map = new LinkedHashMap<>();

                String cityName = rootElement.elementText("city");
                map.put("城市", cityName);
                String updatetime = rootElement.elementText("updatetime");
                map.put("更新时间", cityName);
                String wendu = rootElement.elementText("wendu");
                map.put("温度", cityName);

                String shidu = rootElement.elementText("shidu");
                map.put("湿度", cityName);
                String fengxiang = rootElement.elementText("fengxiang");
                map.put("风向", cityName);
                String sunrise_1 = rootElement.elementText("sunrise_1");
                map.put("日出", cityName);
                String sunset_1 = rootElement.elementText("sunset_1");
                map.put("日落", cityName);

                //昨日
                Element yesterday = rootElement.element("yesterday");
                List<Element> yesterdayList = yesterday.elements();
                if (yesterdayList != null) {
                    Element element = yesterdayList.get(0);
                    map.put("昨天", element.elementText("date_1"));
                    map.put("最高温度", element.elementText("high_1"));
                    map.put("最低温度", element.elementText("low_1"));
                }

                //预测
                Element forecast = rootElement.element("forecast");
                List<Element> forecastList = forecast.elements();
                if (forecastList != null) {
                    //预测4天的数据
                }

                return PfResponse.success(map);
            } catch (DocumentException e) {
                e.printStackTrace();
                return PfResponse.error("异常：" + e.getMessage());
            }
        }else{
            return PfResponse.error("访问失败" + response.getStatusLine().getStatusCode());
        }
    }

    @GetMapping("getXml2")
    @ApiOperation(value = "获取Xml天气数据")
    private PfResponse getXml2(@ApiParam("城市拼音") @RequestParam(defaultValue = "kaifeng") String city) throws MalformedURLException {
        String path = "https://flash.weather.com.cn/wmaps/xml/" + city + ".xml";
        URL url = new URL(path);
        try {
            Document document = UtilXml.parse(url);
            Element rootElement = document.getRootElement();
            Map<String, Object> result = new LinkedHashMap<>();
            //所有市县
            List<Element> elements = rootElement.elements();
            if (elements != null) {
                for(Element element : elements){
                    Map<String,String> data = new HashMap<>();
                    data.put("城市", element.elementText("cityname"));
                    data.put("最高温度", element.elementText("tem1"));
                    data.put("最低温度", element.elementText("tem2"));
                    data.put("数据时间", element.elementText("time"));
                    result.put(element.elementText("cityname"),data);
                }
            }
            return PfResponse.success(result);
        } catch (DocumentException e) {
            e.printStackTrace();
            return PfResponse.error("异常：" + e.getMessage());
        }
    }
}

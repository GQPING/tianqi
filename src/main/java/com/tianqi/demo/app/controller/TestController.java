package com.tianqi.demo.app.controller;

import com.alibaba.fastjson.JSONObject;
import com.tianqi.demo.app.domain.xml.Resp;
import com.tianqi.demo.app.service.CityDataService;
import com.tianqi.demo.utils.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.impl.client.DefaultHttpClient;
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

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPInputStream;

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

    /**
     * http解压缩字符串
     * @param response
     * @return
     */
    private String getJsonStringFromGZIP(HttpResponse response) {
        String jsonString = null;
        try {
            InputStream is = response.getEntity().getContent();
            BufferedInputStream bis = new BufferedInputStream(is);
            bis.mark(2);
            // 取前两个字节
            byte[] header = new byte[2];
            int result = bis.read(header);
            // reset输入流到开始位置
            bis.reset();
            // 判断是否是GZIP格式
            int headerData = getShort(header);
            if (result != -1 && headerData == 0x1f8b) {
                is = new GZIPInputStream(bis);
            } else {
                is = bis;
            }
            InputStreamReader reader = new InputStreamReader(is, "utf-8");
            char[] data = new char[100];
            int readSize;
            StringBuffer sb = new StringBuffer();
            while ((readSize = reader.read(data)) > 0) {
                sb.append(data, 0, readSize);
            }
            jsonString = sb.toString();
            bis.close();
            reader.close();
        } catch (Exception e) {
            log.error("HttpTask", e.toString(), e);
        }
        return jsonString;
    }

    /**
     * 是否是GZIP格式
     * @param data
     * @return
     */
    private int getShort(byte[] data) {
        return (int) ((data[0] << 8) | data[1] & 0xFF);
    }

    @GetMapping("getXml1")
    @ApiOperation(value = "获取Xml天气数据")
    private PfResponse getXml1(@ApiParam("城市名称") @RequestParam(defaultValue = "兰考县") String city) throws Exception {
        //第一种
        Map<String, String> params = new LinkedHashMap<>();
        params.put("city", city);
        HttpResponse response = HttpUtil.doGet("http://wthrcdn.etouch.cn", "/WeatherApi",
                "get", null, params);
        if (response.getStatusLine().getStatusCode() == 200) {
            try {
                // 获取到解压缩之后的字符串
                String jsonXml = getJsonStringFromGZIP(response);
                Resp content = (Resp)UtilXml.xmlStrToObject(Resp.class,jsonXml);
                return PfResponse.success(content);
            } catch (DocumentException e) {
                e.printStackTrace();
                return PfResponse.error("异常：" + e.getMessage());
            }
        } else {
            return PfResponse.error("访问失败" + response.getStatusLine().getStatusCode());
        }
    }
}

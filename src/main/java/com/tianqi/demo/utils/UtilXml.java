package com.tianqi.demo.utils;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.io.SAXReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.Reader;
import java.io.StringReader;
import java.net.URL;

/**
 * Xml 操作工具
 * @author cxw
 * @date 2022/2/10
 */
public class UtilXml {
    /**
     * 解析远程 XML 文件
     * @return Document xml 文档
     * */
    public static Document parse(URL url) throws DocumentException {
        SAXReader reader = new SAXReader();
        Document document =reader.read(url);
        return document;
    }

    /**
     * 解析远程 XML 文件
     * @param url
     * @return
     * @throws DocumentException
     */
    public static Document getDoc(String url) throws DocumentException {
        String jsonString = HttpUtils.sendGet(url);
        return DocumentHelper.parseText(jsonString);
    }

    /**
     * 将XML转为指定的POJO对象
     *
     * @param clazz  需要转换的类
     * @param xmlStr xml数据
     * @return
     */
    public static Object xmlStrToObject(Class<?> clazz, String xmlStr) throws Exception {
        Object xmlObject = null;
        Reader reader = null;
        //利用JAXBContext将类转为一个实例
        JAXBContext context = JAXBContext.newInstance(clazz);
        //XMl 转为对象的接口
        Unmarshaller unmarshaller = context.createUnmarshaller();
        reader = new StringReader(xmlStr);
        xmlObject = unmarshaller.unmarshal(reader);
        if (reader != null) {
            reader.close();
        }
        return xmlObject;
    }
}

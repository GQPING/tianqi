package com.tianqi.demo.utils;

import com.mzlion.easyokhttp.HttpClient;
import com.mzlion.easyokhttp.request.GetRequest;
import com.mzlion.easyokhttp.request.PostRequest;
import com.mzlion.easyokhttp.request.TextBodyRequest;
import com.mzlion.easyokhttp.response.callback.Callback;
import com.mzlion.easyokhttp.response.handle.DataHandler;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.zip.GZIPInputStream;

public class UtilHttps {

    private static Logger logger = LoggerFactory.getLogger(UtilHttps.class);

    //PUT请求-------------------------------------------------------------------------**

    /**
     * @param url        请求的url地址
     * @param headers    请求头参数map
     * @param parameters 请求参数map
     * @return 请求的结果字符串
     * 发起PUT同步请求
     */
    public static String doPut(String url, Map<String, String> headers, Map<String, String> parameters, String jsonStr) throws UnsupportedEncodingException {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpPut httpPut = new HttpPut(url);
        addParameters(httpPut, parameters, jsonStr);
        return getResultStr(headers, httpClient, httpPut);
    }

    private static void addParameters(HttpEntityEnclosingRequestBase postPutRequest, Map<String, String> parameters, String jsonStr) throws UnsupportedEncodingException, UnsupportedEncodingException {
        if (!StringUtils.isEmpty(jsonStr)) {
            postPutRequest.setHeader("Content-Type", "application/json;charset=UTF-8");
            postPutRequest.setEntity(new StringEntity(jsonStr, Charset.forName("UTF-8")));
        } else {
            // 1、构造list集合，往里面存请求的数据
            List<NameValuePair> list = new ArrayList<>();
            for (String key : parameters.keySet()) {
                String value = parameters.get(key);
                BasicNameValuePair basicNameValuePair = new BasicNameValuePair(key, value);
                list.add(basicNameValuePair);
            }
            //2 我们发现Entity是一个接口，所以只能找实现类，发现实现类又需要一个集合，集合的泛型是NameValuePair类型
            UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(list);
            //3 通过setEntity 将我们的entity对象传递过去
            postPutRequest.setEntity(formEntity);
        }

    }

    private static String getResultStr(Map<String, String> headers, CloseableHttpClient httpClient, HttpRequestBase httpRequest) {
        String body = null;
        CloseableHttpResponse response;
        //设置请求头的参数
        setHeaders(headers, httpRequest);
        try {
            //执行请求
            response = httpClient.execute(httpRequest);
            //请求成功执行
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                //获取返回的数据
                HttpEntity entity = response.getEntity();
                //转换成字符串
                body = EntityUtils.toString(entity);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return body;
    }

    private static void setHeaders(Map<String, String> headers, HttpRequestBase httpRequest) {
        if (headers != null && !headers.isEmpty()) {
            for (String key : headers.keySet()) {
                String value = headers.get(key);
                httpRequest.setHeader(key, value);
            }
        }
    }

    //DELETE请求-------------------------------------------------------------------------**

    /**
     * @author Binean
     * @Description 发送delete请求
     */
    public static String doDelete(String url, Map<String, String> headers, String encode) {
        if (encode == null) {
            encode = "utf-8";
        }
        String content = null;
        CloseableHttpClient closeableHttpClient = HttpClientBuilder.create().build();
        HttpDelete httpdelete = new HttpDelete(url);
        //设置header
        if (headers != null && headers.size() > 0) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                httpdelete.setHeader(entry.getKey(), entry.getValue());
            }
        }
        CloseableHttpResponse httpResponse = null;
        try {
            httpResponse = closeableHttpClient.execute(httpdelete);
            HttpEntity entity = httpResponse.getEntity();
            content = EntityUtils.toString(entity, encode);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                httpResponse.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {   //关闭连接、释放资源
            closeableHttpClient.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content;
    }


	//各种GET请求-------------------------------------------------------------------------**

    public static <T> T doGet(String url, Map<String, String> paramMap, Map<String, String> headMap, Class<T> clz) {
        try {
            GetRequest request = HttpClient.get(url);
            if (paramMap != null && !paramMap.isEmpty()) {
                request = request.queryString(paramMap);
            }
            if (headMap != null && !headMap.isEmpty()) {
                for (Entry<String, String> item : headMap.entrySet()) {
                    request = request.header(item.getKey(), item.getValue());
                }
            }
            request.header("Connection", "close");
            T result = null;
            if (clz.equals(String.class)) {
                result = (T) request.execute().asString();
            } else {
                result = request.execute().asBean(clz);
            }
            return result;
        } catch (Exception e) {
            logger.error(e.getMessage());
            return null;
        }
    }

    public static <T> T doGet(String url, Map<String, String> paramMap, Map<String, String> headMap, Class<T> clz,
                              int timeout) {
        try {
            GetRequest request = HttpClient.get(url).readTimeout(timeout);
            if (paramMap != null && !paramMap.isEmpty()) {
                request = request.queryString(paramMap);
            }
            if (headMap != null && !headMap.isEmpty()) {
                for (Entry<String, String> item : headMap.entrySet()) {
                    request = request.header(item.getKey(), item.getValue());
                }
            }
            request.header("Connection", "close");
            T result = null;
            if (clz.equals(String.class)) {
                result = (T) request.execute().asString();
            } else {
                result = request.execute().asBean(clz);
            }
            return result;
        } catch (Exception e) {
            logger.error(e.getMessage());
            return null;
        }
    }

    public static String doUpzipGet(String url, Map<String, String> paramMap, Map<String, String> headMap,
                                    int timeout) {
        try {
            GetRequest request = HttpClient.get(url).readTimeout(timeout);
            if (paramMap != null && !paramMap.isEmpty()) {
                request = request.queryString(paramMap);
            }
            if (headMap != null && !headMap.isEmpty()) {
                for (Entry<String, String> item : headMap.entrySet()) {
                    request = request.header(item.getKey(), item.getValue());
                }
            }

            request.header("Connection", "close");
            String result = null;
            result = request.execute().custom(new DataHandler<String>() {

                @Override
                public String handle(Response response) throws IOException {
                    // TODO Auto-generated method stub
                    String rs = null;
                    GZIPInputStream gis = null;
                    ByteArrayOutputStream baos = null;
                    try {
                        if ("gzip".equals(response.header("Content-Encoding"))) {
                            byte[] buffer = new byte[4096];
                            int size = 0;
                            baos = new ByteArrayOutputStream();
                            gis = new GZIPInputStream(response.body().byteStream());
                            while ((size = gis.read(buffer)) != -1) {
                                baos.write(buffer, 0, size);
                            }
                            Charset cs = java.nio.charset.Charset.forName("UTF-8");
                            rs = new String(baos.toByteArray(), cs);
                        } else {
                            rs = response.body().string();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (gis != null) {
                        gis.close();
                    }
                    if (baos != null) {
                        baos.close();
                    }
                    return rs;
                }
            });
            return result;
        } catch (Exception e) {
            logger.error(e.getMessage());
            return null;
        }
    }

    public static String doGet(String url, Map<String, String> paramMap) {
        try {
            return doGet(url, paramMap, null, String.class);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return null;
        }
    }

    public static <T> void doAsyncGet(String url, Map<String, String> paramMap, Map<String, String> headMap,
                                      Callback<T> callback) {
        try {
            GetRequest request = HttpClient.get(url);
            if (paramMap != null && !paramMap.isEmpty()) {
                request = request.queryString(paramMap);
            }
            if (headMap != null && !headMap.isEmpty()) {
                for (Entry<String, String> item : headMap.entrySet()) {
                    request = request.header(item.getKey(), item.getValue());
                }
            }
            request.header("Connection", "close");
            request.execute(callback);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    public static String doGet(String url, Map<String, String> paramMap, int timeout) {
        try {
            return doGet(url, paramMap, null, String.class, timeout);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return null;
        }
    }

    public static <T> T doGet(String url, Map<String, String> paramMap, Class<T> clz) {
        return doGet(url, paramMap, null, clz);
    }

	//各种POST请求-------------------------------------------------------------------------**

    public static <T> void doAsyncPost(String url, Map<String, String> paramMap, Map<String, String> headMap,
                                       Callback<T> callback) {
        try {
            PostRequest request = HttpClient.post(url);
            if (paramMap != null && !paramMap.isEmpty()) {
                request = request.param(paramMap);
            }
            if (headMap != null && !headMap.isEmpty()) {
                for (Entry<String, String> item : headMap.entrySet()) {
                    request = request.header(item.getKey(), item.getValue());
                }
            }
            request.header("Connection", "close");
            request.execute(callback);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    public static <T> T doPost(String url, Map<String, String> paramMap, Map<String, String> headMap, Class<T> clz) {
        try {
            PostRequest request = HttpClient.post(url);
            if (paramMap != null && !paramMap.isEmpty()) {
                request = request.param(paramMap);
            }
            if (headMap != null && !headMap.isEmpty()) {
                for (Entry<String, String> item : headMap.entrySet()) {
                    request = request.header(item.getKey(), item.getValue());
                }
            }
            request.header("Connection", "close");
            T result = null;
            if (clz.equals(String.class)) {
                result = (T) request.execute().asString();
            } else {
                result = request.execute().asBean(clz);
            }
            return result;
        } catch (Exception e) {
            logger.error(e.getMessage());
            return null;
        }
    }

    public static <T> T doPost(String url, Map<String, String> paramMap, Map<String, String> headMap, Class<T> clz,
                               int timeout) {
        try {
            PostRequest request = HttpClient.post(url).readTimeout(timeout);
            if (paramMap != null && !paramMap.isEmpty()) {
                request = request.param(paramMap);
            }
            if (headMap != null && !headMap.isEmpty()) {
                for (Entry<String, String> item : headMap.entrySet()) {
                    request = request.header(item.getKey(), item.getValue());
                }
            }
            request.header("Connection", "close");
            T result = null;
            if (clz.equals(String.class)) {
                result = (T) request.execute().asString();
            } else {
                result = request.execute().asBean(clz);
            }
            return result;
        } catch (Exception e) {
            logger.error(e.getMessage());
            return null;
        }
    }

    public static String postWithFiles(String url, String fileName, byte[] content, String mediaType, Map<String, String> headers) {
        try {
            Headers httpHeader = Headers.of(headers);
            OkHttpClient client = new OkHttpClient();
            RequestBody formBody = new MultipartBody.Builder().setType(MultipartBody.FORM)
                    .addFormDataPart("file", fileName, RequestBody.create(MediaType.parse(mediaType), content))
                    .addFormDataPart("other_field", "other_field_value").build();
            Request request = new Request.Builder().url(url).headers(httpHeader).post(formBody).build();
            Response response = client.newCall(request).execute();
            return response.body().string();
        } catch (Exception e) {
            logger.error(e.getMessage());
            return null;
        }
    }

    public static <T> T doPost(String url, Map<String, String> paramMap, Class<T> clz) {
        return doPost(url, paramMap, null, clz);
    }

    public static String doPost(String url, Map<String, String> paramMap) {
        try {
            return doPost(url, paramMap, null, String.class);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return null;
        }
    }

    public static String doPost(String url, Map<String, String> paramMap, int timeout) {
        try {
            return doPost(url, paramMap, null, String.class, timeout);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return null;
        }
    }

    public static String doPost(String url, Map<String, String> paramMap, String cookie) {
        PostRequest request = HttpClient.post(url);
        if (paramMap != null && !paramMap.isEmpty()) {
            request = request.param(paramMap);
        }
        request.header("Connection", "close");
        request.header("Cookie", cookie);
        String result = request.execute().asString();
        return result;
    }

    public static String doPost(String url, Map<String, String> paramMap, Map<String, String> headMap) {
        return doPost(url, paramMap, headMap, String.class);
    }

    public static String doPostJson(String url, String json) {
        try {
            String result = HttpClient.textBody(url).json(json).charset("utf-8").execute().asString();
            return result;
        } catch (Exception e) {
            logger.error(e.getMessage());
            return null;
        }

    }

    public static <T> void doAsyncPostJson(String url, String json, Callback<T> callback) {
        try {
            HttpClient.textBody(url).json(json).charset("utf-8").execute(callback);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    public static String doPostJson(String url, String json, String cookie) {
        try {
            TextBodyRequest request = HttpClient.textBody(url).json(json).charset("utf-8");
            request.header("Cookie", cookie);
            request.header("Connection", "close");
            String result = request.execute().asString();
            return result;
        } catch (Exception e) {
            logger.error(e.getMessage());
            return null;
        }

    }

    public static <T> T doPostJson(String url, String json, String cookie, Class<T> clz) {
        try {
            TextBodyRequest request = HttpClient.textBody(url).json(json).charset("utf-8");
            request.header("Cookie", cookie);
            request.header("Connection", "close");
            T result = request.execute().asBean(clz);
            return result;
        } catch (Exception e) {
            logger.error(e.getMessage());
            return null;
        }

    }

    public static String doPostJson(String url, String json, Map<String, String> headMap, String cookie) {
        try {
            TextBodyRequest request = HttpClient.textBody(url).json(json).charset("utf-8");
            if (cookie != null) {
                request.header("Cookie", cookie);
            }
            request.header("Connection", "close");
            if (headMap != null && !headMap.isEmpty()) {
                for (Entry<String, String> item : headMap.entrySet()) {
                    request = request.header(item.getKey(), item.getValue());
                }
            }
            String result = request.execute().asString();
            return result;
        } catch (Exception e) {
            logger.error(e.getMessage());
            return null;
        }

    }

    public static <T> T doPostJson(String url, String json, String cookie, Class<T> clz, int timeout) {
        try {
            TextBodyRequest request = HttpClient.textBody(url).json(json).charset("utf-8").readTimeout(timeout);
            request.header("Cookie", cookie);
            request.header("Connection", "close");
            T result = request.execute().asBean(clz);
            return result;
        } catch (Exception e) {
            logger.error(e.getMessage());
            return null;
        }

    }

    public static String doPostXml(String url, String xml) {
        try {
            String result = HttpClient.textBody(url).xml(xml).charset("utf-8").execute().asString();
            return result;
        } catch (Exception e) {
            logger.error(e.getMessage());
            return null;
        }
    }
}
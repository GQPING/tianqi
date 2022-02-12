package com.tianqi.demo.utils;

import org.apache.commons.io.FileUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * 文件处理工具类
 *
 * @author ruoyi
 */
public class UtilFile {

    public static String FILENAME_PATTERN = "[a-zA-Z0-9_\\-\\|\\.\\u4e00-\\u9fa5]+";

    /**
     * 读入文件内容
     *
     * @param filePath 文件路径
     * @param encoding 字符集
     * @return 文件内容
     */
    private String read(String filePath, String encoding) throws IOException {
        ClassPathResource classPathResource = new ClassPathResource(filePath);
        File file = classPathResource.getFile();
        return FileUtils.readFileToString(file, encoding);
    }

    /**
     * 向文件写入数据
     *
     * @param filePath 文件路径
     * @param serial 需要写入的内容
     * @param encoding 字符集
     * @param append 是否追加至文件末尾
     * @throws IOException
     */
    private void writeMaxSerial(String filePath, String serial, String encoding, boolean append) throws IOException {
        ClassPathResource classPathResource = new ClassPathResource(filePath);
        File file = classPathResource.getFile();
        FileUtils.write(file, serial, encoding, append);
    }

    /**
     * 读取本地资源文件
     * @param filePath
     * @return 以字符串形式返回
     */
    public static String read(String filePath) {
        try {
            //读取Resource目录下的XML文件
            Resource resource = new ClassPathResource(filePath);
            //获取文件输入流
            InputStream inputStream = resource.getInputStream();
            //利用输入流获取XML文件内容
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));

            //缓存文件字符内容
            StringBuffer buffer = new StringBuffer();

            String line = "";
            while ((line = br.readLine()) != null) {
                buffer.append(line);
            }
            br.close();

            return buffer.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 读取本地资源文件
     *
     * @param response
     * @param filePath
     */
    public static void read(HttpServletResponse response, String filePath) {
        try {
            ClassPathResource classPathResource = new ClassPathResource(filePath);
            File file = classPathResource.getFile();
            InputStream inputStream = classPathResource.getInputStream();

            //文件输入流
            InputStream fis = new BufferedInputStream(inputStream);
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            fis.close();
            response.reset();

            //获取文件的名字再浏览器下载页面
            String name = file.getName();
            response.addHeader("Content-Disposition", "attachment;filename=" + new String(name.getBytes(), "iso-8859-1"));
            response.addHeader("Content-Length", "" + file.length());
            OutputStream out = new BufferedOutputStream(response.getOutputStream());
            response.setContentType("application/octet-stream");
            out.write(buffer);
            out.flush();
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除文件
     *
     * @param filePath 文件
     * @return
     */
    public static boolean delete(String filePath) {
        boolean flag = false;
        File file = new File(filePath);
        // 路径为文件且不为空则进行删除
        if (file.isFile() && file.exists()) {
            file.delete();
            flag = true;
        }
        return flag;
    }

    /**
     * 文件名称验证
     *
     * @param filename 文件名称
     * @return true 正常 false 非法
     */
    public static boolean isValid(String filename) {
        return filename.matches(FILENAME_PATTERN);
    }
}

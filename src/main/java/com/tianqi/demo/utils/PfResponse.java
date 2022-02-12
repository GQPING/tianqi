package com.tianqi.demo.utils;

import org.apache.http.HttpStatus;
import java.util.HashMap;

/**
 * @author cxw
 * @date 2022/2/8
 */
public class PfResponse extends HashMap<String, Object> {
    /**
     * 状态码
     */
    public static final String CODE_TAG = "code";

    /**
     * 返回内容
     */
    public static final String MSG_TAG = "msg";

    /**
     * 数据对象
     */
    public static final String DATA_TAG = "data";

    /**
     * 初始化一个新创建的 AjaxResult 对象，使其表示一个空消息。
     */
    public PfResponse() {
    }

    /**
     * 初始化一个新创建的 AjaxResult 对象
     *
     * @param code 状态码
     * @param msg  返回内容
     */
    public PfResponse(int code, String msg) {
        super.put(CODE_TAG, code);
        super.put(MSG_TAG, msg);
    }

    /**
     * 初始化一个新创建的 AjaxResult 对象
     *
     * @param code 状态码
     * @param msg  返回内容
     * @param data 数据对象
     */
    public PfResponse(int code, String msg, Object data) {
        super.put(CODE_TAG, code);
        super.put(MSG_TAG, msg);
        if (data != null) {
            super.put(DATA_TAG, data);
        }
    }

    /**
     * 返回成功消息
     *
     * @return 成功消息
     */
    public static PfResponse success() {
        return PfResponse.success("操作成功");
    }

    /**
     * 返回成功数据
     *
     * @return 成功消息
     */
    public static PfResponse success(Object data) {
        return PfResponse.success("操作成功", data);
    }

    /**
     * 返回成功消息
     *
     * @param msg 返回内容
     * @return 成功消息
     */
    public static PfResponse success(String msg) {
        return PfResponse.success(msg, null);
    }

    /**
     * 返回成功消息
     *
     * @param msg  返回内容
     * @param data 数据对象
     * @return 成功消息
     */
    public static PfResponse success(String msg, Object data) {
        return new PfResponse(HttpStatus.SC_OK, msg, data);
    }

    /**
     * 返回错误消息
     *
     * @return
     */
    public static PfResponse error() {
        return PfResponse.error("操作失败");
    }

    /**
     * 返回错误消息
     *
     * @param msg 返回内容
     * @return 警告消息
     */
    public static PfResponse error(String msg) {
        return PfResponse.error(msg, null);
    }

    /**
     * 返回错误消息
     *
     * @param msg  返回内容
     * @param data 数据对象
     * @return 警告消息
     */
    public static PfResponse error(String msg, Object data) {
        return new PfResponse(HttpStatus.SC_INTERNAL_SERVER_ERROR, msg, data);
    }

    /**
     * 返回错误消息
     *
     * @param code 状态码
     * @param msg  返回内容
     * @return 警告消息
     */
    public static PfResponse error(int code, String msg) {
        return new PfResponse(code, msg, null);
    }

    /**
     * 方便链式调用
     *
     * @param key   键
     * @param value 值
     * @return 数据对象
     */
    @Override
    public PfResponse put(String key, Object value) {
        super.put(key, value);
        return this;
    }
}

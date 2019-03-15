package com.company.app.mp.model;


import java.io.Serializable;

/**
 * 描述:
 * 创建时间：2019/3/11-3:15 PM
 *
 * @author: yinshuai
 */
public class DownloadModel implements Serializable {

    /**
     * 标题
     */
    private String title;
    /**
     * 下载连接
     */
    private String url;

    /**
     * 下载优先级 默认为0
     */
    private int priority = 0;

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getTitle() {
        return title;
    }

    public DownloadModel setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public DownloadModel setUrl(String url) {
        this.url = url;
        return this;
    }
}

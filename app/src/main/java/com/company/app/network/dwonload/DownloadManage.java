package com.company.app.network.dwonload;

import com.company.app.mp.model.DownloadModel;
import com.company.app.network.dwonload.listener.LogDownloadListener;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.db.DownloadManager;
import com.lzy.okgo.request.GetRequest;
import com.lzy.okserver.OkDownload;
import com.lzy.okserver.download.DownloadListener;
import com.lzy.okserver.download.DownloadTask;

import java.io.File;
import java.util.List;

/**
 * 描述:
 * 创建时间：2019/3/11-5:27 PM
 *
 * @author: yinshuai
 */
public class DownloadManage {


    /**
     * 添加一个任务带监听
     *
     * @param model
     */
    public static void addDwonloadTask(DownloadModel model, DownloadListener listener) {
        GetRequest<File> request = OkGo.<File>get(model.getUrl());

        //这里第一个参数是tag，代表下载任务的唯一标识，传任意字符串都行，需要保证唯一,我这里用url作为了tag
        OkDownload.request(model.getUrl(), request)
                .priority(model.getPriority())
                .extra1(model)
                .save()
                .register(listener)
                .register(new LogDownloadListener())
                .start();
    }


    /**
     * 添加一个任务
     *
     * @param model
     */
    public static void addDwonloadTask(DownloadModel model) {
        GetRequest<File> request = OkGo.<File>get(model.getUrl());

        //这里第一个参数是tag，代表下载任务的唯一标识，传任意字符串都行，需要保证唯一,我这里用url作为了tag
        OkDownload.request(model.getUrl(), request)
                .priority(model.getPriority())
                .extra1(model)
                .save()
                .register(new LogDownloadListener())
                .start();
    }


    /**
     * 获取一个任务
     *
     * @param tag
     * @return
     */
    public static DownloadTask getTask(String tag) {
        if (DownloadManager.getInstance().get(tag) == null) {
            return null;
        }
        return OkDownload.restore(DownloadManager.getInstance().get(tag));
    }


    /**
     * 删除任务 会删除下载文件
     *
     * @param task
     */
    public static void deleteTask(DownloadTask task) {
        if (task != null) {
            task.remove(true);
        }
    }

    /**
     * 取消任务
     *
     * @param tag
     */
    public static void cancelTask(String tag) {
        DownloadTask task = DownloadManage.getTask(tag);
        if (task != null) {
            task.remove();
        }
    }


    /**
     * 暂停任务
     *
     * @param tag
     */
    public static void pauseTask(String tag) {
        DownloadTask task = DownloadManage.getTask(tag);
        if (task != null) {
            task.pause();
        }
    }


    /**
     * 删除任务
     * true -删除原文件
     * false -不删除原文件
     *
     * @param task
     */
    public static void deleteTask(DownloadTask task, boolean isDeleteFile) {
        if (task != null) {
            task.remove(isDeleteFile);
        }
    }

    /**
     * 获取全部任务
     */
    public static List<DownloadTask> getAllTask() {
        return OkDownload.restore(DownloadManager.getInstance().getAll());
    }


    /**
     * 获取已完成的任务
     */
    public static List<DownloadTask> getFinished() {
        return OkDownload.restore(DownloadManager.getInstance().getFinished());
    }


    /**
     * 获取正在下载的任务
     */
    public static List<DownloadTask> getDownloading() {
        return OkDownload.restore(DownloadManager.getInstance().getDownloading());
    }


    /**
     * 清空下载任务
     */
    public static void removeAll() {
        OkDownload.getInstance().removeAll();
    }

    /**
     * 清空下载任务 是否删除文件夹
     */
    public static void removeAll(boolean isDelectDir) {
        OkDownload.getInstance().removeAll();
    }

}

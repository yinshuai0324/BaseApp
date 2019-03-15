package com.company.app.interfaces.bean;

import android.databinding.ViewDataBinding;
import android.view.View;

import com.company.app.R;
import com.company.app.databinding.ItemDownloadViewBinding;
import com.company.app.mp.model.DownloadModel;
import com.company.app.network.dwonload.DownloadManage;
import com.company.app.view.baseRecyclerView.inter.BaseRecyclerViewBean;
import com.lzy.okgo.model.Progress;
import com.lzy.okserver.download.DownloadListener;

import java.io.File;

/**
 * 描述:
 * 创建时间：2019/3/11-2:41 PM
 *
 * @author: yinshuai
 */
public class DownloadItemBean extends BaseRecyclerViewBean {

    private ItemDownloadViewBinding binding;

    private DownloadModel model;

    public DownloadItemBean(DownloadModel model) {
        this.model = model;
    }

    @Override
    public int getViewType() {
        return R.layout.item_download_view;
    }


    public DownloadModel getModel() {
        return model;
    }


    @Override
    public void onViewDataBinding(ViewDataBinding viewDataBinding) {
        if (viewDataBinding instanceof ItemDownloadViewBinding) {
            binding = (ItemDownloadViewBinding) viewDataBinding;
            binding.title.setText(model.getTitle());

            binding.download.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DownloadManage.addDwonloadTask(model, new onDownloadListener(model.getUrl()));
                }
            });

            binding.downloadCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DownloadManage.cancelTask(model.getUrl());
                }
            });

            binding.downloadPause.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DownloadManage.pauseTask(model.getUrl());
                }
            });
        }
    }


    public class onDownloadListener extends DownloadListener {

        public onDownloadListener(Object tag) {
            super(tag);
        }

        @Override
        public void onStart(Progress progress) {
            binding.state.setText("状态：开始下载");
        }

        @Override
        public void onProgress(Progress progress) {
            binding.state.setText("状态：正在下载：" + (progress.speed / 1024) + "/KB/S");
            binding.progressBar.setMaxProgress(progress.totalSize);
            binding.progressBar.setProgress(progress.currentSize);
        }

        @Override
        public void onError(Progress progress) {
            binding.state.setText("状态：下载出错");
        }

        @Override
        public void onFinish(File file, Progress progress) {
            binding.state.setText("状态：下载成功");
        }

        @Override
        public void onRemove(Progress progress) {
            baseRecyclerView.removeBean(DownloadItemBean.this, true);
        }
    }
}

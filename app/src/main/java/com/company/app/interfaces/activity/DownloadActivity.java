package com.company.app.interfaces.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;

import com.company.app.R;
import com.company.app.base.BaseActivity;
import com.company.app.databinding.ActivityDownloadBinding;
import com.company.app.interfaces.bean.DownloadItemBean;
import com.company.app.mp.model.DownloadModel;
import com.company.app.mp.presenter.ActivityDownloadPresenter;
import java.util.ArrayList;
import java.util.List;

/**
 * 多个文件下载Demo
 *
 * @author yinshuai
 */
public class DownloadActivity extends BaseActivity<ActivityDownloadBinding, ActivityDownloadPresenter> {
    private ActivityDownloadBinding binding;
    private List<DownloadModel> list = new ArrayList<>();

    @Override
    protected int getResLayout() {
        return R.layout.activity_download;
    }

    @Override
    protected void initView(ActivityDownloadBinding binding) {
        this.binding = binding;
        binding.baseRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void initialize() {
        list.add(new DownloadModel().setTitle("王者荣耀").setUrl("https://df22e9ad429c448f50558c5b118d25b7.dd.cdntips.com/imtt.dd.qq.com/16891/52D9FF5B0E4F30719F913E09BCE9C3E9.apk?mkey=5c862d037a90fc70&f=0c58&fsname=com.tencent.tmgp.sgame_1.43.1.27_43012701.apk"));
        list.add(new DownloadModel().setTitle("QQ").setUrl("https://800b469047e60d7f28127e1b654e460e.dd.cdntips.com/imtt.dd.qq.com/16891/62034C5405473DA5F5A0E873FB77093D.apk?mkey=5c862aaa7a90fc70&f=0c99&fsname=com.tencent.mobileqq_7.9.8_999.apk"));
        list.add(new DownloadModel().setTitle("微信").setUrl("https://ea0fc00e7efcf9b1ab0100055780a4b3.dd.cdntips.com/imtt.dd.qq.com/16891/68CF64B59AB1C0719D2DCB9DB0EFC03A.apk?mkey=5c862a8b7a90fc70&f=0ce9&fsname=com.tencent.mm_7.0.3_1400.apk"));
        list.add(new DownloadModel().setTitle("QQ空间").setUrl("https://60f5b975ca2292b9f24ac44150961aee.dd.cdntips.com/imtt.dd.qq.com/16891/60A106A777895C74418E334E861B2063.apk?mkey=5c862a697a90fc70&f=1849&fsname=com.qzone_8.2.7.288_118.apk"));
        list.add(new DownloadModel().setTitle("陌陌").setUrl("https://60f5b975ca2292b9f24ac44150961aee.dd.cdntips.com/imtt.dd.qq.com/16891/A4114FAD1DFFEAEE8F35054BE92E3C83.apk?mkey=5c862a327a90fc70&f=0ce9&fsname=com.immomo.momo_8.15.1_3806.apk"));
        list.add(new DownloadModel().setTitle("派派").setUrl("https://b1fa8238a80cc59ac848e1977c49c5e8.dd.cdntips.com/imtt.dd.qq.com/16891/D33F5C8F3DBE44B1093D530307169A46.apk?mkey=5c862a127a90fc70&f=1455&fsname=com.ifreetalk.ftalk_6.5.008_10605008.apk"));
        list.add(new DownloadModel().setTitle("百度贴吧").setUrl("https://55dd809f23956f1456d662bee81845f5.dd.cdntips.com/imtt.dd.qq.com/16891/F01341A5C3C93F854BE5D25D2578D0F5.apk?mkey=5c862bfa7a90fc70&f=0af0&fsname=com.baidu.tieba_10.0.8.2_167772161.apk"));
        list.add(new DownloadModel().setTitle("探探").setUrl("https://b1fa8238a80cc59ac848e1977c49c5e8.dd.cdntips.com/imtt.dd.qq.com/16891/6F56CA0B9B8A7CECBD312B2FCFF3F08A.apk?mkey=5c862bc77a90fc70&f=24c5&fsname=com.p1.mobile.putong_3.4.1_256.apk"));
        list.add(new DownloadModel().setTitle("拼多多").setUrl("https://ea0fc00e7efcf9b1ab0100055780a4b3.dd.cdntips.com/imtt.dd.qq.com/16891/5D2F2CC99B424684BFDD0C23D0C3C493.apk?mkey=5c862bac7a90fc70&f=07b4&fsname=com.xunmeng.pinduoduo_4.46.0_44600.apk"));
        list.add(new DownloadModel().setTitle("微视").setUrl("https://20e3dd735055bfaecef7edceec27aa1d.dd.cdntips.com/imtt.dd.qq.com/16891/0384F3B3581D809D3F49193098C54C10.apk?mkey=5c862b687a90fc70&f=9870&fsname=com.tencent.weishi_5.0.6.588_506.apk"));

        for (int i = 0; i < list.size(); i++) {
            binding.baseRecyclerView.addBean(new DownloadItemBean(list.get(i)));
        }
        binding.baseRecyclerView.notifyDataSetChanged();
    }
}

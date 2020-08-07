package com.laoniu.ezandroid.ui.fmt;

import android.content.Context;
import android.widget.ImageView;

import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.laoniu.ezandroid.R;
import com.laoniu.ezandroid.databinding.FmtBannerBinding;
import com.laoniu.ezandroid.ui.BaseFragment;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;


public class BannerFmt extends BaseFragment<FmtBannerBinding> {

    List<String> list_path;
    List<String> list_title;

    @Override
    public int getLayoutId() {
        return R.layout.fmt_banner;
    }

    @Override
    public void initView() {
        binding.banner.setImages(list_path);
        binding.banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                //binding.banner点击事件
                ToastUtils.showShort("click="+position);
            }
        });
        binding.banner.setImageLoader(new ImageLoader() {
            @Override
            public void displayImage(Context context, Object path, ImageView imageView) {
                Glide.with(context).load((String) path).into(imageView);
            }
        });
        binding.banner.start();
    }

    private void getData(){
        //放图片地址的集合
        list_path = new ArrayList<>();
        //放标题的集合
        list_title = new ArrayList<>();

        list_path.add("https://ww4.sinaimg.cn/large/006uZZy8jw1faic21363tj30ci08ct96.jpg");
        list_path.add("https://ww4.sinaimg.cn/large/006uZZy8jw1faic259ohaj30ci08c74r.jpg");
        list_path.add("https://ww4.sinaimg.cn/large/006uZZy8jw1faic2b16zuj30ci08cwf4.jpg");
        list_path.add("https://ww4.sinaimg.cn/large/006uZZy8jw1faic2e7vsaj30ci08cglz.jpg");
        list_title.add("好好学习");
        list_title.add("天天向上");
        list_title.add("热爱劳动");
        list_title.add("不搞对象");
    }


}

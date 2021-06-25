package com.laoniu.ezandroid.ui.act;

import android.text.TextUtils;
import android.view.ViewGroup;

import com.laoniu.ezandroid.BaseBindingActivity;
import com.laoniu.ezandroid.R;
import com.laoniu.ezandroid.databinding.ActWebviewBinding;
import com.laoniu.ezandroid.model.WebUrl;
import com.laoniu.ezandroid.utils.WebViewUtils;

public class WebviewActivity extends BaseBindingActivity<ActWebviewBinding> {

    @Override
    public int getLayoutId() {
        return R.layout.act_webview;
    }

    @Override
    public void initView() {
        String url = getIntent().getStringExtra("data");
        String title = getIntent().getStringExtra("title");

        setTitle(!TextUtils.isEmpty(title) ? title : "");
        if(TextUtils.isEmpty(url)){
//            ToastUtils.showShort("url不能为空");
//            finish();
//            return;
            url= WebUrl.google_translate;
        }

        WebViewUtils.set(binding.webview);

        binding.webview.loadUrl(url);
    }


    @Override
    protected void onDestroy() {
        if (binding.webview != null) {
            binding.webview.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            binding.webview.clearHistory();
            ((ViewGroup) binding.webview.getParent()).removeView(binding.webview);
            binding.webview.destroy();
        }
        super.onDestroy();
    }
}

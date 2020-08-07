package com.laoniu.ezandroid.ui.act;

import android.app.StatusBarManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.ViewGroup;
import android.webkit.WebView;

import androidx.annotation.Nullable;

import com.blankj.utilcode.util.ToastUtils;
import com.laoniu.ezandroid.R;
import com.laoniu.ezandroid.model.WebUrl;
import com.laoniu.ezandroid.ui.BaseActivity;
import com.laoniu.ezandroid.utils.WebViewUtils;

public class WebviewActivity extends BaseActivity {

    WebView webView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_webview);
        onLeftClick();

        String url = getIntent().getStringExtra("data");
        String title = getIntent().getStringExtra("title");

        setTitle(!TextUtils.isEmpty(title) ? title : "");
        if(TextUtils.isEmpty(url)){
//            ToastUtils.showShort("url不能为空");
//            finish();
//            return;
            url= WebUrl.google_translate;
        }
        webView = (WebView)findViewById(R.id.webview);
        WebViewUtils.set(webView);

        webView.loadUrl(url);
    }



    @Override
    protected void onDestroy() {
        if (webView != null) {
            webView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            webView.clearHistory();

            ((ViewGroup) webView.getParent()).removeView(webView);
            webView.destroy();
            webView = null;
        }
        super.onDestroy();
    }
}

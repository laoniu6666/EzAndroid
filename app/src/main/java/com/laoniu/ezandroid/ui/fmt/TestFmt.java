package com.laoniu.ezandroid.ui.fmt;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;

import com.laoniu.ezandroid.R;
import com.laoniu.ezandroid.databinding.FmtChatBinding;
import com.laoniu.ezandroid.ui.BaseFragment;

public class TestFmt extends BaseFragment<FmtChatBinding> {

    public static TestFmt newInstance(Bundle bundle) {
        TestFmt fragment = new TestFmt();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fmt_chat;
    }

    @Override
    public void initView() {
        String data = getArguments().getString("data");
        binding.tv.setText(data);
    }

}

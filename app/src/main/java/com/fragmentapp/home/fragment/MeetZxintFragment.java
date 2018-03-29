package com.fragmentapp.home.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.fragmentapp.R;
import com.fragmentapp.base.LazyFragment;
import com.fragmentapp.helper.ImageUtil;
import com.uuzuche.lib_zxing.activity.CaptureFragment;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by liuzhen on 2017/11/2.
 */

public class MeetZxintFragment extends LazyFragment {

    /**
     * 选择系统图片Request Code
     */
    public static final int REQUEST_IMAGE = 112;

    private CaptureFragment captureFragment;

    public static MeetZxintFragment newInstance(Bundle bundle) {
        MeetZxintFragment fragment = new MeetZxintFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_zxing;
    }

    @Override
    protected void init() {
        setLazyLoad(false);
        tv_title.setText("消息");
        tv_right_title.setText("相册");

        captureFragment = new CaptureFragment();
        // 为二维码扫描界面设置定制化界面
        CodeUtils.setFragmentArgs(captureFragment, R.layout.layout_zxing_camera);
        captureFragment.setAnalyzeCallback(analyzeCallback);
        getChildFragmentManager().beginTransaction().replace(R.id.fl_my_container, captureFragment).commit();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        captureFragment.setAnalyzeCallback(null);
    }

    @OnClick({R.id.menu})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.menu:
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_IMAGE);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE) {
            if (data != null) {
                Uri uri = data.getData();
                try {
                    CodeUtils.analyzeBitmap(ImageUtil.getImageAbsolutePath(getContext(), uri), analyzeCallback);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 二维码解析回调函数
     */
    CodeUtils.AnalyzeCallback analyzeCallback = new CodeUtils.AnalyzeCallback() {
        @Override
        public void onAnalyzeSuccess(Bitmap mBitmap, String result) {
            //处理扫描结果（在界面上显示）
            if (null != result) {
                Toast.makeText(getActivity(), result, Toast.LENGTH_LONG).show();
//                Bundle bundle = new Bundle();
//                bundle.putInt("FragmentID", 16);
//                bundle.putString("webname", "扫码结果");
//                bundle.putString("weburl", result);
//                bundle.putBoolean("isLocation", false);
//                bundle.putBoolean("NoToolbar", false);
//                ActivityUtil.easyStartActivity(getActivity(), AddFragmentActivity.class, bundle);
//                getActivity().finish();
            }
        }

        @Override
        public void onAnalyzeFailed() {
            Toast.makeText(getActivity(), "解析失败", Toast.LENGTH_LONG).show();
        }
    };

}

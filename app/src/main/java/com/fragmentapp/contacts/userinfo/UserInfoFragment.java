package com.fragmentapp.contacts.userinfo;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.fragmentapp.R;
import com.fragmentapp.base.LazyFragment;
import com.fragmentapp.helper.KLog;
import com.fragmentapp.helper.ResourcesUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Bryan on 2017/8/23.
 */

public class UserInfoFragment extends LazyFragment implements View.OnClickListener {
    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerview;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    private int defaultScaleHeight;
    private UserInfoAdapter mAdapter;
    private View mHeaderView;
    private FrameLayout mIvNavIcon;
    private TextView mTvName;
    private ImageView mIvBg;
    private ImageView mAvatar;
    private float mStartY;
    private View mfooterView;
    private TextView mTvSubmit;
    private Bundle mBundle;
    private String mString;

    public static UserInfoFragment newInstance(Bundle bundle) {
        UserInfoFragment fragment = new UserInfoFragment();
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_notoolbar_recyclerview;
    }

    @Override
    protected void init() {
        mSwipeRefreshLayout.setEnabled(false);
        defaultScaleHeight = (int) ResourcesUtil.getDimension(R.dimen.d720_0);
        mRecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new UserInfoAdapter(R.layout.item_userinfo);
        mRecyclerview.setAdapter(mAdapter);
        mHeaderView = getLayoutInflater().inflate(R.layout.item_head_userinfo, null);
        mHeaderView.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, defaultScaleHeight));
        mAvatar = mHeaderView.findViewById(R.id.iv_avatar);
        mIvBg = mHeaderView.findViewById(R.id.iv_bg);
        mTvName = mHeaderView.findViewById(R.id.tv_name);
        mIvNavIcon = mHeaderView.findViewById(R.id.fl_nav_icon);
        mAdapter.addHeaderView(mHeaderView);

        mfooterView = getLayoutInflater().inflate(R.layout.item_btn_sendmessage, null);
        mfooterView.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) ResourcesUtil.getDimension(R.dimen.d286_0)));
        mTvSubmit = mfooterView.findViewById(R.id.btn_submit);
        mAdapter.addFooterView(mfooterView);

        mIvNavIcon.setOnClickListener(this);
        mTvSubmit.setOnClickListener(this);
        mRecyclerview.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        // 记录手指压下时的数据
                        mStartY = motionEvent.getY();
                        KLog.e("mStartY", mStartY);
                        break;
                    case MotionEvent.ACTION_MOVE:
                        // 获取手指当前位置
                        float moveY = motionEvent.getY();
                        // 计算手指移动距离
                        float offsetY = moveY - mStartY;
                        KLog.e("offsetY", offsetY);

                        if (offsetY + defaultScaleHeight < ResourcesUtil.dip2px(ResourcesUtil.getDimension(R.dimen.d10_0))) {
                            return false;
                        }
                        mHeaderView.getLayoutParams().height = defaultScaleHeight + (int) offsetY / 4;
                        mHeaderView.requestLayout();

                        break;
                    case MotionEvent.ACTION_UP:
                        ResetAnimation animation = new ResetAnimation();
                        animation.setDuration(500);
                        mHeaderView.startAnimation(animation);
                        break;

                }


                return false;
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fl_nav_icon:
                if (getActivity() != null) {
                    getActivity().finish();
                }
                break;

            case R.id.btn_submit:
                break;

            default:
                break;
        }
    }


    /**
     * 松手后恢复默认状态的动画
     */
    class ResetAnimation extends Animation {

        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            super.applyTransformation(interpolatedTime, t);
            //interpolatedTime:[0,1],表示执行百分比
            //缩小scleView
            mHeaderView.getLayoutParams().height = calcBetweenValue(mHeaderView.getHeight(), defaultScaleHeight, interpolatedTime);
            mHeaderView.requestLayout();
        }
    }

    /**
     * 计算中间值
     *
     * @param src      起始值
     * @param dest     终止值
     * @param progress 进度[0,1]
     * @return
     */
    private int calcBetweenValue(int src, int dest, float progress) {
        return (int) (src + (dest - src) * progress);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }


    /**
     * 拨打电话（跳转到拨号界面，用户手动点击拨打）
     *
     * @param phoneNum 电话号码
     */
    public void callPhone(String phoneNum) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        Uri data = Uri.parse("tel:" + phoneNum);
        intent.setData(data);
        try {
            startActivity(intent);
        } catch (Exception e) {

        }

    }


}

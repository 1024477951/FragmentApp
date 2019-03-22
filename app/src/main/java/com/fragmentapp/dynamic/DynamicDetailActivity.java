package com.fragmentapp.dynamic;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;

import com.blankj.utilcode.util.ToastUtils;
import com.flyco.animation.BounceEnter.BounceTopEnter;
import com.fragmentapp.R;
import com.fragmentapp.base.BaseActivity;
import com.fragmentapp.dynamic.adapter.DynamicDetailAdapter;
import com.fragmentapp.dynamic.adapter.DynamicItemAdapter;
import com.fragmentapp.view.dialog.KeyboardDialog;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by liuzhen on 2018/11/26.
 */

public class DynamicDetailActivity extends BaseActivity implements OnRefreshLoadMoreListener {

    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.rvComment)
    RecyclerView rvComment;
    private DynamicDetailAdapter commentAdapter;
    private int page,count,headY;

    private KeyboardDialog dialog;

    public static void start(Context context) {
        start(context, null);
    }

    public static void start(Context context, Intent extras) {
        Intent intent = new Intent();
        intent.setClass(context, DynamicDetailActivity.class);
        if (extras != null) {
            intent.putExtras(extras);
        }
        context.startActivity(intent);
    }

    @Override
    public int layoutID() {
        return R.layout.activity_dynamic_detail;
    }

    @Override
    public void init() {
        dialog = KeyboardDialog.newInstance();
//        rvComment.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//            public void onGlobalLayout() {
//
//                Rect r = new Rect();
//                rvComment.getWindowVisibleDisplayFrame(r);
//                int screenHeight = getWindow().getDecorView().getRootView().getHeight();
//                int keypadHeight = screenHeight - r.bottom;
//                Logger.e("keypadHeight--"+keypadHeight);
//                if (dialog != null) {
//                    if (keypadHeight > 0) {
//                        dialog.reloadEmoji(false);
//                    } else {
//                        dialog.reloadEmoji(true);
//                    }
//                }
//            }
//        });
        setTitleText("动态详情");

        initComment();

        Intent intent = getIntent();
        if (intent != null) {
            Bundle extras = intent.getExtras();
            if(extras == null)
                return;
            switch (intent.getType()) {
                case "text/plain"://分享的内容类型，如果png图片：image/png 
                    ToastUtils.showShort("分享内容：\ntitle: " + extras.get(Intent.EXTRA_TITLE) + "\ncontent: " + extras.get(Intent.EXTRA_TEXT));
                    break;
                default:
                    break;
            }
        }
    }

    private void initComment(){
        page = 1;
        count = 3;
        commentAdapter = new DynamicDetailAdapter(R.layout.item_dynamic_detail);
        refreshLayout.setOnRefreshLoadMoreListener(this);
        View head = LayoutInflater.from(context).inflate(R.layout.item_dynamic_detail_head, null);
        if (head != null){
            RecyclerView recyclerView = head.findViewById(R.id.recyclerView);

            DynamicItemAdapter adapter = new DynamicItemAdapter(R.layout.item_dynamic_item);
            recyclerView.setAdapter(adapter);
            List<String> items = new ArrayList<>();
            for(int i = 0;i< 5;i++){
                items.add(""+i);
            }
            adapter.setNewData(items);

            commentAdapter.addHeaderView(head);
        }
        final View fullView = findViewById(R.id.full_head);
        final View stickyHead = LayoutInflater.from(context).inflate(R.layout.item_dynamic_detail_sticky_head, null);
        if (stickyHead != null){
            commentAdapter.addHeaderView(stickyHead);
        }

        rvComment.setAdapter(commentAdapter);
        getList();
        rvComment.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                int[] location = new int[2];
                fullView.getLocationOnScreen(location);
                int y = location[1];
                headY = y;
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (stickyHead == null) return;
                int[] location = new int[2];
                stickyHead.getLocationOnScreen(location);
                int getTop = location[1];
                if (getTop <= headY) {
                    fullView.setVisibility(View.VISIBLE);
                } else {
                    fullView.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        page++;
        if (page > count) {
            refreshLayout.finishLoadMoreWithNoMoreData();
        }else{
            getList();
            refreshLayout.finishLoadMore();
        }
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        refreshLayout.finishRefresh();
        page = 1;
        getList();
    }

    private void getList(){
        List<String> items = new ArrayList<>();
        for(int i = 0;i< 5*page;i++){
            items.add(""+i);
        }
        commentAdapter.setNewData(items);
    }

    @OnClick({R.id.layout_comment})
    public void click(View view){
        switch (view.getId()){
            case R.id.layout_comment:
                dialog.onSwitch(getSupportFragmentManager());
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (dialog != null){
            if (dialog.isVisible()) {
                dialog.dismiss();
            }
            dialog = null;
        }
    }

}

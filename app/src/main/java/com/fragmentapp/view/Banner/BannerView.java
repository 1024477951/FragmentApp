package com.fragmentapp.view.Banner;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.fragmentapp.R;
import com.fragmentapp.helper.DensityUtil;

/**
 * Created by liuzhen on 2018/3/6.
 */

public class BannerView extends FrameLayout {

    private RecyclerView recyclerView;
    private BannerAdapter adapter;

    public BannerView(Context context) {
        this(context,null);
    }

    public BannerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public BannerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        recyclerView = new RecyclerView(getContext());
        ViewGroup.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DensityUtil.dp2px(getContext(),200));
        recyclerView.setLayoutParams(params);

        adapter = new BannerAdapter(R.layout.item_banner);
        recyclerView.setAdapter(adapter);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        linearLayoutManager.setStackFromEnd(true);
        new PagerSnapHelper().attachToRecyclerView(recyclerView);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {//不滑动的时候
                    int lastItemPosition = linearLayoutManager.findLastCompletelyVisibleItemPosition();
//                    lin_indicate.setIndicatePosition(lastItemPosition);
                }
            }
        });
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

            }
        });
    }

    public void load(int count,int position){

    }


}

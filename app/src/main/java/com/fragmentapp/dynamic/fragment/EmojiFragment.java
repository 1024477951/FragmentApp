package com.fragmentapp.dynamic.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.fragmentapp.R;
import com.fragmentapp.base.LazyFragment;
import com.fragmentapp.dynamic.adapter.EmojiAdapter;
import com.fragmentapp.dynamic.adapter.EmojiListAdapter;
import com.fragmentapp.emoji.StickerCategory;
import com.fragmentapp.emoji.StickerManager;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;

/**
 * Created by liuzhen on 2018/11/30.
 */

public class EmojiFragment extends LazyFragment {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private EmojiAdapter adapter;
    private EmojiListAdapter.CallBack callBack;

    private String name;

    public static EmojiFragment newInstance(Bundle bundle) {
        EmojiFragment fragment = new EmojiFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_emoji;
    }

    @Override
    protected void init() {
        adapter = new EmojiAdapter(R.layout.item_emoji);
        recyclerView.setAdapter(adapter);

        final StickerManager manager = StickerManager.getInstance();
        // 贴图
        StickerCategory category = manager.getCategory(name);
        adapter.setNewData(category.getStickers());
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int position) {
                if (callBack != null){
                    callBack.click(adapter.getData().get(position),position);
                }
            }
        });
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCallBack(EmojiListAdapter.CallBack callBack){
        this.callBack = callBack;
    }

}

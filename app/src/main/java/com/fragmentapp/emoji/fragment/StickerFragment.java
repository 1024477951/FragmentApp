package com.fragmentapp.emoji.fragment;

import android.os.Bundle;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.fragmentapp.R;
import com.fragmentapp.emoji.adapter.StickerAdapter;
import com.fragmentapp.emoji.manager.StickerCategory;
import com.fragmentapp.emoji.manager.StickerManager;

import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

/**
 * Created by liuzhen on 2018/11/30.
 */

public class StickerFragment extends EmojiBaseFragment {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private StickerAdapter adapter;

    public static StickerFragment newInstance(Bundle bundle) {
        StickerFragment fragment = new StickerFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_sticker;
    }

    @Override
    protected void init() {
        adapter = new StickerAdapter(R.layout.item_sticker);
        recyclerView.setAdapter(adapter);
        final StickerManager manager = StickerManager.getInstance();
//        // 贴图
        final StickerCategory category = manager.getCategory(name);
        adapter.setNewData(category.getStickers());
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int position) {
                if (callBack != null){
                    callBack.click(adapter.getData().get(position).getCategory(),name);
                }
            }
        });
    }

}

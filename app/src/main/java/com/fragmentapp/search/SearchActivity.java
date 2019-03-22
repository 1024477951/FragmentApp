package com.fragmentapp.search;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.EditText;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.fragmentapp.R;
import com.fragmentapp.base.BaseActivity;
import com.fragmentapp.helper.KeyboardUtil;
import com.fragmentapp.home.bean.HomeDataBean;
import com.fragmentapp.search.adapter.SearchAdapter;
import com.fragmentapp.search.imple.ISearchView;
import com.fragmentapp.search.presenter.SearchPresenter;

import java.util.List;

import butterknife.BindView;

/**
 * Created by liuzhen on 2018/11/14.
 */

public class SearchActivity extends BaseActivity implements ISearchView{

    public static void start(Context context) {
        start(context, null);
    }

    public static void start(Context context, Intent extras) {
        Intent intent = new Intent();
        intent.setClass(context, SearchActivity.class);
        if (extras != null) {
            intent.putExtras(extras);
        }
        context.startActivity(intent);
    }

    @BindView(R.id.suggestions_list_container)
    View suggestions_list_container;
    @BindView(R.id.suggestions_list)
    RecyclerView mSuggestionsList;
    @BindView(R.id.search_bar_text)
    EditText editText;
    private SearchAdapter adapter;

    @Override
    public int layoutID() {
        return R.layout.activity_search;
    }

    @Override
    public void init() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(context,
                LinearLayoutManager.VERTICAL, true);
        mSuggestionsList.setLayoutManager(layoutManager);
        mSuggestionsList.setItemAnimator(null);
        adapter = new SearchAdapter(R.layout.item_search_tag);
        adapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM);
        mSuggestionsList.setAdapter(adapter);
        new SearchPresenter(this).getArticleList(1);

        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                moveSuggestListToInitialPos();
            }
        });
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getCurrentFocus() != null) {
                    KeyboardUtil.showKeyboard(getCurrentFocus());
                }
                editText.setFocusable(true);
                editText.findFocus();
                editText.requestFocus();
                editText.requestFocusFromTouch();
                editText.setVisibility(View.VISIBLE);
            }
        });
        final int finalHeight = suggestions_list_container.getHeight();
        suggestions_list_container.getLayoutParams().height = finalHeight;
        suggestions_list_container.requestLayout();
        ViewTreeObserver vto = suggestions_list_container.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (editText.hasFocus()) {
                    editText.clearFocus();
                    editText.setFocusable(false);
                }
            }
        });

    }

    public void removeGlobalLayoutObserver(View view, ViewTreeObserver.OnGlobalLayoutListener layoutListener) {
        if (Build.VERSION.SDK_INT < 16) {
            view.getViewTreeObserver().removeGlobalOnLayoutListener(layoutListener);
        } else {
            view.getViewTreeObserver().removeOnGlobalLayoutListener(layoutListener);
        }
    }

    private void moveSuggestListToInitialPos() {
        suggestions_list_container.setTranslationY(-(suggestions_list_container.getHeight()+editText.getHeight()*2));
    }

    @Override
    public void success(List<HomeDataBean> list) {
        adapter.setNewData(list);
    }

    @Override
    public void error() {

    }

    @Override
    public void loading() {

    }

    @Override
    public void loadStop() {

    }
}

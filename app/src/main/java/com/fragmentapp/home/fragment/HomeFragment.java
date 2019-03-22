package com.fragmentapp.home.fragment;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.flyco.dialog.entity.DialogMenuItem;
import com.flyco.dialog.listener.OnOperItemClickL;
import com.flyco.dialog.widget.ActionSheetDialog;
import com.fragmentapp.R;
import com.fragmentapp.TabHelper;
import com.fragmentapp.home.adapter.HomeAdapter;
import com.fragmentapp.base.IMFragment;
import com.fragmentapp.home.bean.HomeDataBean;
import com.fragmentapp.home.imple.IHomeView;
import com.fragmentapp.home.presenter.HomePresenter;
import com.fragmentapp.im.IMActivity;
import com.fragmentapp.search.SearchActivity;
import com.fragmentapp.selector.PhotoSelectUtils;
import com.fragmentapp.view.dialog.IOSTaoBaoDialog;
import com.orhanobut.logger.Logger;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by liuzhen on 2017/11/8.
 */

public class HomeFragment extends IMFragment implements IHomeView,TabHelper.OnListenerClick {

    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
//    private TextHeadView textHeadView;
//    private DownHeadView downHeadView;//扇形头部
//    private StickyHeadView stickyHeadView;//粘性头部
//    private SunHeadView sunHeadView;

    private HomeAdapter adapter;

    private HomePresenter presenter;
    private int page = 1,lastPage = -1;

    @Override
    protected void init() {

        setTitleText("消息");
        setTitleRightMenu(R.mipmap.de_ic_add);
        presenter = new HomePresenter(this);
        page = 1;

        adapter = new HomeAdapter(R.layout.item_home);
        adapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        View head = LayoutInflater.from(getContext()).inflate(R.layout.layout_search, null);
        if (head != null){
            adapter.addHeaderView(head);
            head.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SearchActivity.start(getContext());
                }
            });
        }
        presenter.getArticleList(page);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                IMActivity.start(getContext());
            }
        });
        adapter.setOnItemLongClickListener(new BaseQuickAdapter.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(BaseQuickAdapter adapter, View view, int position) {
//                ActionSheetDialogNoTitle();
                final IOSTaoBaoDialog dialog = new IOSTaoBaoDialog(getContext(), refreshLayout);
                dialog.show();
                return false;
            }
        });
//        textHeadView = new TextHeadView(getContext());
//        downHeadView = new DownHeadView(getContext());
//        stickyHeadView = new StickyHeadView(getContext());
//        sunHeadView = new SunHeadView(getContext());

//        emptyLayout.setCallBack(new EmptyLayout.CallBack() {
//            @Override
//            public void click() {
//                presenter.getArticleList(page);
//            }
//        });

//        Observable.just("object")
//                .subscribeOn(Schedulers.newThread())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Observer<String>() {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//
//                    }
//
//                    @Override
//                    public void onNext(String list) {
//
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//
//                    }
//
//                    @Override
//                    public void onComplete() {
//
//                    }
//                });

    }

    private void ActionSheetDialogNoTitle() {
        final ArrayList<DialogMenuItem> mContents = new ArrayList<>();
        DialogMenuItem item = new DialogMenuItem("test",1);
        mContents.add(item);
        item = new DialogMenuItem("帮助与反馈",2);
        mContents.add(item);
        item = new DialogMenuItem("退出App",3);
        mContents.add(item);
        final ActionSheetDialog dialog = new ActionSheetDialog(getContext(), mContents, refreshLayout);
        dialog.isTitleShow(false).show();
        dialog.setOnOperItemClickL(new OnOperItemClickL() {
            @Override
            public void onOperItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getContext(),mContents.get(position).mOperName,Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getUserVisibleHint()) {//初始化默认加载
            onVisible();
        }
    }

    @OnClick({R.id.menu})
    public void click(View view) {
        PhotoSelectUtils.getInstance().openNum(HomeFragment.this);
    }

    @Override
    public void success(List<HomeDataBean> list) {
        if (list.size() == 0){
//            emptyLayout.showEmpty((ViewGroup) getView(),"empty");
        }else {
            page++;//如果有数据则+1下一页
            if (lastPage != page) {
                adapter.setNewData(list);
            }
            lastPage = page;
        }
    }

    @Override
    public void error() {
//        emptyLayout.showEmpty((ViewGroup) getView(),"error");
    }

    @Override
    public void loading() {
        showDialog();
    }

    @Override
    public void loadStop() {
        dismissDialog();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter = null;
//        textHeadView = null;
//        downHeadView = null;
//        stickyHeadView = null;
//        sunHeadView = null;
    }

    @Override
    public void click() {
        int position = adapter.findReadPosition(recyclerView);
        smoothMoveToPosition(position);
        Logger.e("tag--"+position);
    }

    /**
     * 滑动到指定位置
     */
    private void smoothMoveToPosition(final int position) {
        int firstItem = recyclerView.getChildLayoutPosition(recyclerView.getChildAt(0));
        int lastItem = recyclerView.getChildLayoutPosition(recyclerView.getChildAt(recyclerView.getChildCount() - 1));

        if (position < firstItem) {//往上定位
            // 第一种可能:跳转位置在第一个可见位置之前，使用smoothScrollToPosition
            recyclerView.smoothScrollToPosition(position);
        } else if (position <= lastItem) {//往下定位
            // 第二种可能:跳转位置在第一个可见位置之后，最后一个可见项之前
            int movePosition = position - firstItem;
            if (movePosition >= 0 && movePosition < recyclerView.getChildCount()) {
                int top = recyclerView.getChildAt(movePosition).getTop();
                // smoothScrollToPosition 不会有效果，此时调用smoothScrollBy来滑动到指定位置
                recyclerView.smoothScrollBy(0, top);
            }
        }else{//未读item是最后一个的时候
            recyclerView.smoothScrollToPosition(position);
        }
    }

}

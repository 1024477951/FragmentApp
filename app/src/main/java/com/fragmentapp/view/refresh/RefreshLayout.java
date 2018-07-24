package com.fragmentapp.view.refresh;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Rect;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.AbsListView;
import android.widget.FrameLayout;

import com.fragmentapp.R;
import com.fragmentapp.helper.TimeUtil;
import com.orhanobut.logger.Logger;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by LiuZhen on 2017/3/24.
 */
public class RefreshLayout extends FrameLayout {

    private String TAG = "tag";
    private int downY;// 按下时y轴的偏移量
    private final static int RATIO = 3;//
    //头部的高度
    protected int mHeadHeight = 120;
    //头部layout
    protected FrameLayout mHeadLayout, mFootLayout;//头部容器
    private List<IHeadView> heads = new ArrayList<>();//支持添加多个头部
    private List<IFootView> foots = new ArrayList<>();

    public static final int DOWN_REFRESH = 0;// 下拉刷新状态
    public static final int RELEASE_REFRESH = 1;// 松开刷新
    public static final int LOADING = 2;// 正在刷新中
    public static final int END = 3;// 正在刷新中
    private int currentState = DOWN_REFRESH;// 头布局的状态: 默认为下拉刷新状态

    private View list;//子节点中的 recyclerview 视图
    private LayoutParams listParam, footParam;//用于控制下拉动画展示
    private boolean isLoadingMore = false;// 是否进入加载状态，防止多次重复的启动
    private boolean isStart = false;//表示正在加载刷新中，还没停止
    private boolean isAllow = true;//是否进行加载操作
    private boolean isTop = false, isBottom = false;
    private int mTouchSlop;
    private CallBack callBack;

    public RefreshLayout(Context context) {
        this(context, null, 0);
    }

    public RefreshLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RefreshLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.RefreshLayout, defStyleAttr, 0);
        try {
            mHeadHeight = a.getDimensionPixelSize(R.styleable.RefreshLayout__height, 120);
        } finally {
            a.recycle();
        }
        mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();

        init();
    }

    private void initHeaderContainer() {
        mHeadLayout = new FrameLayout(getContext());
        LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, mHeadHeight);
        this.addView(mHeadLayout, layoutParams);
    }

    public void initFootContainer() {
        footParam = new LayoutParams(LayoutParams.MATCH_PARENT, mHeadHeight);
        mFootLayout = new FrameLayout(getContext());//底部布局
        mFootLayout.setBackgroundColor(Color.BLACK);
        footParam.gravity = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;
        footParam.setMargins(0, 0, 0, -mHeadHeight);
        this.addView(mFootLayout, footParam);
    }

    private void init() {
        initHeaderContainer();
    }

    @Override
    protected void onFinishInflate() {//布局加载成xml时触发
        super.onFinishInflate();

        initFootContainer();
        if (list == null) {
            list = getChildAt(1);
            listParam = (LayoutParams) list.getLayoutParams();
            list.setOnTouchListener(new OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    return onFingerTouch(event);
                }
            });
        }
    }

    /**
     * 设置头部View
     */
    public RefreshLayout setHeaderView(final IHeadView headView) {
        if (headView != null) {
            mHeadLayout.addView(headView.getView());
            heads.add(headView);
        }
        return this;
    }

    /**
     * 设置尾部View
     */
    public RefreshLayout setFootView(final IFootView footView) {
        if (footView != null) {
            mFootLayout.addView(footView.getView());
            foots.add(footView);
        }
        return this;
    }

    public boolean onFingerTouch(MotionEvent ev) {
        if (isAllow == true) {

            isTop = isViewToTop(list, mTouchSlop);
            isBottom = isViewToBottom(list, mTouchSlop);

            switch (ev.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    currentState = END;
                    downY = (int) ev.getY();
                    break;
                case MotionEvent.ACTION_MOVE:
                    if (!isTop && !isBottom)//没有到顶，无需计算操作
                        break;
                    int moveY = (int) ev.getY();
                    /**
                     * 得到滑动的距离，可以打印出日志查看，如果滑动的速度很快，那么得到距离的跨度会很大，比如快速滑动，可能只得到三个数据，50，400，700，而如果缓慢的
                     * 滑动，则30，35，39，41...696,700,显然我们想得到的是后面的数据，否则跨度很大会出现闪速的现象
                     * */
                    int diff = moveY - downY;
                    /**这里是为了降低跨度存在的，尽量的让数值跨度不要那么大*/
                    int paddingTop = diff / RATIO;

                    if (paddingTop > 0 && isTop) {
                        //向下滑动多少后开始启动刷新,Margin判断是为了限制快速用力滑动的时候导致头部侵入的高度不够就开始加载了
                        if (paddingTop >= mHeadHeight && (listParam.topMargin >= mHeadHeight) && currentState == DOWN_REFRESH) { // 完全显示了.
//                        Log.i(TAG, "松开刷新 RELEASE_REFRESH");
                            currentState = RELEASE_REFRESH;
                            refreshHeaderView();
                            start();
                        } else if (currentState == END) { // 没有显示完全
//                        Log.i(TAG, "下拉刷新 DOWN_PULL_REFRESH");
                            currentState = DOWN_REFRESH;
                            refreshHeaderView();
                        }
                        if (paddingTop <= (mHeadHeight + 10) && !isStart) {//已经处于运行刷新状态的时候禁止设置
                            listParam.setMargins(0, paddingTop, 0, 0);
                            list.setLayoutParams(listParam);
                            if (callBack != null)
                                callBack.pullListener(paddingTop);
                        }
                    } else if (isBottom) {
                        //限制上滑时不能超过底部的宽度，不然会超出边界
                        //mHeadHeight+20 上滑设置的margin要超过headheight，不然下面判断的大于headheight不成立，下面的margin基础上面设置后的参数
                        if (Math.abs(paddingTop) <= (mHeadHeight + 10) && !isStart) {//已经处于运行刷新状态的时候禁止设置
                            listParam.setMargins(0, 0, 0, -paddingTop);
                            footParam.setMargins(0, 0, 0, -paddingTop - mHeadHeight);
                            list.setLayoutParams(listParam);
                        }
                        //如果滑动的距离大于头部或者底部的高度，并且设置的margin也大于headheight
                        //listParam用来限制recyclerview列表迅速滑动，footParam用来限制bottom foothead迅速滑动导致没有达到head的高度就开始加载了
                        if (Math.abs(paddingTop) >= mHeadHeight && (listParam.bottomMargin >= mHeadHeight || footParam.bottomMargin >= 0))
                            isLoadingMore = true;//头部是否拉取到位，然后执行加载动画

                    }
                    break;
                case MotionEvent.ACTION_UP:
                    if (isLoadingMore) {//是否开始加载
                        currentState = LOADING;
                        refreshHeaderView();
                        isLoadingMore = false;
                        isStart = true;//是否开始加载
                        isAllow = false;
                    } else {
                        currentState = END;
                        refreshHeaderView();
                        if (!isStart) {
                            // 隐藏头布局
                            listParam.setMargins(0, 0, 0, 0);
                            footParam.setMargins(0, 0, 0, -mHeadHeight);
                            list.setLayoutParams(listParam);
                        }
                        if (callBack != null)
                            callBack.pullListener(0);
                    }

                    break;
                default:
                    break;
            }
        }
        return super.onTouchEvent(ev);
    }

    /**
     * 根据currentState刷新头布局的状态
     */
    private void refreshHeaderView() {
        if (callBack == null || isStart)
            return;
        String val;
        switch (currentState) {
            case DOWN_REFRESH: // 下拉刷新状态
                val = "下拉";
                break;
            case RELEASE_REFRESH: // 松开刷新状态
                val = "开始...";
                break;
            case LOADING: // 正在刷新中状态
                val = "刷新中...";
                TimeUtil.startTime();
                break;
            case END:
            default:
                val = "";
                break;
        }

        callBack.refreshHeaderView(currentState, val);
    }

    /**
     * 是否下拉操作
     **/
    public boolean isBottom() {
        return isBottom;
    }

    /**
     * 开始加载
     **/
    public void start() {
        isLoadingMore = true;
        for (IHeadView head : heads) {
            head.startAnim();
        }
    }

    /**
     * 结束加载
     **/
    public void stop() {
        if (callBack != null)
            callBack.pullListener(0);
        currentState = END;
        TimeUtil.endTime();
        long loadTime = TimeUtil.getDateMillis();//大于0表示小于默认的加载时间
//        Toast.makeText(getContext(), loadTime + "毫秒", Toast.LENGTH_SHORT).show();
        if (loadTime > 0) {//最小限制2秒的动画加载效果
            postDelayed(new Runnable() {//没有网络访问时固定2秒加载完成
                @Override
                public void run() {
                    listParam.setMargins(0, 0, 0, 0);
                    footParam.setMargins(0, 0, 0, -mHeadHeight);
                    list.setLayoutParams(listParam);
                    isLoadingMore = false;
                    isStart = false;
                    isAllow = true;
                    for (IHeadView head : heads) {
                        head.stopAnim();
                    }
                    refreshHeaderView();
                }
            }, loadTime);
        }else{
            listParam.setMargins(0, 0, 0, 0);
            footParam.setMargins(0, 0, 0, -mHeadHeight);
            list.setLayoutParams(listParam);
            isLoadingMore = false;
            isStart = false;
            isAllow = true;
            for (IHeadView head : heads) {
                head.stopAnim();
            }
            refreshHeaderView();
        }

    }

    public static boolean isViewToTop(View view, int mTouchSlop) {
        if (view instanceof AbsListView) return isAbsListViewToTop((AbsListView) view);
        if (view instanceof RecyclerView) return isRecyclerViewToTop((RecyclerView) view);
        return (view != null && Math.abs(view.getScrollY()) <= 2 * mTouchSlop);
    }

    public static boolean isViewToBottom(View view, int mTouchSlop) {
        if (view instanceof AbsListView) return isAbsListViewToBottom((AbsListView) view);
        if (view instanceof RecyclerView) return isRecyclerViewToBottom((RecyclerView) view);
//        if (view instanceof WebView) return isWebViewToBottom((WebView) view,mTouchSlop);
//        if (view instanceof ViewGroup) return isViewGroupToBottom((ViewGroup) view);
        return false;
    }

    public static boolean isAbsListViewToTop(AbsListView absListView) {
        if (absListView != null) {
            int firstChildTop = 0;
            if (absListView.getChildCount() > 0) {
                // 如果AdapterView的子控件数量不为0，获取第一个子控件的top
                firstChildTop = absListView.getChildAt(0).getTop() - absListView.getPaddingTop();
            }
            if (absListView.getFirstVisiblePosition() == 0 && firstChildTop == 0) {
                return true;
            }
        }
        return false;
    }

    public static boolean isRecyclerViewToTop(RecyclerView recyclerView) {
        if (recyclerView != null) {
            RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
            if (manager == null) {
                return true;
            }
            if (manager.getItemCount() == 0) {
                return true;
            }

            if (manager instanceof LinearLayoutManager) {
                LinearLayoutManager layoutManager = (LinearLayoutManager) manager;

                int firstChildTop = 0;
                if (recyclerView.getChildCount() > 0) {
                    // 处理item高度超过一屏幕时的情况
                    View firstVisibleChild = recyclerView.getChildAt(0);
                    if (firstVisibleChild != null && firstVisibleChild.getMeasuredHeight() >= recyclerView.getMeasuredHeight()) {
                        if (android.os.Build.VERSION.SDK_INT < 14) {
                            return !(ViewCompat.canScrollVertically(recyclerView, -1) || recyclerView.getScrollY() > 0);
                        } else {
                            return !ViewCompat.canScrollVertically(recyclerView, -1);
                        }
                    }

                    // 如果RecyclerView的子控件数量不为0，获取第一个子控件的top

                    // 解决item的topMargin不为0时不能触发下拉刷新
                    View firstChild = recyclerView.getChildAt(0);
                    RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) firstChild.getLayoutParams();
                    firstChildTop = firstChild.getTop() - layoutParams.topMargin - getRecyclerViewItemTopInset(layoutParams) - recyclerView.getPaddingTop();
                }

                if (layoutManager.findFirstCompletelyVisibleItemPosition() < 1 && firstChildTop == 0) {
                    return true;
                }
            } else if (manager instanceof StaggeredGridLayoutManager) {
                StaggeredGridLayoutManager layoutManager = (StaggeredGridLayoutManager) manager;

                int[] out = layoutManager.findFirstCompletelyVisibleItemPositions(null);
                if (out[0] < 1) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 通过反射获取RecyclerView的item的topInset
     */
    private static int getRecyclerViewItemTopInset(RecyclerView.LayoutParams layoutParams) {
        try {
            Field field = RecyclerView.LayoutParams.class.getDeclaredField("mDecorInsets");
            field.setAccessible(true);
            // 开发者自定义的滚动监听器
            Rect decorInsets = (Rect) field.get(layoutParams);
            return decorInsets.top;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static boolean isAbsListViewToBottom(AbsListView absListView) {
        if (absListView != null && absListView.getAdapter() != null && absListView.getChildCount() > 0 && absListView.getLastVisiblePosition() == absListView.getAdapter().getCount() - 1) {
            View lastChild = absListView.getChildAt(absListView.getChildCount() - 1);

            return lastChild.getBottom() <= absListView.getMeasuredHeight();
        }
        return false;
    }

    public static boolean isRecyclerViewToBottom(RecyclerView recyclerView) {
        if (recyclerView != null) {
            RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
            if (manager == null || manager.getItemCount() == 0) {
                return false;
            }

            if (manager instanceof LinearLayoutManager) {
                // 处理item高度超过一屏幕时的情况
                View lastVisibleChild = recyclerView.getChildAt(recyclerView.getChildCount() - 1);
                if (lastVisibleChild != null && lastVisibleChild.getMeasuredHeight() >= recyclerView.getMeasuredHeight()) {
                    if (android.os.Build.VERSION.SDK_INT < 14) {
                        return !(ViewCompat.canScrollVertically(recyclerView, 1) || recyclerView.getScrollY() < 0);
                    } else {
                        return !ViewCompat.canScrollVertically(recyclerView, 1);
                    }
                }

                LinearLayoutManager layoutManager = (LinearLayoutManager) manager;
                if (layoutManager.findLastCompletelyVisibleItemPosition() == layoutManager.getItemCount() - 1) {
                    return true;
                }
            } else if (manager instanceof StaggeredGridLayoutManager) {
                StaggeredGridLayoutManager layoutManager = (StaggeredGridLayoutManager) manager;

                int[] out = layoutManager.findLastCompletelyVisibleItemPositions(null);
                int lastPosition = layoutManager.getItemCount() - 1;
                for (int position : out) {
                    if (position == lastPosition) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public RefreshLayout setCallBack(CallBack callBack) {
        this.callBack = callBack;
        return this;
    }

    public interface CallBack {
        /**
         * 监听下拉时的状态
         */
        void refreshHeaderView(int state, String stateVal);

        /**
         * 监听下拉时的距离
         */
        void pullListener(int y);
    }


}

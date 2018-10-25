package com.fragmentapp.im.emojiutils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.SpannableString;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout.LayoutParams;

import com.fragmentapp.R;

import java.util.ArrayList;
import java.util.List;

public class EmojiWidget implements OnItemClickListener {

	private Context context;
	private Activity activity; // 用来表明是哪个activity发起的
	


	/** 显示表情页的ViewPager */
	private ViewPager emoji_viewpage;
	/** 游标显示布局 */
	private LinearLayout emoji_cursor;
	
	/** 表情页界面集合 */
	private ArrayList<View> emojiPageViews;
	/** 表情数据填充器 */
	private List<EmojiAdapter> emojiAdapters;

	/** 游标点集合 */
	private ArrayList<ImageView> emojiCursorViews;
	/** 表情集合 */
	private List<List<EmojiEntry>> emojis;

	/** 当前表情页 */
	private int current = 0;
	
	/** 输入框*/
	private EditText et_sendmessage;

	/**标志*/
	private int UI_FLAG;

	/**更新UI*/
	private Handler mUIHandler;
	
	public EmojiWidget(Activity activity, final Context context, int UI_FLAG, Handler mUIHandler, EditText et_sendmessage) {
		this.UI_FLAG = UI_FLAG;
		this.mUIHandler = mUIHandler;
		this.emojis = EmojiConversionUtils.INSTANCE.emojiLists;
		if (this.emojis == null || this.emojis.size() == 0) {
			EmojiConversionUtils.INSTANCE.init(context);
			this.emojis = EmojiConversionUtils.INSTANCE.emojiLists;
		} 
		this.et_sendmessage = et_sendmessage;
		this.activity = activity;
		this.context = context;
		Init_View();
		Init_viewPager();
		Init_Point();
		Init_Data();
	}
	

	
	/** 初始化控件 */
	private void Init_View() { 
		this.emoji_viewpage = (ViewPager) activity.findViewById(R.id.emoji_viewpage);
		this.emoji_cursor = (LinearLayout) activity.findViewById(R.id.emoji_cursor);
	}
	
	/** 初始化显示表情的ViewPager */
	@SuppressWarnings("deprecation")
	private void Init_viewPager() {
		this.emojiPageViews = new ArrayList<View>();
		// 左侧添加空页
		View nullView1 = new View(context);
		// 设置透明背景
		nullView1.setBackgroundColor(Color.TRANSPARENT);
		emojiPageViews.add(nullView1);

		// 中间添加表情页
		this.emojiAdapters = new ArrayList<EmojiAdapter>();
		for (int i = 0; i < emojis.size(); i++) {
			GridView view = new GridView(context);
			EmojiAdapter adapter = new EmojiAdapter(context, emojis.get(i));
			view.setAdapter(adapter);
			emojiAdapters.add(adapter);
			view.setOnItemClickListener(this);
			view.setNumColumns(7);
			view.setBackgroundColor(Color.TRANSPARENT);
			view.setHorizontalSpacing(1);
			view.setVerticalSpacing(1);
			view.setStretchMode(GridView.STRETCH_COLUMN_WIDTH);
			view.setCacheColorHint(0);
			view.setPadding(5, 0, 5, 0);

			view.setSelector(new ColorDrawable(Color.TRANSPARENT));
			view.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,
					LayoutParams.WRAP_CONTENT));
			view.setGravity(Gravity.CENTER);
			this.emojiPageViews.add(view);
		}

		// 右侧添加空页面
		View nullView2 = new View(context);
		// 设置透明背景
		nullView2.setBackgroundColor(Color.TRANSPARENT);
		this.emojiPageViews.add(nullView2);
	}

	/** 初始化填充数据 */
	private void Init_Data() {
		this.emoji_viewpage.setAdapter(new ViewPagerAdapter(this.emojiPageViews));

		this.emoji_viewpage.setCurrentItem(1);
		current = 0;
		this.emoji_viewpage.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				refreshUI(PAGE_SELECTED, arg0); // 页面更新
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {

			}
		});

	} 
	
	/** 初始化游标 */
	private void Init_Point() {
		this.emojiCursorViews = new ArrayList<ImageView>();
		ImageView imageView;
		for (int i = 0; i < emojiPageViews.size(); i++) {
			imageView = new ImageView(context);
//			imageView.setBackgroundResource(R.mipmap.emoji_cursor_1);
			LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
					new ViewGroup.LayoutParams(LayoutParams.WRAP_CONTENT,
							LayoutParams.WRAP_CONTENT));
			layoutParams.leftMargin = 10;
			layoutParams.rightMargin = 10;
			layoutParams.width = 8;
			layoutParams.height = 8;
			emoji_cursor.addView(imageView, layoutParams);
			if (i == 0 || i == emojiPageViews.size() - 1) {
				imageView.setVisibility(View.GONE);
			}
			if (i == 1) {
//				imageView.setBackgroundResource(R.mipmap.emoji_cursor_2);
			}
			this.emojiCursorViews.add(imageView); 
		}
	}


	/** 刷新UI向主线程*/
	private void refreshUI(int arg1,Object obj) {
		Message msg = this.mUIHandler.obtainMessage();
		msg.what = this.UI_FLAG;
		msg.arg1 = arg1;
		msg.obj = obj;
		msg.sendToTarget();
	}
	
	public static final int PAGE_SELECTED = 0x1024;

	/** 用来对接UI的刷新,保证始终在主线程*/
	public void refreshWidgetUI(Message msg) {
		switch(msg.arg1) {
		case PAGE_SELECTED:onPageSelected((Integer)msg.obj);
		}
	}
	
	/** 当切换页面时会回调该方法*/
	private void onPageSelected(int arg0) {
		current = arg0 - 1;
		// 描绘分页点
		draw_Point(emojiCursorViews, arg0);
		// 如果是第一屏或者是最后一屏禁止滑动，其实这里实现的是如果滑动的是第一屏则跳转至第二屏，如果是最后一屏则跳转到倒数第二屏.
		if (arg0 == emojiCursorViews.size() - 1 || arg0 == 0) {
			if (arg0 == 0) {
				emoji_viewpage.setCurrentItem(arg0 + 1);// 第二屏 会再次实现该回调方法实现跳转.
//				emojiCursorViews.get(1).setBackgroundResource(R.mipmap.emoji_cursor_2);
			} else {
				emoji_viewpage.setCurrentItem(arg0 - 1);// 倒数第二屏
//				emojiCursorViews.get(arg0 - 1).setBackgroundResource(
//						R.mipmap.emoji_cursor_2);
			}
		}
	}
	
	/** 绘制游标背景 */
	private void draw_Point(ArrayList<ImageView> emojiCursorViews, int index) {
		for (int i = 1; i < emojiCursorViews.size(); i++) {
			if (index == i) {
//				emojiCursorViews.get(i).setBackgroundResource(R.mipmap.emoji_cursor_2);
			} else {
//				emojiCursorViews.get(i).setBackgroundResource(R.mipmap.emoji_cursor_1);
			}
		}
	}
	
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
		EmojiEntry emoji = (EmojiEntry) this.emojiAdapters.get(this.current).getItem(position);
		if (emoji.getId() == R.mipmap.emoji_item_delete) {
			int selection = et_sendmessage.getSelectionStart();
			String text = et_sendmessage.getText().toString();
			if (selection > 0) {
				String text2 = text.substring(selection - 1);
				if ("]".equals(text2)) {
					int start = text.lastIndexOf("[");
					int end = selection;
					et_sendmessage.getText().delete(start, end);
					return;
				}
				et_sendmessage.getText().delete(selection - 1, selection);
			}
		}
		if (!TextUtils.isEmpty(emoji.getCharacter())) {

			SpannableString spannableString = EmojiConversionUtils.INSTANCE
					.addFace(this.context, emoji.getId(), emoji.getCharacter());
			et_sendmessage.append(spannableString);
		}
	}
}

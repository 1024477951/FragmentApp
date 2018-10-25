package com.fragmentapp.im.emojiutils;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.fragmentapp.R;

import java.util.List;

/**
 * 表情适配器
 */
public class EmojiAdapter extends BaseAdapter {

	private List<EmojiEntry> data;
	private LayoutInflater inflater;
	private int size = 0;

	public EmojiAdapter(Context context, List<EmojiEntry> list) {
		this.inflater = LayoutInflater.from(context);
		this.data = list;
		this.size = list.size();
	}

	@Override
	public int getCount() {
		return this.size;
	}

	@Override
	public Object getItem(int position) {
		return data.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@SuppressWarnings("deprecation")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		EmojiEntry emoji = data.get(position);
		ViewHolder viewHolder = null;
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = inflater.inflate(R.layout.emoji_item, null);
			viewHolder.emoji_item_face = (ImageView) convertView
					.findViewById(R.id.emoji_item_face);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		if (emoji.getId() == R.mipmap.emoji_item_delete) {
			convertView.setBackgroundDrawable(null);
			viewHolder.emoji_item_face.setImageResource(emoji.getId());
		} else if (TextUtils.isEmpty(emoji.getCharacter())) {
			convertView.setBackgroundDrawable(null);
			viewHolder.emoji_item_face.setImageDrawable(null);
		} else {
			viewHolder.emoji_item_face.setTag(emoji);
			viewHolder.emoji_item_face.setImageResource(emoji.getId());
		}

		return convertView;
	}

	/** 缓存的holder*/
	class ViewHolder {
		public ImageView emoji_item_face;
	}
}
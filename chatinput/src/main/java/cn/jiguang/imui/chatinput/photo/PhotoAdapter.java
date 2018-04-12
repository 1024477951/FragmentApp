package cn.jiguang.imui.chatinput.photo;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import cn.jiguang.imui.chatinput.ChatInputView;
import cn.jiguang.imui.chatinput.listener.OnFileSelectedListener;
import cn.jiguang.imui.chatinput.utils.ViewUtil;
import cn.jiguang.imui.chatinput.model.FileItem;
import cn.jiguang.imui.chatinput.R;
import cn.jiguang.imui.chatinput.model.VideoItem;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.PhotoViewHolder> {

    private Context mContext;

    private List<FileItem> mMedias = new ArrayList<>();
    private List<FileItem> mSendFiles = new ArrayList<>();
    private List<Integer> mSelectedItems = new ArrayList<>();

    private OnFileSelectedListener mListener;

    public PhotoAdapter(List<FileItem> list) {
        if (list != null) {
            mMedias = list;
        }
    }

    public List<FileItem> getSelectedFiles() {
        return mSendFiles;
    }

    public void setOnPhotoSelectedListener(OnFileSelectedListener listener) {
        mListener = listener;
    }

    public void resetCheckedState() {
        mSendFiles.clear();
        mSelectedItems.clear();
        notifyDataSetChanged();
    }

    @Override
    public PhotoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View layout = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_photo_select, parent, false);
        return new PhotoViewHolder(layout);
    }

    @Override
    public void onBindViewHolder(final PhotoViewHolder holder, final int position) {

        if (holder.container.getHeight() != ChatInputView.sMenuHeight) {
            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(
                    ChatInputView.sMenuHeight, ChatInputView.sMenuHeight);
            holder.container.setLayoutParams(layoutParams);
        }

        FileItem item = mMedias.get(position);
        Glide.with(mContext)
                .load(item.getFilePath())
                .into(holder.ivPhoto);

//        holder.ivPhoto.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
//        int width = holder.ivPhoto.getMeasuredWidth();

        if (mSelectedItems.contains(position)) {    // Current photo is selected
            holder.ivShadow.setVisibility(VISIBLE);
            addSelectedAnimation(holder.container);

        } else if (holder.ivShadow.getVisibility() == View.VISIBLE) { // Selected before, now have not been selected
            holder.ivShadow.setVisibility(View.GONE);
            addDeselectedAnimation(holder.container);
        }

        final FileItem.Type fileItem = item.getType();

        if (fileItem == FileItem.Type.Video) {
            holder.tvDuration.setVisibility(View.VISIBLE);

            long duration = ((VideoItem) item).getDuration();
            String durationStr = String.format(Locale.CHINA, "%02d:%02d",
                    TimeUnit.SECONDS.toMinutes(duration),
                    TimeUnit.SECONDS.toSeconds(duration));

            holder.tvDuration.setText(durationStr);
        }

        if (item.isCheck()  == true){
            holder.tvTick.setText(item.getIndex()+"");
            holder.tvTick.setBackground(mContext.getResources().getDrawable(R.drawable.shape_radius_338eff_30));
        }else{
            holder.tvTick.setText("");
            holder.tvTick.setBackground(mContext.getResources().getDrawable(R.drawable.shape_radius_1a000000_30));
        }

        holder.ivPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int p = holder.getAdapterPosition();

                if (mSelectedItems.size() >= 9)
                    return;
                if (!mSelectedItems.contains(p)) {
                    holder.setIsRecyclable(false);

                    mSelectedItems.add(p);
                    mSendFiles.add(mMedias.get(p));

                    holder.ivShadow.setVisibility(VISIBLE);

                    addSelectedAnimation(holder.container);

                    if (mListener != null) {
                        mListener.onFileSelected();
                    }
                    mMedias.get(position).setCheck(true);
                    mMedias.get(position).setIndex(mSelectedItems.size());
                    holder.tvTick.setBackground(mContext.getResources().getDrawable(R.drawable.shape_radius_338eff_30));
                } else {
                    holder.setIsRecyclable(true);

                    mSelectedItems.remove(Integer.valueOf(p));
                    mSendFiles.remove(mMedias.get(p));

                    holder.ivShadow.setVisibility(GONE);

                    addDeselectedAnimation(holder.container);
                    if (mListener != null) {
                        mListener.onFileDeselected();
                    }
                    mMedias.get(position).setCheck(false);
                    holder.tvTick.setBackground(mContext.getResources().getDrawable(R.drawable.shape_radius_1a000000_30));
                    setItemIndex();
                }
                notifyDataSetChanged();
            }
        });
    }

    private void setItemIndex(){
        int index = 0;
        for(int i = 0;i < mMedias.size();i++){
            if (mMedias.get(i).isCheck()){
                index++;
                mMedias.get(i).setIndex(index);
            }else{
                mMedias.get(i).setIndex(0);
            }
        }
    }

    @Override
    public int getItemCount() {
        return mMedias.size();
    }

    @Override
    public int getItemViewType(int position) {
        return mMedias.get(position).getType().getCode();
    }

    private void addDeselectedAnimation(View... views) {
        List<Animator> valueAnimators = new ArrayList<>();
        for (View v : views) {
            ObjectAnimator scaleX = ObjectAnimator.ofFloat(v, "scaleX", 1.0f);
            ObjectAnimator scaleY = ObjectAnimator.ofFloat(v, "scaleY", 1.0f);

            valueAnimators.add(scaleX);
            valueAnimators.add(scaleY);
        }

        AnimatorSet set = new AnimatorSet();
        set.playTogether(valueAnimators);
        set.setDuration(150);
        set.start();
    }

    private void addSelectedAnimation(View... views) {
        List<Animator> valueAnimators = new ArrayList<>();
        for (View v : views) {
            ObjectAnimator scaleX = ObjectAnimator.ofFloat(v, "scaleX", 0.98f);
            ObjectAnimator scaleY = ObjectAnimator.ofFloat(v, "scaleY", 0.98f);

            valueAnimators.add(scaleX);
            valueAnimators.add(scaleY);
        }

        AnimatorSet set = new AnimatorSet();
        set.playTogether(valueAnimators);
        set.setDuration(150);
        set.start();
    }

    static final class PhotoViewHolder extends RecyclerView.ViewHolder {
        View container;
        TextView tvDuration;
        ImageView ivPhoto;
        ImageView ivShadow;
        TextView tvTick;

        PhotoViewHolder(View itemView) {
            super(itemView);
            container = itemView;
            tvDuration = (TextView) itemView.findViewById(R.id.text_photoselect_duration);
            ivPhoto = (ImageView) itemView.findViewById(R.id.image_photoselect_photo);
            ivShadow = (ImageView) itemView.findViewById(R.id.image_photoselect_shadow);
            tvTick =  itemView.findViewById(R.id.tv_photoselect_tick);
        }
    }
}

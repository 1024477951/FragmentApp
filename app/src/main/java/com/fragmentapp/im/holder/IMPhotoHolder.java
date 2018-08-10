package com.fragmentapp.im.holder;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.NinePatch;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.fragmentapp.App;
import com.fragmentapp.R;
import com.fragmentapp.im.MessageStatus;
import com.fragmentapp.im.bean.MsgBean;

/**
 * Created by liuzhen on 2018/8/7.
 */

public class IMPhotoHolder extends IMBaseHolder{

    protected ImageView iv_content;

    @Override
    protected void init(boolean isSelef) {
        iv_content = getView(R.id.iv_content);
        layout_content.setBackground(null);
    }

    @Override
    protected void status(int status) {
        switch (status) {//0:消息占位（在发送图片、文件等耗时任务是）1:发送中 2:发送成功 3:已收到 4:已读 5:撤销 10:发送失败 11:已删除
            case MessageStatus.SPACE:
            case MessageStatus.CREATED:
                pb_load.setVisibility(View.VISIBLE);
                btn_error.setVisibility(View.GONE);
                tv_read.setVisibility(View.GONE);
                break;
            case MessageStatus.SEND_ERROR:
                pb_load.setVisibility(View.GONE);
                tv_read.setVisibility(View.GONE);
                btn_error.setVisibility(View.VISIBLE);
                break;
            case MessageStatus.SEND_SUCCEED:
            case MessageStatus.RECEIVE_SUCCEED:
                pb_load.setVisibility(View.GONE);
                btn_error.setVisibility(View.GONE);
                tv_read.setVisibility(View.VISIBLE);
                tv_read.setText("已送达");
                break;
            case MessageStatus.READ:
                pb_load.setVisibility(View.GONE);
                btn_error.setVisibility(View.GONE);
                tv_read.setVisibility(View.VISIBLE);
                tv_read.setText("已读");
                break;
        }
    }

    @Override
    protected void content(final MsgBean item) {
        btn_error.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clickListener != null) {
                    clickListener.click(v,item);
                }
            }
        });
        layout_content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clickListener != null) {
                    clickListener.click(v,item);
                }
            }
        });
        layout_content.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (longClickListener != null) {
                    longClickListener.longClick(v, item);
                }
                return true;
            }
        });
        profile_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clickListener != null) {
                    clickListener.click(v,item);
                }
            }
        });
    }

    public IMPhotoHolder(View view) {
        super(view);

    }

    private void showImage(){
        Bitmap bitmap_bg = BitmapFactory.decodeResource(App.getInstance().getResources(), R.drawable.icon_im_send_def_bg);
        Bitmap bitmap_in = BitmapFactory.decodeResource(App.getInstance().getResources(), R.drawable.icon_demo_bg);
        final Bitmap bp = getRoundCornerImage(bitmap_bg, bitmap_in);
        final Bitmap bp2 = getShardImage(bitmap_bg, bp);
        iv_content.setImageBitmap(bp2);
    }

    public Bitmap getRoundCornerImage(Bitmap bitmap_bg, Bitmap bitmap_in)
    {
        Bitmap roundConcerImage = Bitmap.createBitmap(500,500, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(roundConcerImage);
        Paint paint = new Paint();
        Rect rect = new Rect(0,0,500,500);
        Rect rectF = new Rect(0, 0, bitmap_in.getWidth(), bitmap_in.getHeight());
        paint.setAntiAlias(true);
        NinePatch patch = new NinePatch(bitmap_bg, bitmap_bg.getNinePatchChunk(), null);
        patch.draw(canvas, rect);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap_in, rectF, rect, paint);
        return roundConcerImage;
    }
    public Bitmap getShardImage(Bitmap bitmap_bg,Bitmap bitmap_in)
    {
        Bitmap roundConcerImage = Bitmap.createBitmap(500,500, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(roundConcerImage);
        Paint paint = new Paint();
        Rect rect = new Rect(0,0,500,500);
        paint.setAntiAlias(true);
        NinePatch patch = new NinePatch(bitmap_bg, bitmap_bg.getNinePatchChunk(), null);
        patch.draw(canvas, rect);
        Rect rect2 = new Rect(2,2,498,498);
        canvas.drawBitmap(bitmap_in, rect, rect2, paint);
        return roundConcerImage;
    }

}

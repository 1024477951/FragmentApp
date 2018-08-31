package com.fragmentapp.im.holder;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.NinePatch;
import android.graphics.Paint;
import android.graphics.Path;
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
//        layout_content.setBackground(null);
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
        showImage(item.isSelf());
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

    private void showImage(boolean isSelf){
        Bitmap bitmap_in = BitmapFactory.decodeResource(App.getInstance().getResources(), R.drawable.icon_demo_bg);
//        final Bitmap bp = getRoundCornerImage(bitmap_bg, bitmap_in);
        final Bitmap bp2 = canvasTriangle(bitmap_in,isSelf ? 0 : 1);
        iv_content.setImageBitmap(bp2);
    }

    /**
     * 绘制成微信聊天效果
     * @param bitmapimg
     * @param direct
     * @return
     */
    public static Bitmap canvasTriangle(Bitmap bitmapimg, int direct) {
        Bitmap output = Bitmap.createBitmap(bitmapimg.getWidth()/2,
                bitmapimg.getHeight()/2, Bitmap.Config.ARGB_4444);
        Canvas canvas = new Canvas(output);
        //设置默认背景颜色
        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmapimg.getWidth(),
                bitmapimg.getHeight());

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        //右边
        if (direct == 0) {
            canvas.drawRect(0, 0, bitmapimg.getWidth() - 15, bitmapimg.getHeight(), paint);
            Path path = new Path();
            path.moveTo(bitmapimg.getWidth() - 15, 10);
            path.lineTo(bitmapimg.getWidth(), 20);
            path.lineTo(bitmapimg.getWidth() - 15, 30);
            path.lineTo(bitmapimg.getWidth() - 15, 10);
            canvas.drawPath(path, paint);
        }
        //左边
        if (direct == 1) {
            canvas.drawRect(15, 0, bitmapimg.getWidth(), bitmapimg.getHeight(), paint);
            Path path = new Path();
            path.moveTo(15, 10);
            path.lineTo(0, 20);
            path.lineTo(15, 30);
            path.lineTo(15, 10);
            canvas.drawPath(path, paint);
        }
        //两层绘制交集。显示上层
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmapimg, rect, rect, paint);
        return output;
    }


}

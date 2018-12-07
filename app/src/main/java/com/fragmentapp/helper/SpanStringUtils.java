/**
 *
 */
package com.fragmentapp.helper;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.widget.EditText;
import android.widget.TextView;

import com.blankj.utilcode.util.Utils;
import com.fragmentapp.view.emoji.EmotionUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author : zejian
 * @time : 2016年1月5日 上午11:30:39
 * @email : shinezejian@163.com
 * @description :文本中的emojb字符处理为表情图片
 */
public class SpanStringUtils {

    public static SpannableString getEmotionContent(String source, int dimenSize) {
        SpannableString spannableString = new SpannableString(source);
        Resources res = Utils.getApp().getResources();
        int size = (int) ((Utils.getApp().getResources().getDimension(dimenSize)));
        String regexEmotion = "\\[([\u4e00-\u9fa5\\w])+]";
        Pattern patternEmotion = Pattern.compile(regexEmotion);
        Matcher matcherEmotion = patternEmotion.matcher(spannableString);

        while (matcherEmotion.find()) {
            // 获取匹配到的具体字符
            String key = matcherEmotion.group();
            // 匹配字符串的开始位置
            int start = matcherEmotion.start();
            // 利用表情名字获取到对应的图片
            Integer imgRes = EmotionUtils.getImgByName(key);
            if (imgRes != null) {
                // 压缩表情图片
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inPreferredConfig = Bitmap.Config.ARGB_4444;
                Bitmap bitmap = BitmapFactory.decodeResource(res, imgRes, options);
                if (bitmap != null) {

//					KLog.e("SpanStringUtils",size);
                    Bitmap scaleBitmap = Bitmap.createScaledBitmap(bitmap, size, size, true);
                    VerticalImageSpan span = new VerticalImageSpan(Utils.getApp(), scaleBitmap);
                    spannableString.setSpan(span, start, start + key.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                }

            }
        }
        return spannableString;
    }

}

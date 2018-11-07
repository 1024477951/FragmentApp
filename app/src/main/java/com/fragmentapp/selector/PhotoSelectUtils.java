package com.fragmentapp.selector;

import android.Manifest;
import android.app.Activity;
import android.support.v4.app.Fragment;

import com.hanmaker.bryan.hc.R;
import com.hanmaker.bryan.hc.util.log.KLog;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.permissions.RxPermissions;
import com.luck.picture.lib.tools.PictureFileUtils;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by liuzhen on 2018/8/3.
 */

public class PhotoSelectUtils {

    private PhotoSelectUtils() {
    }

    private static PhotoSelectUtils instance;

    public static PhotoSelectUtils getInstance() {
        if (instance == null) {
            instance = new PhotoSelectUtils();
        }
        return instance;
    }

    /**
     * 选择
     */
    public void open(Activity activity, boolean isCrop) {
        PictureSelector.create(activity)
                .openGallery(PictureMimeType.ofImage())
                .theme(R.style.picture_QQ_style)
                .imageSpanCount(4)// 每行显示个数 int
//                .compressSavePath(getPath())//压缩图片保存地址
                .selectionMode(PictureConfig.SINGLE)// 多选 or 单选 PictureConfig.MULTIPLE or PictureConfig.SINGLE
                .previewImage(true)// 是否可预览图片 true or false
                .previewVideo(true)// 是否可预览视频 true or false
                .enablePreviewAudio(true) // 是否可播放音频 true or false
                .isCamera(true)// 是否显示拍照按钮 true or false
                .imageFormat(PictureMimeType.PNG)// 拍照保存图片格式后缀,默认jpeg
                .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                .sizeMultiplier(0.5f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效
                .setOutputCameraPath("/photo")// 自定义拍照保存路径,可不填
                .enableCrop(isCrop)// 是否裁剪 true or false
                .glideOverride(160, 160)// int glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
                .withAspectRatio(1, 1)// int 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
                .isGif(true)// 是否显示gif图片 true or false
                .freeStyleCropEnabled(false)// 裁剪框是否可拖拽 true or false
                .isDragFrame(false)// 是否可拖动裁剪框(固定)
                .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
    }

    /**
     * 选择
     */
    public void open(Fragment fragment, boolean isCrop) {
        PictureSelector.create(fragment)
                .openGallery(PictureMimeType.ofImage())
                .theme(R.style.picture_QQ_style)
                .imageSpanCount(4)// 每行显示个数 int
//                .compressSavePath(getPath())//压缩图片保存地址
                .selectionMode(PictureConfig.SINGLE)// 多选 or 单选 PictureConfig.MULTIPLE or PictureConfig.SINGLE
                .previewImage(true)// 是否可预览图片 true or false
                .previewVideo(true)// 是否可预览视频 true or false
                .enablePreviewAudio(true) // 是否可播放音频 true or false
                .isCamera(true)// 是否显示拍照按钮 true or false
                .imageFormat(PictureMimeType.PNG)// 拍照保存图片格式后缀,默认jpeg
                .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                .sizeMultiplier(0.5f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效
                .setOutputCameraPath("/photo")// 自定义拍照保存路径,可不填
                .enableCrop(isCrop)// 是否裁剪 true or false
                .glideOverride(160, 160)// int glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
                .withAspectRatio(1, 1)// int 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
                .isGif(true)// 是否显示gif图片 true or false
                .freeStyleCropEnabled(false)// 裁剪框是否可拖拽 true or false
                .isDragFrame(false)// 是否可拖动裁剪框(固定)
                .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
    }

    /**
     * 选择
     */
    public void openNum(Fragment fragment) {
        PictureNewSelector.create(fragment)
                .openGallery(PictureMimeType.ofImage())
                .theme(R.style.picture_QQ_num_style)
//                .compressSavePath(getPath())//压缩图片保存地址
                .maxSelectNum(9)
                .minSelectNum(1)
                .minimumCompressSize(100)// 小于100kb的图片不压缩
                .cropCompressQuality(50)// 裁剪压缩质量 默认90 int
                .compress(false)// 是否压缩 true or false
                .synOrAsy(true)//同步true或异步false 压缩 默认同步
                .imageSpanCount(4)// 每行显示个数 int
                .selectionMode(PictureConfig.MULTIPLE)// 多选 or 单选 PictureConfig.MULTIPLE or PictureConfig.SINGLE
                .previewImage(false)// 是否可预览图片 true or false
                .previewVideo(true)// 是否可预览视频 true or false
                .enablePreviewAudio(true) // 是否可播放音频 true or false
                .isCamera(true)// 是否显示拍照按钮 true or false
                .imageFormat(PictureMimeType.PNG)// 拍照保存图片格式后缀,默认jpeg
                .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                .sizeMultiplier(0.5f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效
                .setOutputCameraPath("/photo")// 自定义拍照保存路径,可不填
                .glideOverride(160, 160)// int glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
                .isGif(true)// 是否显示gif图片 true or false
                .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
    }

    /**
     * 拍照
     */
    public void img(Fragment fragment, boolean isCrop) {
        PictureSelector.create(fragment)
                .openCamera(PictureMimeType.ofImage())
                .theme(R.style.picture_QQ_style)
//                .compressSavePath(getPath())//压缩图片保存地址
                .setOutputCameraPath("/photo")// 自定义拍照保存路径,可不填
                .enableCrop(isCrop)// 是否裁剪 true or false
                .withAspectRatio(1, 1)// int 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
                .freeStyleCropEnabled(false)// 裁剪框是否可拖拽 true or false
                .isDragFrame(false)// 是否可拖动裁剪框(固定)
                .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
    }

    public void clear(Activity context) {
        RxPermissions permissions = new RxPermissions(context);
        permissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE).subscribe(new Observer<Boolean>() {
            @Override
            public void onSubscribe(Disposable d) {
            }

            @Override
            public void onNext(Boolean aBoolean) {
                if (aBoolean) {
                    PictureFileUtils.deleteCacheDirFile(context);
                } else {
//                    ToastUtils.showToastLongSafe(getString(R.string.picture_jurisdiction));
                    KLog.e("PictureFileUtils", context.getString(R.string.picture_jurisdiction));
                }
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onComplete() {
            }
        });
    }

}

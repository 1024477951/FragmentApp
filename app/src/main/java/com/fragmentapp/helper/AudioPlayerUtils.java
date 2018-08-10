package com.fragmentapp.helper;

import android.media.MediaPlayer;
import android.util.Base64;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by liuzhen on 2018/6/14.
 */

public class AudioPlayerUtils {
    private MediaPlayer mediaPlayer;
    /*-1:初始状态，0:播放完毕，1播放中*/
    public static int playStatus = -1;
    File tempFile = null;

    private AudioPlayerUtils() {
    }

    private static AudioPlayerUtils poolUtils = null;
    public static AudioPlayerUtils newInstance() {
        if (poolUtils == null) {
            poolUtils = new AudioPlayerUtils();
        }
        poolUtils.init();
        return poolUtils;
    }

    /*
    * 初始化mediaPlayer
    * */
    private void init() {
        if (mediaPlayer == null)
            mediaPlayer = new MediaPlayer();
    }



    /**
     * 播放base64数据类型的声音数据
     * @param base64Str
     */
    public void playBase64(String base64Str) {
        try {
            tempFile=base64ToFile(base64Str);
//            tempFile = base64ToFile(encodeBase64File(context));
        } catch (Exception e) {
            e.printStackTrace();
        }
        switch (playStatus) {
            case 1:
                mediaPlayer.stop();
                mediaPlayer.reset();
                break;
            case 0:
                mediaPlayer.stop();
                mediaPlayer.reset();
                break;
            case -1:
                break;
        }
        playStatus = 1;
        try {
            mediaPlayer.setDataSource(tempFile.getPath());
//            mediaPlayer.setDataSource(context, Uri.parse
//                    ("android.resource://" + "com.mdks.doctor/" + raw));
            //如果是setDataSource，那么调用完这个文件之后，音频文件没有真正的加载
            //要调用prepare方法
            //异步的加载方式
            mediaPlayer.prepareAsync();
            //设置循环播放
            mediaPlayer.setLooping(false);
            //加载完成时播放
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
//                    Toaster.show(context, "Prepared----完成");
                    mediaPlayer.start();
                    if (callBack != null){
                        callBack.start();
                    }
                }
            });
            //播放完成时调用
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
//                    ToastUtils.showToastLongSafe("Completion----播放完毕");
                    mediaPlayer.reset();
                    playStatus = 0;
                    if (tempFile != null && tempFile.exists()) {
                        tempFile.delete();
                    }
                    if (callBack != null){
                        callBack.stop();
                    }
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 将本地audio文件转为base64字符串
     *（没有数据时，可用本地文件转base64来作为测试数据）
     * @param context
     * @return
     * @throws Exception
     */
//    public String encodeBase64File(Context context) throws Exception {
//        //R.raw.test：在raw资源文件夹下放入测试声音文件
//        InputStream inputStream = context.getResources().openRawResource(R.raw.test);
//        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//        byte[] buffer = new byte[1024];
//        int len = 0;
//        while ((len = inputStream.read(buffer)) != -1) {
//            outputStream.write(buffer, 0, len);
//        }
//        outputStream.close();
//        inputStream.close();
//        return Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT);
//    }

    /**
     * 将base64字符转换成临时音乐文件
     */
    private File base64ToFile(String base64Str) {
        FileOutputStream outputStream = null;
        if (tempFile == null || !tempFile.exists())
            try {
                tempFile = File.createTempFile("temp", ".mp3");
                byte[] audioByte = Base64.decode(base64Str, Base64.DEFAULT);
                if (tempFile != null) {
                    outputStream = new FileOutputStream(tempFile);
                    outputStream.write(audioByte, 0, audioByte.length);
                    outputStream.flush();
                    outputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (outputStream != null) {
                        outputStream.flush();
                        outputStream.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        return tempFile;
    }

    /**
     * 停止播放，在activity不可见时，停止播放
     */
    public void stop() {
        if (playStatus == 1 && mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
            playStatus = 0;
            if (tempFile != null && tempFile.exists()) {
                tempFile.delete();
            }
        }else{
            mediaPlayer = null;
        }
        if (callBack != null){
            callBack.stop();
        }
    }

    private CallBack callBack;
    public void setCallBack(CallBack callBack){
        this.callBack = callBack;
    }

    public interface CallBack{
        void stop();
        void start();
    }

}
